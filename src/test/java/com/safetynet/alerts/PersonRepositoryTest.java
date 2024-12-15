
package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour le repository {@link PersonRepository}.
 * Vérifie le bon fonctionnement des opérations CRUD sur les personnes.
 */
public class PersonRepositoryTest {

    private PersonRepository personRepository;
    /**
     * Initialise le repository avant chaque test en y ajoutant une personne de base.
     */
    @BeforeEach
    public void setup() {
        personRepository = new PersonRepository();
        // Ajoute une personne de base pour chaque test
        Person basePerson = new Person(
                "John",
                "Doe",
                "123 Main St",
                "Culver",
                "97451",
                "841-874-6512",
                "john.doe@example.com"
        );
        personRepository.save(basePerson);
    }
    /**
     * Teste la méthode {@link PersonRepository#save(Person)}
     * pour s'assurer qu'elle enregistre correctement une nouvelle personne.
     */
    @Test
    public void testSave_newPerson() {
        // Préparation des données simulées
        Person newPerson = new Person(
                "Jane",
                "Smith",
                "456 Elm St",
                "Culver",
                "12345",
                "123-456-7890",
                "jane.smith@example.com"
        );
        // Simulation de l'enregistrement
        Person saved = personRepository.save(newPerson);
        // Vérifications
        assertNotNull(saved);
        assertEquals("Jane", saved.getFirstName());
        assertEquals("Smith", saved.getLastName());
        // Vérifie que l'objet est bien dans la liste
        Optional<Person> found = personRepository.findByFirstNameAndLastName("Jane", "Smith");
        assertTrue(found.isPresent());
    }
    /**
     * Teste la méthode {@link PersonRepository#save(Person)}
     * pour s'assurer qu'elle met à jour correctement une personne existante.
     */
    @Test
    public void testSave_updateExistingPerson() {
        // On modifie l'adresse de John Doe déjà présent
        Person updatedPerson = new Person(
                "John",
                "Doe",
                "789 Maple St",// Nouvelle adresse
                "Culver",
                "97451",
                "999-999-9999", // Nouveau numéro de téléphone
                "john.new@example.com" // Nouvel email
        );
       // Simulation de la mise à jour
        Person saved = personRepository.save(updatedPerson);
         // Vérifications
        assertNotNull(saved);
        assertEquals("John", saved.getFirstName());
        assertEquals("Doe", saved.getLastName());
        assertEquals("789 Maple St", saved.getAddress());

        // Vérifie que la personne existe et est mise à jour
        Optional<Person> found = personRepository.findByFirstNameAndLastName("John", "Doe");
        assertTrue(found.isPresent());
        assertEquals("789 Maple St", found.get().getAddress());
        assertEquals("999-999-9999", found.get().getPhone());
        assertEquals("john.new@example.com", found.get().getEmail());
    }
    /**
     * Teste la méthode {@link PersonRepository#findByFirstNameAndLastName(String, String)}
     * pour s'assurer qu'elle retourne correctement une personne existante.
     */
    @Test
    public void testFindByFirstNameAndLastName_found() {
        // Recherche de la personne de base
        Optional<Person> found = personRepository.findByFirstNameAndLastName("John", "Doe");
        assertTrue(found.isPresent(), "La personne doit être trouvée dans le repository.");
        assertEquals("John", found.get().getFirstName(), "Le prénom doit être 'John'.");
        assertEquals("Doe", found.get().getLastName(), "Le nom doit être 'Doe'.");
    }
    /**
     * Teste la méthode {@link PersonRepository#findByFirstNameAndLastName(String, String)}
     * pour s'assurer qu'elle retourne vide lorsqu'aucune personne ne correspond.
     */
    @Test
    public void testFindByFirstNameAndLastName_notFound() {
        // Recherche d'une personne inexistante
        Optional<Person> found = personRepository.findByFirstNameAndLastName("Jane", "Doe");
        assertFalse(found.isPresent(), "Aucune personne ne doit être trouvée pour ce nom.");
    }
    /**
     * Teste la méthode {@link PersonRepository#deleteByFirstNameAndLastName(String, String)}
     * pour s'assurer qu'elle supprime correctement une personne existante.
     */
    @Test
    public void testDeleteByFirstNameAndLastName_found() {
        // Suppression de la personne de base
        boolean deleted = personRepository.deleteByFirstNameAndLastName("John", "Doe");
        assertTrue(deleted, "La suppression de la personne doit réussir.");

        // Vérifie que John Doe n'existe plus
        Optional<Person> found = personRepository.findByFirstNameAndLastName("John", "Doe");
        assertFalse(found.isPresent(), "La personne ne doit plus être présente après suppression.");
    }
    /**
     * Teste la méthode {@link PersonRepository#deleteByFirstNameAndLastName(String, String)}
     * pour s'assurer qu'elle retourne false lorsqu'on tente de supprimer une personne inexistante.
     */
    @Test
    public void testDeleteByFirstNameAndLastName_notFound() {
        // Tentative de suppression d'une personne inexistante
        boolean deleted = personRepository.deleteByFirstNameAndLastName("Jane", "Smith");
        assertFalse(deleted, "La suppression doit échouer car la personne n'existe pas.");
    }
    /**
     * Teste la méthode {@link PersonRepository#findAll()}
     * pour s'assurer qu'elle retourne correctement toutes les personnes enregistrées.
     */
    @Test
    public void testFindAll() {
        // Récupère toutes les personnes après l'insertion initiale
        List<Person> allPersons = personRepository.findAll();
        // On a inséré John Doe dans le @BeforeEach, donc on doit avoir 1 résultat
        assertEquals(1, allPersons.size());

        // Ajoute une nouvelle personne pour voir comment évolue findAll
        personRepository.save(new Person(
                "Jane",
                "Smith",
                "456 Elm St",
                "Culver",
                "12345",
                "123-456-7890",
                "jane.smith@example.com"
        ));
        // Récupère toutes les personnes après l'ajout
        allPersons = personRepository.findAll();
        // Maintenant, on doit avoir 2 personnes
        assertEquals(2, allPersons.size());
    }
}

