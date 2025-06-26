package nl.hu.bep.battlesnake.webservices.snake;

import com.mongodb.client.MongoDatabase;
import nl.hu.bep.battlesnake.models.api.snake.SnakeDTO;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.hu.bep.battlesnake.models.db.SnakeRequest;
import nl.hu.bep.setup.MongoService;

@Path("/snake")
public class SnakeResource {

    private final MongoDatabase database = MongoService.getDatabase();

    @GET
    @RolesAllowed({"user", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSnake() {
        SnakeService service = new SnakeService(database);
        SnakeDTO snakeDTO = service.getSnakeDTO();

      if (snakeDTO == null) {
          return Response.status(Response.Status.NOT_FOUND).entity("Snake not found").build();
      }

        return Response.ok(snakeDTO).build();
    }

    @PUT
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSnake(SnakeRequest snake) {
        SnakeService service = new SnakeService(database);
        boolean isUpdated = service.updateSnake(snake);

        if (!isUpdated) {
            return Response.status(Response.Status.NOT_FOUND).entity("Snake not found").build();
        }

        return Response.ok(snake).build();
    }
}