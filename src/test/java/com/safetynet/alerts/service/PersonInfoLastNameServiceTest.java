package com.safetynet.alerts.service;

import com.safetynet.alerts.SafetyNetAlertsApplication;
import com.safetynet.alerts.dto.PersonInfoLastNameDTO;
import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Classe de test pour le service {@link PersonInfoLastNameService}.
 * Vérifie le bon fonctionnement des méthodes liées à la récupération des informations des personnes par nom de famille.
 */
@SpringBootTest(classes = SafetyNetAlertsApplication.class)
@AutoConfigureMockMvc
public class PersonInfoLastNameServiceTest {

    @MockBean
    private DataService dataService;

    @Autowired
    private PersonInfoLastNameService personInfoLastNameService;
    /**
     * Teste la méthode {@link PersonInfoLastNameService#getPersonByLastName(String)}
     * pour un nom de famille valide afin de s'assurer qu'elle retourne les personnes correspondantes avec les informations correctes.
     */
    @Test
    public void testGetPersonByLastName() {
        // Nom de famille correspondant au fichier JSON
        String lastName = "Boyd";

        // Données simulées basées sur le fichier JSON
        List<Person> mockPersons = List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john.boyd@example.com"),
                new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "roger.boyd@example.com")
        );

        List<MedicalRecord> mockMedicalRecords = List.of(
                new MedicalRecord("John", "Boyd", "03/06/1984", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")),
                new MedicalRecord("Roger", "Boyd", "09/06/2017", List.of(), List.of("peanuts"))
        );

        DataWrapper mockData = new DataWrapper(mockPersons, List.of(), mockMedicalRecords);
        when(dataService.getData()).thenReturn(mockData);

        // Appel de la méthode à tester
        List<PersonInfoLastNameDTO> result = personInfoLastNameService.getPersonByLastName(lastName);

        // Vérifications des résultats
        assertEquals(2, result.size()); // Vérifie que deux personnes sont trouvées
        assertEquals("John", result.get(0).getFirstName());
        assertEquals(40, result.get(0).getAge()); // Calcule l'âge correct
        assertEquals("Roger", result.get(1).getFirstName());
        assertTrue(result.get(1).getAllergies().contains("peanuts"));

        // Vérification de l'appel du service
        verify(dataService, times(1)).getData();
    }

}

