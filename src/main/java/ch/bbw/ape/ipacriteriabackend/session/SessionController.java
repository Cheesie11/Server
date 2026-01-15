package ch.bbw.ape.ipacriteriabackend.session;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * Create a new session
     */
    @PostMapping
    public ResponseEntity<?> createSession(@RequestBody SessionRequest request) {
        try {
            logger.info("Creating new session for user: {}", request.userId());
            Session session = sessionService.createSession(
                    request.userId(),
                    request.title(),
                    request.description(),
                    request.criteria()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(session);
        } catch (RuntimeException e) {
            logger.error("Error creating session: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get all sessions for a user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserSessions(@PathVariable String userId) {
        try {
            List<Session> sessions = sessionService.getUserSessions(userId);
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            logger.error("Error fetching sessions: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Get a specific session
     */
    @GetMapping("/{sessionId}")
    public ResponseEntity<?> getSession(@PathVariable String sessionId) {
        try {
            return sessionService.getSession(sessionId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error fetching session: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Update a session
     */
    @PutMapping("/{sessionId}")
    public ResponseEntity<?> updateSession(
            @PathVariable String sessionId,
            @RequestBody SessionRequest request) {
        try {
            Session session = sessionService.updateSession(
                    sessionId,
                    request.title(),
                    request.description(),
                    request.criteria()
            );
            return ResponseEntity.ok(session);
        } catch (RuntimeException e) {
            logger.error("Error updating session: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Add a criteria to a session
     */
    @PostMapping("/{sessionId}/criteria")
    public ResponseEntity<?> addCriteria(
            @PathVariable String sessionId,
            @RequestBody SessionCriteria criteria) {
        try {
            Session session = sessionService.addCriteria(sessionId, criteria);
            return ResponseEntity.ok(session);
        } catch (RuntimeException e) {
            logger.error("Error adding criteria: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Delete a session
     */
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<?> deleteSession(@PathVariable String sessionId) {
        try {
            sessionService.deleteSession(sessionId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting session: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}

/**
 * DTO for session creation/update requests
 */
record SessionRequest(
        String userId,
        String title,
        String description,
        List<SessionCriteria> criteria
) {}
