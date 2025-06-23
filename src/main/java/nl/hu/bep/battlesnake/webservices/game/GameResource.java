package nl.hu.bep.battlesnake.webservices.game;

import nl.hu.bep.battlesnake.models.api.game.GameRequest;
import nl.hu.bep.battlesnake.models.api.game.MoveResponse;
import nl.hu.bep.battlesnake.models.api.game.SnakeRequest;
import nl.hu.bep.battlesnake.webservices.game.moveservice.*;
import nl.hu.bep.setup.MongoService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.hu.bep.battlesnake.models.db.GameSession;
import static com.mongodb.client.model.Updates.set;

import com.mongodb.client.MongoCollection;

import static com.mongodb.client.model.Filters.eq;

@Path("/")
public class GameResource {

    private final MongoCollection<GameSession> sessions =
            MongoService.getDatabase().getCollection("gamesessions", GameSession.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SnakeRequest getSnake() {
        System.out.println("getSnake");
        return new SnakeRequest();
    }

    @POST
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response onStart(GameRequest request) {
        GameSession session = new GameSession(request.getGame().getId(), request);
        sessions.insertOne(session);
        return Response.ok().build();
    }

    @POST
    @Path("/end")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response onEnd(GameRequest request) {
        sessions.updateOne(
                eq("gameId", request.getGame().getId()),
                set("end", request)
        );
        return Response.ok().build();
    }

    @POST
    @Path("/move")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MoveResponse moveGame(GameRequest request) {
        GameStateSimulator simulator = new GameStateSimulator();
        Evaluator evaluator = new Evaluator();
        MoveValidator validator = new MoveValidator();

        Strategy ai = new ParanoidMinimax(simulator, evaluator, validator);
        String move = ai.findBestMove(request);

        return new MoveResponse(move);
    }
}
