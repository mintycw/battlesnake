package nl.hu.bep.battlesnake.webservices.snake;

import nl.hu.bep.battlesnake.models.api.game.SnakeResponse;
import nl.hu.bep.battlesnake.models.api.snake.SnakeDTO;
import org.bson.Document;

import java.util.Date;

public class SnakeDTOMapper {
    private static class SnakeCommonFields {
        String color;
        String head;
        String tail;
        String author;

        SnakeCommonFields(Document doc) {
            this.color = doc.getString("color");
            this.head = doc.getString("head");
            this.tail = doc.getString("tail");
            this.author = doc.getString("author");

        }
    }

    public static SnakeDTO formSnakeDTO(Document doc) {
        SnakeCommonFields fields = new SnakeCommonFields(doc);
        Date lastUpdated = doc.getDate("lastUpdated");
        String updatedBy = doc.getString("updatedBy");

        return new SnakeDTO(fields.color, fields.head, fields.tail, fields.author, lastUpdated, updatedBy);
    }

    public static SnakeResponse formSnakeResponse(Document doc) {
        String apiVersion = doc.getString("apiVersion");
        String version = doc.getString("version");

        SnakeCommonFields fields = new SnakeCommonFields(doc);
        return new SnakeResponse(apiVersion, fields.author, fields.color, fields.head, fields.tail, version
        );
    }
}
