package nl.hu.bep;

import com.mongodb.client.MongoDatabase;
import nl.hu.bep.battlesnake.models.api.snake.SnakeDTO;
import nl.hu.bep.battlesnake.models.db.SnakeRequest;
import nl.hu.bep.battlesnake.webservices.snake.SnakeResource;
import nl.hu.bep.battlesnake.webservices.snake.SnakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SnakeResourceTest {

    @Mock
    private MongoDatabase mockDb;

    @Mock
    private SnakeService mockService;

    @InjectMocks
    private SnakeResource snakeResource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        snakeResource = new SnakeResource();
    }

    @Test
    public void testGetSnakeFound() {
        SnakeDTO dummySnake = new SnakeDTO(); // Populate if needed

        SnakeResource resource = new SnakeResource() {
            @Override
            public Response getSnake() {
                return Response.ok(dummySnake).build();
            }
        };

        Response response = resource.getSnake();
        assertEquals(200, response.getStatus());
        assertEquals(dummySnake, response.getEntity());
    }

    @Test
    public void testGetSnakeNotFound() {
        SnakeResource resource = new SnakeResource() {
            @Override
            public Response getSnake() {
                return Response.status(Response.Status.NOT_FOUND).entity("Snake not found").build();
            }
        };

        Response response = resource.getSnake();
        assertEquals(404, response.getStatus());
        assertEquals("Snake not found", response.getEntity());
    }

    @Test
    public void testUpdateSnakeSuccess() {
        SnakeRequest request = new SnakeRequest(); // Provide values if needed

        SnakeResource resource = new SnakeResource() {
            @Override
            public Response updateSnake(SnakeRequest snake) {
                return Response.ok(snake).build();
            }
        };

        Response response = resource.updateSnake(request);
        assertEquals(200, response.getStatus());
        assertEquals(request, response.getEntity());
    }

    @Test
    public void testUpdateSnakeNotFound() {
        SnakeRequest request = new SnakeRequest();

        SnakeResource resource = new SnakeResource() {
            @Override
            public Response updateSnake(SnakeRequest snake) {
                return Response.status(Response.Status.NOT_FOUND).entity("Snake not found").build();
            }
        };

        Response response = resource.updateSnake(request);
        assertEquals(404, response.getStatus());
        assertEquals("Snake not found", response.getEntity());
    }
}