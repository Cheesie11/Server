package ch.bbw.ape.ipacriteriabackend.session;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends MongoRepository<Session, String> {
    
    /**
     * Find all sessions for a specific user
     */
    List<Session> findByUserId(String userId);

    /**
     * Find a specific session by ID and user ID (security check)
     */
    Optional<Session> findByIdAndUserId(String id, String userId);

    /**
     * Delete all sessions for a user
     */
    void deleteByUserId(String userId);
}
