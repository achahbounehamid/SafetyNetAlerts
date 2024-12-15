package com.safetynet.alerts.service;
import com.safetynet.alerts.SafetyNetAlertsApplication;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/**
 * Classe de test pour le service {@link PersonService}.
 * Vérifie le bon fonctionnement des opérations CRUD sur les personnes.
 */
@SpringBootTest(classes = SafetyNetAlertsApplication.class)
@AutoConfigureMockMvc
public class PersonServiceTest {

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;
    /**
     * Teste la méthode {@link PersonService#addPerson(Person)}
     * pour s'assurer qu'elle ajoute correctement une personne.
     */
    @Test
    public void testAddPerson() {
        // Préparation des données simulées
        Person person = new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com");
        // Simulation du comportement du repository
        when(personRepository.save(any(Person.class))).thenReturn(person);
        // Appel de la méthode à tester
        Person result = personService.addPerson(person);
        // Vérifications
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(personRepository, times(1)).save(person);
    }
    /**
     * Teste la méthode {@link PersonService#updatePerson(String, String, Person)}
     * lorsqu'une personne existe et peut être mise à jour.
     */
    @Test
    public void testUpdatePerson() {
        // Préparation des données simulées
        Person existingPerson = new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com");
        Person updatedPerson = new Person("John", "Doe", "456 Elm St", "City", "67890", "987-654-3210", "john.new@example.com");
        // Simulation du comportement du repository
        when(personRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(existingPerson));
        // Appel de la méthode à tester
        Person result = personService.updatePerson("John", "Doe", updatedPerson);
        // Vérifications
        assertNotNull(result);
        assertEquals("456 Elm St", result.getAddress());
        verify(personRepository, times(1)).save(existingPerson);
    }
    /**
     * Teste la méthode {@link PersonService#deletePerson(String, String)}
     * pour s'assurer qu'elle supprime correctement une personne existante.
     */
    @Test
    public void testDeletePerson() {
        // Préparation des données simulées
        when(personRepository.deleteByFirstNameAndLastName("John", "Doe")).thenReturn(true);
        // Appel de la méthode à tester
        boolean result = personService.deletePerson("John", "Doe");
       // Vérifications
        assertTrue(result);
        verify(personRepository, times(1)).deleteByFirstNameAndLastName("John", "Doe");
    }
    /**
     * Teste la méthode {@link PersonService#getAllPersons()}
     * pour s'assurer qu'elle retourne correctement toutes les personnes.
     */
    @Test
    public void testGetAllPersons() {
        // Préparation des données simulées
        List<Person> persons = List.of(
                new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com"),
                new Person("Jane", "Smith", "456 Elm St", "City", "67890", "987-654-3210", "jane.smith@example.com")
        );
        // Simulation du comportement du repository
        when(personRepository.findAll()).thenReturn(persons);
        // Appel de la méthode à tester
        List<Person> result = personService.getAllPersons();
        // Vérifications
        assertEquals(2, result.size());
        verify(personRepository, times(1)).findAll();
    }
}
