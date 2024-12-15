package com.safetynet.alerts.service;
import com.safetynet.alerts.dto.ChildAlertResponseDTO;
import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
/**
 * Classe de test pour le service  ChildAlertService
 * Vérifie le bon fonctionnement des méthodes liées à la récupération des informations des enfants à une adresse donnée.
 */
@SpringBootTest
public class ChildAlertServiceTest {

    @MockBean
    private DataService dataService;

    @Autowired
    private ChildAlertService childAlertService;
    /**
     * Teste la méthode  ChildAlertService#getChildrenAtAddress
     * pour une adresse valide afin de s'assurer qu'elle retourne les enfants attendus et les autres membres du foyer.
     */
    @Test
    public void testGetChildrenAtAddress() {
        // Préparation des données simulées
        String address = "1509 Culver St";
        List<Person> mockPersons = List.of(
                new Person("Roger", "Boyd", address, "Culver", "97451", "841-874-6512", "roger.boyd@example.com"),
                new Person("Felicia", "Boyd", address, "Culver", "97451", "841-874-6512", "felicia.boyd@example.com"),
                new Person("Tenley", "Boyd", address, "Culver", "97451", "841-874-6513", "tenley.boyd@example.com")
        );

        List<MedicalRecord> mockMedicalRecords = List.of(
                new MedicalRecord("Roger", "Boyd", "03/06/1984", List.of(), List.of()),
                new MedicalRecord("Felicia", "Boyd", "04/06/1986", List.of(), List.of()),
                new MedicalRecord("Tenley", "Boyd", "02/18/2015", List.of(), List.of("peanuts"))
        );

        DataWrapper mockData = new DataWrapper(mockPersons, List.of(), mockMedicalRecords);
        when(dataService.getData()).thenReturn(mockData);

        // Exécution de la méthode
        ChildAlertResponseDTO result = childAlertService.getChildrenAtAddress(address);

        // Vérification des résultats
        assertEquals(1, result.getChildren().size());
        assertEquals("Tenley", result.getChildren().get(0).getFirstName());
        assertEquals(2, result.getOtherHouseholdMembers().size());
        assertTrue(result.getOtherHouseholdMembers().contains("Roger Boyd"));
        assertTrue(result.getOtherHouseholdMembers().contains("Felicia Boyd"));

        // Vérification que le service a été appelé
        verify(dataService, times(1)).getData();
    }
}


