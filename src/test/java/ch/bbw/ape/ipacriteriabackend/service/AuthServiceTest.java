package ch.bbw.ape.ipacriteriabackend.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    MongoClient mongoClient;

    @Mock
    MongoDatabase mongoDatabase;

    @Mock
    MongoCollection<Document> usersCollection;

    AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService() {
            @Override
            protected MongoClient createMongoClient(String connectionString) {
                return mongoClient;
            }
        };
    }

    @Test
    void getUsersCollection_returnsUsersCollection() {
        when(mongoClient.getDatabase("ipa-criteria-backend"))
                .thenReturn(mongoDatabase);

        when(mongoDatabase.getCollection("users"))
                .thenReturn(usersCollection);

        MongoCollection<Document> result = authService.getUsersCollection();

        assertEquals(usersCollection, result);
    }
}
