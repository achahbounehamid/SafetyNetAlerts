package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;



@RestController
@RequestMapping("/person")
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        logger.info("Received POST request to add a new person: {}", person);
        return ResponseEntity.ok(personService.addPerson(person));
    }



    @PutMapping("/{firstName}/{lastName}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody Person updatedPerson) {
        logger.info("Received PUT request for: {} {}", firstName, lastName);
        logger.info("Request body: {}", updatedPerson);
        Person person = personService.updatePerson(firstName, lastName, updatedPerson);
        if (person != null) {
            logger.info("Person updated successfully: {}", person);
            return ResponseEntity.ok(person);
        }
        logger.warn("Person not found: {} {}", firstName, lastName);
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable String firstName, @PathVariable String lastName) {
        logger.info("Received DELETE request to remove person: {} {}", firstName, lastName);
        if (personService.deletePerson(firstName, lastName)) {
            logger.info("Person deleted successfully: {} {}", firstName, lastName);
            return ResponseEntity.noContent().build();
        }
        logger.warn("Person not found for deletion: {} {}", firstName, lastName);
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        logger.info("Received GET request to fetch all persons.");
        return ResponseEntity.ok(personService.getAllPersons());
    }
}
