package ch.bbw.ape.ipacriteriabackend.person;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ch.bbw.ape.ipacriteriabackend.criteria.Criteria;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "persons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    private String id;

    @Field("lastname")
    private String lastname;

    @Field("firstname")
    private String firstname;

    @Field("subject")
    private String subject;

    @Field("date")
    private LocalDate date;

    @Field("criteria")
    private List<Criteria> criteria;

}
