package ch.bbw.ape.ipacriteriabackend.service;

import ch.bbw.ape.ipacriteriabackend.person.Person;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Service
public class RegisterService {

    @Value("${db.url}")
    private String url;


    @Value("${db.user}")
    private String user;

    @Value("${db.pwd}")
    private String pwd;

    public void saveNewUser(Person person) {
        String connectionString = String.format("mongodb+srv://%s:%s@%s/?retryWrites=true&w=majority", user, pwd, url);

        try (MongoClient mongoClient = MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build())) {
            MongoDatabase database = mongoClient.getDatabase("persons");
            try {
                MongoCollection<Document> carDocs = database.getCollection("persons");
                Document doc = new Document();
                doc.append("id", person.getId());
                doc.append("firstname", person.getFirstname());
                doc.append("lastname", person.getLastname());
                doc.append("subject", person.getSubject());
                doc.append("date", person.getDate());
                carDocs.insertOne(doc);
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
    }

}
