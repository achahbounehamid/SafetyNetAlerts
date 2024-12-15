import com.safetynet.alerts.SafetyNetAlertsApplication;
import com.safetynet.alerts.dto.PersonInfoLastNameDTO;
import com.safetynet.alerts.service.PersonInfoLastNameService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Classe de test pour le contrôleur  PersonInfoLastNameController.
 * Vérifie le bon fonctionnement des méthodes liées à la récupération des informations de personnes par nom de famille.
 */
@SpringBootTest(classes = SafetyNetAlertsApplication.class)
@AutoConfigureMockMvc
public class PersonInfoLastNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonInfoLastNameService personInfoLastNameService;
    /**
     * Teste la récupération des personnes par nom de famille via l'endpoint GET /personInfo.
     * Vérifie que lorsque des personnes correspondant au nom de famille existent, elles sont retournées correctement.
     *
     * @throws Exception en cas d'erreur lors de la requête
     */
    @Test
    public void testGetPersonByLastName() throws Exception {
        // Préparation des données simulées
        String lastName = "Boyd";
        List<PersonInfoLastNameDTO> mockResponse = List.of(
                new PersonInfoLastNameDTO("John", "Boyd", "1509 Culver St", "john.boyd@example.com", 39,
                        List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")),
                new PersonInfoLastNameDTO("Roger", "Boyd", "1509 Culver St", "roger.boyd@example.com", 6,
                        List.of(), List.of("peanuts"))
        );

        // Simulation de la réponse du service
        when(personInfoLastNameService.getPersonByLastName(lastName)).thenReturn(mockResponse);

        // Exécution de la requête GET et vérification de la réponse
        mockMvc.perform(get("/personInfo")
                        .param("lastName", lastName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].age").value(39))
                .andExpect(jsonPath("$[1].firstName").value("Roger"))
                .andExpect(jsonPath("$[1].allergies[0]").value("peanuts"));

        // Vérification que le service a été appelé
        verify(personInfoLastNameService, times(1)).getPersonByLastName(lastName);
    }

}

