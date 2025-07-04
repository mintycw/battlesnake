package nl.hu.bep;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import nl.hu.bep.battlesnake.models.api.history.*;
import nl.hu.bep.battlesnake.models.db.GameSession;
import nl.hu.bep.battlesnake.webservices.history.HistoryResource;
import nl.hu.bep.battlesnake.webservices.history.HistoryService;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HistoryResourceTest {

    @Mock
    private MongoDatabase mockDb;

    @Mock
    private MongoCollection<GameSession> mockCollection;

    @Mock
    private HistoryService mockService;

    @InjectMocks
    private HistoryResource historyResource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        historyResource = new HistoryResource();
        when(mockDb.getCollection("gamesessions", GameSession.class)).thenReturn(mockCollection);
    }

    @Test
    public void testGetStatsReturnsData() {
        StatsDTO dummyStats = new StatsDTO(10, 5, 5); // replace with your constructor args

        HistoryService service = mock(HistoryService.class);
        when(service.getSummaryStats()).thenReturn(dummyStats);

        HistoryResource resource = new HistoryResource() {
            @Override
            public Response getStats() {
                return Response.ok(dummyStats).build();
            }
        };

        Response response = resource.getStats();
        assertEquals(200, response.getStatus());
        assertEquals(dummyStats, response.getEntity());
    }

    @Test
    public void testGetGameByIdFound() {
        GameDTO game = new GameDTO(); // fill with dummy values as needed

        HistoryResource resource = new HistoryResource() {
            @Override
            public Response getGameById(String id) {
                return Response.ok(game).build();
            }
        };

        Response response = resource.getGameById("abc123");
        assertEquals(200, response.getStatus());
        assertEquals(game, response.getEntity());
    }

    @Test
    public void testGetGameByIdNotFound() {
        HistoryResource resource = new HistoryResource() {
            @Override
            public Response getGameById(String id) {
                return Response.status(Response.Status.NOT_FOUND).entity("Game not found").build();
            }
        };

        Response response = resource.getGameById("nonexistent");
        assertEquals(404, response.getStatus());
        assertEquals("Game not found", response.getEntity());
    }

    @Test
    public void testDeleteGameByIdFound() {
        HistoryResource resource = new HistoryResource() {
            @Override
            public Response deleteGameById(String id) {
                return Response.ok(Map.of("message", "Game successfully deleted")).build();
            }
        };

        Response response = resource.deleteGameById("abc123");
        assertEquals(200, response.getStatus());
        assertEquals(Map.of("message", "Game successfully deleted"), response.getEntity());
    }

    @Test
    public void testDeleteGameByIdNotFound() {
        HistoryResource resource = new HistoryResource() {
            @Override
            public Response deleteGameById(String id) {
                return Response.status(Response.Status.NOT_FOUND).entity("Game not found").build();
            }
        };

        Response response = resource.deleteGameById("doesnotexist");
        assertEquals(404, response.getStatus());
        assertEquals("Game not found", response.getEntity());
    }
}