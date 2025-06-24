package nl.hu.bep.battlesnake.webservices.auth;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import nl.hu.bep.battlesnake.models.db.User;
import nl.hu.bep.setup.MongoService;

import static com.mongodb.client.model.Filters.eq;

public class UserService {
    private final MongoCollection<User> users;

    public UserService() {
        MongoDatabase database = MongoService.getDatabase();
        this.users = database.getCollection("users", User.class);
    }

    public void saveUser(User user) {
        users.insertOne(user);
    }

    public User findByUsername(String username) {
        return users.find(eq("username", username)).first();
    }
}