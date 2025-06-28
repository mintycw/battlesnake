package nl.hu.bep.battlesnake.webservices.history;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import nl.hu.bep.battlesnake.models.api.history.GameDTO;
import nl.hu.bep.battlesnake.models.api.history.StatsDTO;
import nl.hu.bep.battlesnake.models.db.GameSession;
import org.bson.Document;

import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class HistoryService {
    private final MongoCollection<GameSession> sessions;

    public HistoryService(MongoDatabase db) {
        this.sessions = db.getCollection("gamesessions", GameSession.class);
    }

    public StatsDTO getSummaryStats() {
        var docs = sessions.withDocumentClass(Document.class).find();

        int totalGames = 0;
        int survivedCount = 0;
        int totalEndLength = 0;

        for (Document doc : docs) {
            GameDTO dto = HistoryDTOMapper.fromDocument(doc);
            totalGames++;
            if (dto.isSurvived()) survivedCount++;
            totalEndLength += dto.getEndLength();
        }

        double winRate = totalGames > 0 ? ((double) survivedCount / totalGames) * 100 : 0;
        double averageLength = totalGames > 0 ? (double) totalEndLength / totalGames : 0;

        return new StatsDTO(
                Math.round(winRate),
                Math.round(averageLength),
                totalGames
        );
    }

    public GameDTO getGameDetails(String gameId) {
        Document doc = sessions
                .withDocumentClass(Document.class)
                .find(eq("gameId", gameId))
                .projection(new Document("_id", 0))
                .first();

        if (doc == null) return null;

        return HistoryDTOMapper.fromDocument(doc);
    }

    public boolean deleteGameById(String gameId) {
        var result = sessions.deleteOne(eq("gameId", gameId));
        return result.getDeletedCount() > 0;
    }
}