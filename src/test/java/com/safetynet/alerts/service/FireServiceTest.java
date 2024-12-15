package com.safetynet.alerts.service;

import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.dto.FireDTO;
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
 * Classe de test pour le service  FireService
 * Vérifie le bon fonctionnement des méthodes liées à la récupération des informations de feu par adresse.
 */
class FireServiceTest {

    @Mock
    private DataService dataService;

    private FireService fireService;
    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fireService = new FireService(dataService);
    }
    /**
     * Teste la méthode {@link FireService#getFireInfo(String)}
     * pour une adresse valide afin de s'assurer qu'elle retourne les informations de feu attendues.
     *
     * @throws Exception en cas d'erreur lors de l'exécution du test
     */
    @Test
    void testGetFireInfo_ValidAddress() {
        // Données simulées
        String address = "1509 Culver St";

        List<Firestation> mockFirestations = Arrays.asList(
                new Firestation("1509 Culver St", "3")
        );

        List<Person> mockPersons = Arrays.asList(
                new Person("John", "Boyd", "1509 Culver St", "Springfield", "97451", "841-874-6512", "jaboyd@email.com")
        );

        List<MedicalRecord> mockMedicalRecords = Arrays.asList(
                new MedicalRecord("John", "Boyd", "03/06/1984", Arrays.asList("aznol:350mg","hydrapermazol:100mg"), Arrays.asList("nillacilan"))
        );

        DataWrapper mockData = new DataWrapper(mockPersons, mockFirestations, mockMedicalRecords);
        when(dataService.getData()).thenReturn(mockData);

        // Appel de la méthode à tester
        List<FireDTO> result = fireService.getFireInfo(address);

        // Vérification des résultats
        assertEquals(1, result.size(), "Le nombre de résultats doit être 1.");
        FireDTO fireDTO = result.get(0);
        assertEquals("John", fireDTO.getFirstName());
        assertEquals("Boyd", fireDTO.getLastName());
        assertEquals(40, fireDTO.getAge()); // Assurez-vous que l'âge est correct en fonction de la date actuelle
        assertEquals("841-874-6512", fireDTO.getPhone());
        assertEquals(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), fireDTO.getMedications());
        assertEquals(Arrays.asList("nillacilan"), fireDTO.getAllergies());
        assertEquals("3", fireDTO.getStation());
    }
    /**
     * Teste la méthode {@link FireService#getFireInfo(String)}
     * pour une adresse invalide afin de s'assurer qu'elle retourne une liste vide.

     */
    @Test
    void testGetFireInfo_InvalidAddress() {
        // Données simulées avec une adresse non existante
        String address = "456 Unknown St";

        DataWrapper mockData = new DataWrapper(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        when(dataService.getData()).thenReturn(mockData);

        // Appel de la méthode à tester
        List<FireDTO> result = fireService.getFireInfo(address);

        // Vérification
        assertEquals(0, result.size(), "Le résultat doit être une liste vide pour une adresse invalide.");
    }
}
