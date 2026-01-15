package ch.bbw.ape.ipacriteriabackend.session;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a criteria within a session
 * Based on the IPA structure (A01, A02, etc.)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionCriteria {

    @Field("id")
    private String id; // e.g., "A01"

    @Field("title")
    private String title; // e.g., "Auftragsanalyse und Wahl einer Projektmethode"

    @Field("guidingQuestion")
    private String guidingQuestion; // e.g., "Wie erfolgt die Auftragsanalyse?..."

    @Field("qualityLevels")
    private List<QualityLevel> qualityLevels; // List of Gütestufe 3, 2, 1, 0
}
