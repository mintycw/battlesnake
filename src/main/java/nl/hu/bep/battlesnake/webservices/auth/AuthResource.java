package nl.hu.bep.battlesnake.webservices.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nl.hu.bep.battlesnake.models.api.auth.LogInRequest;
import nl.hu.bep.battlesnake.models.api.auth.SignUpRequest;
import nl.hu.bep.battlesnake.models.db.User;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Path("/auth")
public class AuthResource {
    private final String SECRET_KEY = System.getenv("SECRET_KEY");
    private final UserService userService = new UserService();

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(SignUpRequest signupRequest) throws NoSuchAlgorithmException {
        String username = signupRequest.getUsername();
        String password = signupRequest.getPassword();
        String role = signupRequest.getRole();

        if (username == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Username and password required").build();
        }

        if (userService.findByUsername(username) != null) {
            return Response.status(Response.Status.CONFLICT).entity("User already exists").build();
        }

        String hashedPassword = hashPassword(password);

        User user = new User(username, hashedPassword, role);
        userService.saveUser(user);

        return Response.ok(Map.of("message", "User registered successfully")).build();
    }


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LogInRequest loginRequest, @Context HttpServletResponse response) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userService.findByUsername(username);
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }

        try {
            if (!user.getPassword().equals(hashPassword(password))) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
            }
        } catch (NoSuchAlgorithmException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error verifying password").build();
        }

        String token = Jwts.builder()
                .setSubject(username)
                .claim("roles", List.of(user.getRole()))
                .setExpiration(new Date(System.currentTimeMillis() + AuthConfig.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();

        // Convert to seconds
        int expirationTime = (int) (AuthConfig.EXPIRATION_TIME / 1000);

        // Set token in HttpOnly cookie
        NewCookie jwtCookie = new NewCookie(
                "jwt",
                token,
                "/eindopdracht_war",
                null,
                null,
                expirationTime,
                false,
                true);

        return Response.ok(Map.of("message", "Login successful"))
                .cookie(jwtCookie)
                .build();
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout() {
        NewCookie expiredTokenCookie = new NewCookie(
                "jwt",
                "",
                "/eindopdracht_war",
                null,
                null,
                0,
                false,
                true
        );
        return Response.ok(Map.of("message", "Logged out"))
                .cookie(expiredTokenCookie)
                .build();
    }

    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public Response getCurrentUser(@Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();
        return Response.ok(Map.of("username", username)).build();
    }

    // Simple SHA-256 hash helper
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}