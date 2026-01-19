package ch.bbw.ape.ipacriteriabackend.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
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

    protected MongoClient createMongoClient(String connectionString) {
        return MongoClients.create(
                MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(connectionString))
                        .build()
        );
    }

    public MongoCollection<Document> getUsersCollection() {
        String connectionString = String.format(
                "mongodb+srv://%s:%s@%s/?retryWrites=true&w=majority&appName=names",
                user, pwd, url
        );

        MongoClient mongoClient = createMongoClient(connectionString);

        MongoDatabase database = mongoClient.getDatabase("ipa-criteria-backend");
        return database.getCollection("users");
    }

    public void register(String firstname, String lastname) {
        if (firstname == null || lastname == null) {
            throw new RuntimeException("Firstname and lastname must not be null");
        }

        MongoCollection<Document> users = getUsersCollection();

        Document existingUser = users.find(
                Filters.and(
                        Filters.eq("firstname", firstname),
                        Filters.eq("lastname", lastname)
                )
        ).first();

        if (existingUser != null) {
            throw new RuntimeException("User already exists");
        }

        Document doc = new Document()
                .append("firstname", firstname)
                .append("lastname", lastname);

        users.insertOne(doc);
    }

    public boolean login(String firstname, String lastname) {
        if (firstname == null || lastname == null) {
            return false;
        }

        MongoCollection<Document> users = getUsersCollection();

        return users.find(
                Filters.and(
                        Filters.eq("firstname", firstname),
                        Filters.eq("lastname", lastname)
                )
        ).first() != null;
    }
}