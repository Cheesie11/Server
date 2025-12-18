package ch.bbw.ape.ipacriteriabackend.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Service
public class AuthService {

    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String user;

    @Value("${db.pwd}")
    private String pwd;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private MongoCollection<Document> getUsersCollection() {
        String connectionString = String.format(
                "mongodb+srv://%s:%s@%s/?retryWrites=true&w=majority",
                user, pwd, url
        );

        MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(connectionString))
                        .build()
        );

        MongoDatabase database = mongoClient.getDatabase("users_db");
        return database.getCollection("users");
    }

    public void register(String username, String password) {
        MongoCollection<Document> users = getUsersCollection();

        if (users.find(Filters.eq("username", username)).first() != null) {
            throw new RuntimeException("Username already exists");
        }

        Document doc = new Document()
                .append("username", username)
                .append("password", passwordEncoder.encode(password));

        users.insertOne(doc);
    }

    public boolean login(String username, String password) {
        MongoCollection<Document> users = getUsersCollection();

        Document userDoc = users.find(Filters.eq("username", username)).first();
        if (userDoc == null) return false;

        String hashedPassword = userDoc.getString("password");
        return passwordEncoder.matches(password, hashedPassword);
    }
}
