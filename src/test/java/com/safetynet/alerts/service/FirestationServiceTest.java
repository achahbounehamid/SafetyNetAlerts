package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FirestationResponseDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
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
 * Classe de test pour le service {@link FirestationService}.
 * Vérifie le bon fonctionnement des méthodes liées à la récupération des informations de personnes par numéro de caserne.
 */
class FirestationServiceTest {

    @Mock
    private DataService dataService;

    private FirestationService firestationService;
    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        firestationService = new FirestationService(dataService);
    }
    /**
     * Teste la méthode {@link FirestationService#getPersonsByStation(int)}
     * pour un numéro de caserne valide afin de s'assurer qu'elle retourne les informations attendues.
     */
    @Test
    void testGetPersonsByStation_ValidStation() {
        // Données simulées
        List<Firestation> mockFirestations = Arrays.asList(
                new Firestation("1509 Culver St", "3")
        );

        List<Person> mockPersons = Arrays.asList(
                new Person("John", "Boyd", "1509 Culver St", "Springfield", "97451", "841-874-6512", "jaboyd@email.com"),
                new Person("Jane", "Doe", "1509 Culver St", "Springfield", "97451", "841-874-6513", "janedoe@email.com")
        );

        List<MedicalRecord> mockMedicalRecords = Arrays.asList(
                new MedicalRecord("John", "Boyd", "03/06/1984", Collections.emptyList(), Collections.emptyList()),
                new MedicalRecord("Jane", "Doe", "05/12/2015", Collections.emptyList(), Collections.emptyList())
        );

        DataWrapper mockData = new DataWrapper(mockPersons, mockFirestations, mockMedicalRecords);
        when(dataService.getData()).thenReturn(mockData);

        // Appel de la méthode
        FirestationResponseDTO result = firestationService.getPersonsByStation(3);

        // Vérifications
        assertEquals(2, result.getPersons().size());
        assertEquals(1, result.getNumberOfChildren());
        assertEquals(1, result.getNumberOfAdults());
    }
    /**
     * Teste la méthode {@link FirestationService#getPersonsByStation(int)}
     * pour un numéro de caserne invalide afin de s'assurer qu'elle retourne une réponse vide.
     */
    @Test
    void testGetPersonsByStation_InvalidStation() {
        // Données simulées sans correspondance
        DataWrapper mockData = new DataWrapper(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        when(dataService.getData()).thenReturn(mockData);

        // Appel de la méthode
        FirestationResponseDTO result = firestationService.getPersonsByStation(99);

        // Vérifications
        assertEquals(0, result.getPersons().size());
        assertEquals(0, result.getNumberOfChildren());
        assertEquals(0, result.getNumberOfAdults());
    }
}

