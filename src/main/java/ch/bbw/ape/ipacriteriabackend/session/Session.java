package ch.bbw.ape.ipacriteriabackend.session;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a session that contains multiple criteria
 * A user can have multiple sessions for different projects
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sessions")
public class Session {

    @Id
    private String id;

    @Field("userId")
    private String userId; // Reference to Person ID

    @Field("title")
    private String title; // Project name or session name

    @Field("description")
    private String description; // Optional description

    @Field("createdAt")
    private LocalDateTime createdAt;

    @Field("updatedAt")
    private LocalDateTime updatedAt;

    @Field("criteria")
    private List<SessionCriteria> criteria; // List of criteria for this session
}
