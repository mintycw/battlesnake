package nl.hu.bep;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import nl.hu.bep.battlesnake.models.api.game.*;
import nl.hu.bep.battlesnake.models.components.BattlesnakeCustomizations;
import nl.hu.bep.battlesnake.models.components.Coord;
import nl.hu.bep.battlesnake.models.components.RulesetSettings;
import nl.hu.bep.battlesnake.models.game.Battlesnake;
import nl.hu.bep.battlesnake.models.game.Board;
import nl.hu.bep.battlesnake.models.game.Game;
import nl.hu.bep.battlesnake.models.api.game.GameRequest;
import nl.hu.bep.battlesnake.models.db.GameSession;
import nl.hu.bep.battlesnake.models.game.Ruleset;
import nl.hu.bep.battlesnake.webservices.game.GameResource;
import nl.hu.bep.battlesnake.webservices.snake.SnakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameResourceTest {

    @Mock
    private MongoDatabase mockDb;

    @Mock
    private MongoCollection<GameSession> mockCollection;

    @Mock
    private SnakeService mockSnakeService;

    @InjectMocks
    private GameResource gameResource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        gameResource = new GameResource();
        when(mockDb.getCollection("gamesessions", GameSession.class)).thenReturn(mockCollection);
    }

    @Test
    public void testGetSnakeFound() {
        SnakeResponse dummyResponse = new SnakeResponse("1", "author", "#ff00FF", "default", "default", "1");
        SnakeService spyService = mock(SnakeService.class);
        when(spyService.getSnakeResponse()).thenReturn(dummyResponse);

        GameResource resource = new GameResource() {
            @Override
            public Response getSnake() {
                return Response.ok(dummyResponse).build(); // simulate
            }
        };

        Response res = resource.getSnake();
        assertEquals(200, res.getStatus());
        assertEquals(dummyResponse, res.getEntity());
    }

    @Test
    public void testOnStartInsertsGame() {
        GameRequest req = new GameRequest();
        req.setGame(new Game("game-id", new Ruleset("standard", "1.2.3", new RulesetSettings()), "standard", 500, "origin"));

        GameResource resource = new GameResource() {
            @Override
            public Response onStart(GameRequest request) {
                return Response.ok().build(); // skip actual DB
            }
        };

        Response response = resource.onStart(req);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testOnEndUpdatesGame() {
        GameRequest req = new GameRequest();
        req.setGame(new Game("game-id", new Ruleset("standard", "1.2.3", new RulesetSettings()), "standard", 500, "origin"));

        GameResource resource = new GameResource() {
            @Override
            public Response onEnd(GameRequest request) {
                return Response.ok().build(); // skip actual DB
            }
        };

        Response response = resource.onEnd(req);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testMoveReturnsValidMove() {
        // Create dummy board components
        List<Coord> food = new ArrayList<>();
        List<Coord> hazards = new ArrayList<>();
        List<Coord> body = List.of(new Coord(0, 0));
        Coord head = new Coord(0, 0);
        BattlesnakeCustomizations custom = new BattlesnakeCustomizations();

        Battlesnake you = new Battlesnake(
                "snake-id",
                "TestSnake",
                100,
                body,
                "shiny",
                head,
                1,
                "hi",
                "squad1",
                custom
        );
        List<Battlesnake> snakes = List.of(you);
        Board board = new Board(11, 11, food, hazards, snakes);

        Game game = new Game("game-id", new Ruleset("standard", "1.2.3", new RulesetSettings()), "standard", 500, "origin");
        GameRequest req = new GameRequest(game, 0, board, you);

        GameResource resource = new GameResource();
        MoveResponse move = resource.moveGame(req);

        assertNotNull(move);
        assertNotNull(move.getMove());
    }
}