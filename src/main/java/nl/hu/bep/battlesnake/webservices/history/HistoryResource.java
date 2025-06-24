package nl.hu.bep.battlesnake.webservices.history;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import nl.hu.bep.battlesnake.models.api.history.GameDTO;
import nl.hu.bep.battlesnake.models.api.history.GamesDTO;
import nl.hu.bep.battlesnake.models.db.GameSession;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.hu.bep.setup.MongoService;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Path("/history")
public class HistoryResource {

    private final MongoDatabase database = MongoService.getDatabase();
    private final MongoCollection<GameSession> sessions = database.getCollection("gamesessions", GameSession.class);

    @GET
    @Path("/games")
    @RolesAllowed({"user", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGames() {
        List<GamesDTO> gameIdDTOs = new ArrayList<>();

        try (MongoCursor<Document> cursor = sessions
                .withDocumentClass(Document.class)
                .find()
                .projection(new Document("start", 0).append("end", 0).append("_id", 1))
                .iterator()) {

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String gameId = doc.getString("gameId");
                if (gameId != null) {
                    gameIdDTOs.add(new GamesDTO(gameId));
                }
            }
        }

        return Response.ok(gameIdDTOs).build();
    }

    @GET
    @Path("/games/{id}")
    @RolesAllowed({"user", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameById(@PathParam("id") String id) {
        HistorySummaryService service = new HistorySummaryService(database);
        GameDTO summary = service.summarizeGame(id);

        if (summary == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Game not found").build();
        }

        return Response.ok(summary).build();
    }
}