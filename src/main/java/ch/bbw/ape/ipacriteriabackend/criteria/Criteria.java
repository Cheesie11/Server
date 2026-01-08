package ch.bbw.ape.ipacriteriabackend.criteria;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Criteria {

    @Field("id")
    private String id;

    @Field("title")
    private String title;

    @Field("guidingQuestion")
    private String guidingQuestion;

    @Field("requirements")
    private String requirements;

    @Field("qualityLevels")
    private List<String> qualityLevels;
}
