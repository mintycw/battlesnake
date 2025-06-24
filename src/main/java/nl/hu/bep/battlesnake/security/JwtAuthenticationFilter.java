package nl.hu.bep.battlesnake.security;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import io.jsonwebtoken.*;
import javax.ws.rs.core.Cookie;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthenticationFilter implements ContainerRequestFilter {
    private static final String SECRET_KEY = System.getenv("SECRET_KEY");


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Cookie cookie = requestContext.getCookies().get("jwt");
        if (cookie == null) {
            return; // no token, proceed as anonymous or reject elsewhere
        }

        String token = cookie.getValue();

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);

            SecurityContext original = requestContext.getSecurityContext();
            requestContext.setSecurityContext(new SecurityContext() {
                @Override public Principal getUserPrincipal() {
                    return () -> username;
                }

                @Override public boolean isUserInRole(String role) {
                    return roles != null && roles.contains(role);
                }

                @Override public boolean isSecure() {
                    return original.isSecure();
                }

                @Override public String getAuthenticationScheme() {
                    return "Bearer";
                }
            });

        } catch (JwtException e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}