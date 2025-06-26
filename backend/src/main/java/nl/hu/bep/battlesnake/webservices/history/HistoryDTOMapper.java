package nl.hu.bep.battlesnake.webservices.history;

import nl.hu.bep.battlesnake.models.api.history.GameDTO;
import org.bson.Document;

public class HistoryDTOMapper {
    public static GameDTO fromDocument(Document doc) {
        String gameId = doc.getString("gameId");
        Document start = doc.get("start", Document.class);
        Document end = doc.get("end", Document.class);

        int totalTurns = end.getInteger("turn", 0) - start.getInteger("turn", 0);
        int startingSnakes = start.get("board", Document.class).getList("snakes", Document.class).size();
        int endingSnakes = end.get("board", Document.class).getList("snakes", Document.class).size();

        Document youStart = start.get("you", Document.class);
        Document youEnd = end.get("you", Document.class);

        String yourSnakeName = youStart.getString("name");
        int startLength = youStart.getInteger("length", 0);
        int endLength = youEnd.getInteger("length", 0);
        int startHealth = youStart.getInteger("health", 0);
        int endHealth = youEnd.getInteger("health", 0);
        boolean survived = endingSnakes > 0;

        return new GameDTO(gameId, totalTurns, startingSnakes, endingSnakes,
                yourSnakeName, startLength, endLength, startHealth, endHealth, survived);
    }
}