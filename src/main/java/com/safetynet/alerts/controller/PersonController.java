package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Contrôleur REST pour gérer les opérations CRUD sur les personnes.
 * Permet d'ajouter, de mettre à jour, de supprimer et de récupérer les personnes.
 */

@RestController
@RequestMapping("/person")
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @PostMapping
    /**
     * Ajoute une nouvelle personne.
     *
     * @param person l'objet {@link Person} à ajouter
     * @return une réponse HTTP 200 contenant la personne ajoutée
     */
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        logger.info("Received POST request to add a new person: {}", person);
        return ResponseEntity.ok(personService.addPerson(person));
    }

    @PutMapping("/{firstName}/{lastName}")
    /**
     * Met à jour une personne existante identifiée par son prénom et son nom.
     *
     * @param firstName le prénom de la personne à mettre à jour
     * @param lastName  le nom de la personne à mettre à jour
     * @param updatedPerson l'objet {@link Person} contenant les nouvelles informations
     * @return une réponse HTTP 200 contenant la personne mise à jour, ou 404 si la personne n'est pas trouvée
     */
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
    /**
     * Supprime une personne spécifiée par prénom et nom.
     *
     * @param firstName le prénom de la personne à supprimer
     * @param lastName  le nom de la personne à supprimer
     * @return une réponse HTTP 204 si la suppression a réussi, ou 404 si la personne n'est pas trouvée
     */
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
    /**
     * Récupère la liste de toutes les personnes.
     *
     * @return une réponse HTTP 200 contenant la liste de toutes les personnes
     */
    public ResponseEntity<List<Person>> getAllPersons() {
        logger.info("Received GET request to fetch all persons.");
        return ResponseEntity.ok(personService.getAllPersons());
    }
}
