package nl.hu.bep.battlesnake.webservices.history;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import nl.hu.bep.battlesnake.models.api.history.GameDTO;
import nl.hu.bep.battlesnake.models.db.GameSession;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class HistorySummaryService {
    private final MongoCollection<GameSession> sessions;

    public HistorySummaryService(MongoDatabase db) {
        this.sessions = db.getCollection("gamesessions", GameSession.class);
    }

    public GameDTO summarizeGame(String gameId) {
        Document doc = sessions
                .withDocumentClass(Document.class)
                .find(eq("gameId", gameId))
                .projection(new Document("_id", 0))
                .first();

        if (doc == null) return null;

        return HistoryDTOMapper.fromDocument(doc);
    }
}