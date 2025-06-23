package nl.hu.bep.setup;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.*;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoService {
    private static final MongoClient client;
    private static final MongoDatabase database;

    static {
        String uri = System.getenv("MONGO_URI");

        if (uri == null || uri.isBlank()) {
            throw new IllegalStateException("Environment variable MONGO_URI is not set or is blank.");
        }

        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new com.mongodb.ConnectionString(uri))
                .codecRegistry(pojoCodecRegistry)
                .build();

        client = MongoClients.create(settings);
        database = client.getDatabase("battlesnake");
    }

    public static MongoDatabase getDatabase() {
        return database;
    }
}