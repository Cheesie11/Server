package ch.bbw.ape.ipacriteriabackend.session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * Create a new session for a user
     */
    public Session createSession(String userId, String title, String description, List<SessionCriteria> criteria) {
        logger.info("Creating new session for user: {}", userId);

        if (userId == null || title == null) {
            throw new RuntimeException("UserId and title must not be null");
        }

        Session session = Session.builder()
                .userId(userId)
                .title(title)
                .description(description)
                .criteria(criteria != null ? criteria : List.of())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Session savedSession = sessionRepository.save(session);
        logger.info("Session created with ID: {}", savedSession.getId());
        return savedSession;
    }

    /**
     * Get all sessions for a user
     */
    public List<Session> getUserSessions(String userId) {
        logger.info("Fetching sessions for user: {}", userId);
        return sessionRepository.findByUserId(userId);
    }

    /**
     * Get a specific session by ID
     */
    public Optional<Session> getSession(String sessionId) {
        return sessionRepository.findById(sessionId);
    }

    /**
     * Get a specific session and verify ownership
     */
    public Optional<Session> getSessionForUser(String sessionId, String userId) {
        return sessionRepository.findByIdAndUserId(sessionId, userId);
    }

    /**
     * Update a session
     */
    public Session updateSession(String sessionId, String title, String description, List<SessionCriteria> criteria) {
        logger.info("Updating session: {}", sessionId);

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setTitle(title);
        session.setDescription(description);
        session.setCriteria(criteria);
        session.setUpdatedAt(LocalDateTime.now());

        return sessionRepository.save(session);
    }

    /**
     * Add a criteria to a session
     */
    public Session addCriteria(String sessionId, SessionCriteria criteria) {
        logger.info("Adding criteria to session: {}", sessionId);

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.getCriteria().add(criteria);
        session.setUpdatedAt(LocalDateTime.now());

        return sessionRepository.save(session);
    }

    /**
     * Delete a session
     */
    public void deleteSession(String sessionId) {
        logger.info("Deleting session: {}", sessionId);
        sessionRepository.deleteById(sessionId);
    }
}
