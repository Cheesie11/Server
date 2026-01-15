package ch.bbw.ape.ipacriteriabackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ch.bbw.ape.ipacriteriabackend.person.Person;
import ch.bbw.ape.ipacriteriabackend.person.PersonRepository;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final PersonRepository personRepository;

    public AuthService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void register(String firstname, String lastname) {
        logger.info("Registering user: {} {}", firstname, lastname);
        
        if (firstname == null || lastname == null) {
            logger.error("Firstname or lastname is null");
            throw new RuntimeException("Firstname and lastname must not be null");
        }

        Person existingPerson = personRepository.findByFirstnameAndLastname(firstname, lastname);

        if (existingPerson != null) {
            logger.warn("User already exists: {} {}", firstname, lastname);
            throw new RuntimeException("User already exists");
        }

        Person person = Person.builder()
                .firstname(firstname)
                .lastname(lastname)
                .build();

        logger.info("Saving user to database: {}", person);
        Person savedPerson = personRepository.save(person);
        logger.info("User saved successfully with ID: {}", savedPerson.getId());
    }

    public Person login(String firstname, String lastname) {
        if (firstname == null || lastname == null) {
            throw new RuntimeException("Firstname and lastname must not be null");
        }

        Person person = personRepository.findByFirstnameAndLastname(firstname, lastname);
        if (person == null) {
            throw new RuntimeException("Invalid credentials");
        }
        return person;
    }
}


