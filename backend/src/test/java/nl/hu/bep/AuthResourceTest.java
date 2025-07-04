package nl.hu.bep;

import nl.hu.bep.battlesnake.models.api.auth.LogInRequest;
import nl.hu.bep.battlesnake.models.api.auth.SignUpRequest;
import nl.hu.bep.battlesnake.models.api.auth.UserDTO;
import nl.hu.bep.battlesnake.models.db.User;
import nl.hu.bep.battlesnake.webservices.auth.AuthResource;
import nl.hu.bep.battlesnake.webservices.auth.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.SecurityContext;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthResourceTest {

    @InjectMocks
    private AuthResource authResource;

    @Mock
    private UserService userService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private javax.servlet.http.HttpServletResponse servletResponse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        authResource = new AuthResource(userService); // manually inject mock
        TestUtils.setField(authResource, "SECRET_KEY", "test-secret-key");
    }

    // Helper to inject private final field SECRET_KEY for testing
    static class TestUtils {
        static void setField(Object target, String fieldName, Object value) {
            try {
                var field = target.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void testSignupSuccess() throws NoSuchAlgorithmException {
        SignUpRequest req = new SignUpRequest();
        req.setUsername("testuser");
        req.setPassword("password");
        req.setRole("user");

        when(userService.findByUsername("testuser")).thenReturn(null);

        Response resp = authResource.signup(req);
        assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
        Map<String, Object> entity = (Map<String, Object>) resp.getEntity();
        assertEquals(200, entity.get("status"));
        assertEquals("User registered successfully", entity.get("message"));
        verify(userService).saveUser(any(User.class));
    }

    @Test
    public void testSignupMissingUsernameOrPassword() throws NoSuchAlgorithmException {
        SignUpRequest req = new SignUpRequest();
        req.setUsername(null);
        req.setPassword(null);

        Response resp = authResource.signup(req);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), resp.getStatus());
        Map<String, Object> entity = (Map<String, Object>) resp.getEntity();
        assertEquals(400, entity.get("status"));
        assertTrue(entity.get("message").toString().contains("Username and password required"));
    }

    @Test
    public void testSignupUserExists() throws NoSuchAlgorithmException {
        SignUpRequest req = new SignUpRequest();
        req.setUsername("existing");
        req.setPassword("password");

        when(userService.findByUsername("existing")).thenReturn(new User());

        Response resp = authResource.signup(req);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), resp.getStatus());
        Map<String, Object> entity = (Map<String, Object>) resp.getEntity();
        assertEquals(409, entity.get("status"));
        assertTrue(entity.get("message").toString().contains("User already exists"));
    }

    @Test
    public void testLoginSuccess() throws NoSuchAlgorithmException {
        LogInRequest req = new LogInRequest();
        req.setUsername("testuser");
        req.setPassword("password");

        String hashed = authResource.hashPassword("password");
        User user = new User("testuser", hashed, "user");

        when(userService.findByUsername("testuser")).thenReturn(user);

        Response resp = authResource.login(req, servletResponse);
        assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
        Map<String, Object> entity = (Map<String, Object>) resp.getEntity();
        assertEquals(200, entity.get("status"));
        assertEquals("Login successful", entity.get("message"));
        assertNotNull(resp.getCookies().get("jwt"));
    }

    @Test
    public void testLoginInvalidUser() {
        LogInRequest req = new LogInRequest();
        req.setUsername("notfound");
        req.setPassword("password");

        when(userService.findByUsername("notfound")).thenReturn(null);

        Response resp = authResource.login(req, servletResponse);
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), resp.getStatus());
        Map<String, Object> entity = (Map<String, Object>) resp.getEntity();
        assertEquals(401, entity.get("status"));
        assertTrue(entity.get("message").toString().contains("Invalid credentials"));
    }

    @Test
    public void testLoginInvalidPassword() throws NoSuchAlgorithmException {
        LogInRequest req = new LogInRequest();
        req.setUsername("testuser");
        req.setPassword("wrongpass");

        String hashed = authResource.hashPassword("password");
        User user = new User("testuser", hashed, "user");

        when(userService.findByUsername("testuser")).thenReturn(user);

        Response resp = authResource.login(req, servletResponse);
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), resp.getStatus());
        Map<String, Object> entity = (Map<String, Object>) resp.getEntity();
        assertEquals(401, entity.get("status"));
        assertTrue(entity.get("message").toString().contains("Invalid credentials"));
    }

    @Test
    public void testLoginPasswordHashingError() throws NoSuchAlgorithmException {
        LogInRequest req = new LogInRequest();
        req.setUsername("testuser");
        req.setPassword("password");

        User user = new User("testuser", "somehash", "user");

        when(userService.findByUsername("testuser")).thenReturn(user);

        // Spy on AuthResource to throw on hashPassword
        AuthResource spy = Mockito.spy(authResource);
        doThrow(new NoSuchAlgorithmException()).when(spy).hashPassword(anyString());

        Response resp = spy.login(req, servletResponse);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), resp.getStatus());
        Map<String, Object> entity = (Map<String, Object>) resp.getEntity();
        assertEquals(500, entity.get("status"));
        assertTrue(entity.get("message").toString().contains("Error verifying password"));
    }

    @Test
    public void testLogout() {
        Response resp = authResource.logout();
        assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
        Map<String, Object> entity = (Map<String, Object>) resp.getEntity();
        assertEquals(200, entity.get("status"));
        assertEquals("Logged out", entity.get("message"));
        assertNotNull(resp.getCookies().get("jwt"));
        assertEquals("", resp.getCookies().get("jwt").getValue());
    }

    @Test
    public void testGetCurrentUserSuccess() {
        when(securityContext.getUserPrincipal()).thenReturn(() -> "user1");
        when(securityContext.isUserInRole("admin")).thenReturn(true);
        when(securityContext.isUserInRole("user")).thenReturn(false);

        Response resp = authResource.getCurrentUser(securityContext);
        assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
        UserDTO dto = (UserDTO) resp.getEntity();
        assertEquals("user1", dto.getUsername());
        assertTrue(dto.getRoles().contains("admin"));
        assertFalse(dto.getRoles().contains("user"));
    }

    @Test
    public void testGetCurrentUserUnauthorized() {
        // Simulate exception when calling security context
        Response resp = authResource.getCurrentUser(null);
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), resp.getStatus());
        Map<String, Object> entity = (Map<String, Object>) resp.getEntity();
        assertEquals(401, entity.get("status"));
        assertEquals("Unauthorized", entity.get("message"));
    }
}