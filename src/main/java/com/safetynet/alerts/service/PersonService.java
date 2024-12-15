package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Service permettant la gestion des opérations CRUD (Create, Read, Update, Delete)
 * sur les entités {@link Person}.
 * Il délègue les opérations au {@link PersonRepository}.
 */
@Service
public class PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
    @Autowired
    private PersonRepository personRepository;
    /**
     * Ajoute une nouvelle personne.
     *
     * @param person l'entité {@link Person} à ajouter
     * @return la personne ajoutée
     */
    public Person addPerson(Person person) {
        logger.info("Adding a new person: {}", person);
        return personRepository.save(person);
    }
    /**
     * Met à jour une personne existante identifiée par son prénom et son nom.
     *
     * @param firstName le prénom de la personne à mettre à jour
     * @param lastName le nom de la personne à mettre à jour
     * @param updatedPerson un objet {@link Person} contenant les nouvelles informations
     * @return la personne mise à jour si trouvée, sinon {@code null}
     */
    public Person updatePerson(String firstName, String lastName, Person updatedPerson) {
        logger.info("Attempting to update person: {} {}", firstName, lastName);
        Optional<Person> existingPerson = personRepository.findByFirstNameAndLastName(firstName, lastName);
        if (existingPerson.isPresent()) {
            Person person = existingPerson.get();
            logger.debug("Existing person found: {}", person);
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
    /**
     * Supprime une personne identifiée par son prénom et son nom.
     *
     * @param firstName le prénom de la personne à supprimer
     * @param lastName le nom de la personne à supprimer
     * @return {@code true} si la personne a été supprimée, {@code false} si elle n'a pas été trouvée
     */
    public boolean deletePerson(String firstName, String lastName) {
        logger.info("Attempting to delete person: {} {}", firstName, lastName);
        return personRepository.deleteByFirstNameAndLastName(firstName, lastName);
    }
    /**
     * Récupère la liste de toutes les personnes.
     *
     * @return une liste de toutes les {@link Person} enregistrées
     */
    public List<Person> getAllPersons() {
        logger.info("Fetching all persons.");
        return personRepository.findAll();
    }
}
