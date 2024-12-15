package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PhoneAlertDTO;
import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.model.Firestation;
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
 * Classe de test pour le service {@link PhoneAlertService}.
 * Vérifie le bon fonctionnement des méthodes liées à la récupération des numéros de téléphone par numéro de caserne.
 */
class PhoneAlertServiceTest {

    @Mock
    private DataService dataService;

    private PhoneAlertService phoneAlertService;
    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        phoneAlertService = new PhoneAlertService(dataService);
    }
    /**
     * Teste la méthode {@link PhoneAlertService#getPhoneNumbersByFirestation(int)}
     * pour un numéro de caserne valide afin de s'assurer qu'elle retourne les numéros de téléphone attendus.
     */
    @Test
    void testGetPhoneNumbersByFirestation_ValidStation() {
        // Préparation des données simulées
        List<Firestation> mockFirestations = Arrays.asList(
                new Firestation("1509 Culver St", "3"),
                new Firestation("29 4th Ave", "3")
        );

        List<Person> mockPersons = Arrays.asList(
                new Person("John", "Boyd", "1509 Culver St", "Springfield", "97451", "841-874-6512", "john@example.com"),
                new Person("Jane", "Doe", "29 4th Ave", "Springfield", "97451", "841-874-1234", "jane@example.com")
        );

        DataWrapper mockData = new DataWrapper(mockPersons, mockFirestations, Collections.emptyList());
        when(dataService.getData()).thenReturn(mockData);

        // Appel de la méthode à tester
        PhoneAlertDTO result = phoneAlertService.getPhoneNumbersByFirestation(3);

        // Vérifications
        List<String> expectedPhoneNumbers = Arrays.asList("841-874-6512", "841-874-1234");
        assertEquals(expectedPhoneNumbers, result.getPhoneNumbers(), "Les numéros de téléphone doivent être corrects.");
    }
    /**
     * Teste la méthode {@link PhoneAlertService#getPhoneNumbersByFirestation(int)}
     * pour un numéro de caserne invalide afin de s'assurer qu'elle retourne une liste vide.
     */
    @Test
    void testGetPhoneNumbersByFirestation_InvalidStation() {
        // Préparation des données simulées sans correspondance
        DataWrapper mockData = new DataWrapper(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        when(dataService.getData()).thenReturn(mockData);

        // Appel de la méthode à tester
        PhoneAlertDTO result = phoneAlertService.getPhoneNumbersByFirestation(99);

        // Vérifications
        assertEquals(0, result.getPhoneNumbers().size(), "Le résultat doit être une liste vide pour une station invalide.");
    }
}

