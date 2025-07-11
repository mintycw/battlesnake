package nl.hu.bep.setup;

import nl.hu.bep.battlesnake.security.CORSFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(RolesAllowedDynamicFeature.class);
        packages("nl.hu.bep.battlesnake.webservices", "nl.hu.bep.battlesnake.security");
    }
}
