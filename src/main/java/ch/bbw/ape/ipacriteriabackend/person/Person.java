package ch.bbw.ape.ipacriteriabackend.person;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "persons")

@Entity
@Table(name = "persons")
public class Person {

    @Id
    private String id;

    @Column(name = "lastname", nullable = false)
    @Field("lastname")
    private String lastname;

    @Column(name = "firstname", nullable = false)
    @Field("firstname")
    private String firstname;

    @Column(name = "subject", nullable = false)
    @Field("subject")
    private String subject;

    @Column(name = "date", nullable = false)
    @Field("date")
    private LocalDate date;
}
