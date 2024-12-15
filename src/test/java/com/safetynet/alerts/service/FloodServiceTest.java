package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.FloodResponseDTO;
import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
/**
 * Classe de test pour le service {@link FloodService}.
 * Vérifie le bon fonctionnement des méthodes liées à la récupération des informations de crue par numéros de caserne.
 */
class FloodServiceTest {

    @Mock
    private DataService dataService;

    private FloodService floodService;
    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        floodService = new FloodService(dataService);
    }
    /**
     * Teste la méthode {@link FloodService#getFloodInfo(List)}
     * pour des numéros de casernes valides afin de s'assurer qu'elle retourne les informations de crue attendues.
     */
    @Test
    void testGetFloodInfo_ValidStations() {
        // Données simulées
        List<Firestation> mockFirestations = Arrays.asList(
                new Firestation("1509 Culver St", "3")
        );

        List<Person> mockPersons = Arrays.asList(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@example.com")
        );

        List<MedicalRecord> mockMedicalRecords = Arrays.asList(
                new MedicalRecord("John", "Boyd", "01/08/1986", Arrays.asList("xilliathal"), Arrays.asList("tetracyclaz:650mg"))
        );

        DataWrapper mockData = new DataWrapper(mockPersons, mockFirestations, mockMedicalRecords);
        when(dataService.getData()).thenReturn(mockData);

        // Appel de la méthode à tester
        List<FloodResponseDTO> result = floodService.getFloodInfo(Arrays.asList(3));

        // Vérifications
        assertEquals(1, result.size(), "Le nombre d'adresses doit être 1.");

        FloodResponseDTO address1 = result.get(0);
        assertEquals("1509 Culver St", address1.getAddress(), "L'adresse doit être correcte.");

        FloodDTO resident1 = address1.getResidents().get(0);
        assertEquals("John", resident1.getFirstName(), "Le prénom doit être John.");
        assertEquals(Arrays.asList("tetracyclaz:650mg"), resident1.getMedications(), "Les médicaments doivent être corrects.");
        assertEquals(Arrays.asList("xilliathal"), resident1.getAllergies(), "Les allergies doivent être correctes.");
    }
    /**
     * Teste la méthode {@link FloodService#getFloodInfo(List)}
     * pour des numéros de casernes invalides afin de s'assurer qu'elle retourne une liste vide.
     */
    @Test
    void testGetFloodInfo_InvalidStations() {
        // Données simulées sans correspondance
        DataWrapper mockData = new DataWrapper(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        when(dataService.getData()).thenReturn(mockData);

        // Appel de la méthode à tester
        List<FloodResponseDTO> result = floodService.getFloodInfo(Arrays.asList(99));

        // Vérifications
        assertEquals(0, result.size(), "Le résultat doit être une liste vide pour des stations invalides.");
    }
}
