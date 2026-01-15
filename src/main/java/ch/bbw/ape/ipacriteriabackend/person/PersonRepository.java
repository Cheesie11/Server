package ch.bbw.ape.ipacriteriabackend.person;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
    Person findByFirstnameAndLastname(String firstname, String lastname);
}

