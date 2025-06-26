package nl.hu.bep.battlesnake.webservices.snake;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import nl.hu.bep.battlesnake.models.api.game.SnakeResponse;
import nl.hu.bep.battlesnake.models.api.snake.SnakeDTO;
import nl.hu.bep.battlesnake.models.db.SnakeRequest;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

public class SnakeService {
    private final MongoCollection<SnakeRequest> snakeCollection;

    public SnakeService(MongoDatabase db) {
        this.snakeCollection = db.getCollection("snake", SnakeRequest.class);
    }

    public SnakeDTO getSnakeDTO() {
        Document doc = snakeCollection
                .withDocumentClass(Document.class)
                .find()
                .projection(new Document("_id", 0))
                .first();

        if (doc == null) return null;

        return SnakeDTOMapper.formSnakeDTO(doc);
    }

    public SnakeResponse getSnakeResponse() {
        Document doc = snakeCollection
                .withDocumentClass(Document.class)
                .find()
                .projection(new Document("_id", 0))
                .first();

        if (doc == null) return null;

        return SnakeDTOMapper.formSnakeResponse(doc);
    }


    public boolean updateSnake(SnakeRequest snake) {
        Document update = new Document();
        if (snake.getColor() != null) update.append("color", snake.getColor());
        if (snake.getHead() != null) update.append("head", snake.getHead());
        if (snake.getTail() != null) update.append("tail", snake.getTail());
        if (snake.getUpdatedBy() != null) update.append("updatedBy", snake.getUpdatedBy());
        update.append("lastUpdated", new Date());

        var result = snakeCollection.updateOne(
                new Document(), // match the first document
                new Document("$set", update)
        );

        return result.getModifiedCount() > 0;
    }
}
