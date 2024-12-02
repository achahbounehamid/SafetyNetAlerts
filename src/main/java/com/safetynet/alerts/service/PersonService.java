package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
    @Autowired
    private PersonRepository personRepository;

    // Ajouter une nouvelle personne
    public Person addPerson(Person person) {
        return personRepository.save(person);
    }

    // Mettre à jour une personne existante
    public Person updatePerson(String firstName, String lastName, Person updatedPerson) {
        Optional<Person> existingPerson = personRepository.findByFirstNameAndLastName(firstName, lastName);
        if (existingPerson.isPresent()) {
            Person person = existingPerson.get();
            person.setAddress(updatedPerson.getAddress());
            person.setCity(updatedPerson.getCity());
            person.setZip(updatedPerson.getZip());
            person.setPhone(updatedPerson.getPhone());
            person.setEmail(updatedPerson.getEmail());
            personRepository.save(person);
            logger.info("Person updated successfully: {}", person); // Log la personne mise à jour
            return person;
        }
        logger.warn("Person not found: {} {}", firstName, lastName); // Log si la personne n'est pas trouvée
        return null;
    }

    // Supprimer une personne
    public boolean deletePerson(String firstName, String lastName) {
        return personRepository.deleteByFirstNameAndLastName(firstName, lastName);
    }

    // Obtenir toutes les personnes
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
}
