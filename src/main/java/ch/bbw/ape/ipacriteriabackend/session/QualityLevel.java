package ch.bbw.ape.ipacriteriabackend.session;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a quality level (Gütestufe) for a criteria
 * E.g., Gütestufe 3, 2, 1, 0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityLevel {

    @Field("level")
    private Integer level; // 0, 1, 2, 3

    @Field("description")
    private String description; // e.g., "Three requirements are met"

    @Field("requirements")
    private java.util.List<String> requirements; // List of specific requirements
}
