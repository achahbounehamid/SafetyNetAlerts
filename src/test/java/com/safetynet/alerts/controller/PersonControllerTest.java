import com.safetynet.alerts.SafetyNetAlertsApplication;
import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Classe de test pour le contrôleur {@link PersonController}.
 * Vérifie le bon fonctionnement des opérations CRUD dans le contrôleur PersonController.
 */
@SpringBootTest(classes = SafetyNetAlertsApplication.class)
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;
    /**
     * Teste l'ajout d'une personne via l'endpoint POST /person.
     * Vérifie que le service est appelé et que la réponse est conforme aux attentes.
     *
     * @throws Exception en cas d'erreur lors de la requête
     */
    @Test
    public void testAddPerson() throws Exception {
        Person person = new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com");

        when(personService.addPerson(any(Person.class))).thenReturn(person);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":\"123 Main St\",\"city\":\"City\",\"zip\":\"12345\",\"phone\":\"123-456-7890\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(personService, times(1)).addPerson(any(Person.class));
    }
    /**
     * Teste la mise à jour d'une personne existante via l'endpoint PUT /person/{firstName}/{lastName}.
     * Vérifie que lorsque la personne existe, elle est mise à jour avec succès.
     *
     * @throws Exception en cas d'erreur lors de la requête
     */
    @Test
    public void testUpdatePerson() throws Exception {
        Person updatedPerson = new Person("John", "Doe", "456 Elm St", "City", "67890", "987-654-3210", "john.new@example.com");

        when(personService.updatePerson(eq("John"), eq("Doe"), any(Person.class))).thenReturn(updatedPerson);

        mockMvc.perform(put("/person/John/Doe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"address\":\"456 Elm St\",\"city\":\"City\",\"zip\":\"67890\",\"phone\":\"987-654-3210\",\"email\":\"john.new@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("456 Elm St"))
                .andExpect(jsonPath("$.phone").value("987-654-3210"));

        verify(personService, times(1)).updatePerson(eq("John"), eq("Doe"), any(Person.class));
    }
    /**
     * Teste la suppression d'une personne existante via l'endpoint DELETE /person/{firstName}/{lastName}.
     * Vérifie que lorsque la personne existe, elle est supprimée avec succès.
     *
     * @throws Exception en cas d'erreur lors de la requête
     */
    @Test
    public void testDeletePerson() throws Exception {
        when(personService.deletePerson("John", "Doe")).thenReturn(true);

        mockMvc.perform(delete("/person/John/Doe"))
                .andExpect(status().isNoContent());

        verify(personService, times(1)).deletePerson("John", "Doe");
    }
    /**
     * Teste la récupération de toutes les personnes via l'endpoint GET /person.
     * Vérifie que la liste des personnes retournée est conforme aux attentes.
     *
     * @throws Exception en cas d'erreur lors de la requête
     */
    @Test
    public void testGetAllPersons() throws Exception {
        List<Person> persons = List.of(
                new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com"),
                new Person("Jane", "Smith", "456 Elm St", "City", "67890", "987-654-3210", "jane.smith@example.com")
        );

        when(personService.getAllPersons()).thenReturn(persons);

        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"));

        verify(personService, times(1)).getAllPersons();
    }
}
