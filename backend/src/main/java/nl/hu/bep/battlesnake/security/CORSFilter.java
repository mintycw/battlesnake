package nl.hu.bep.battlesnake.security;

import javax.ws.rs.container.*;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MultivaluedMap;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import java.io.IOException;
import java.util.Set;

@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class CORSFilter implements ContainerResponseFilter, ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Allow preflight (OPTIONS) requests through
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            requestContext.abortWith(Response.ok().build());
        }
    }
    private static final Set<String> ALLOWED_ORIGINS = Set.of(
            System.getenv("FRONTEND_URL"),
            "https://play.battlesnake.com"
    );

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        String origin = requestContext.getHeaderString("Origin");
        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
            responseContext.getHeaders().add("Vary", "Origin");
        }
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
    }
}