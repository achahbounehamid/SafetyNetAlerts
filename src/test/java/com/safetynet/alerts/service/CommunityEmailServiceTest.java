package com.safetynet.alerts.service;

import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
/**
 * Classe de test pour le service {@link CommunityEmailService}.
 * Vérifie le bon fonctionnement des méthodes liées à la récupération des adresses e-mail des habitants d'une ville donnée.
 */
@SpringBootTest
class CommunityEmailServiceTest {

    @Mock
    private DataService dataService;

    private CommunityEmailService communityEmailService;
    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    void setUp() {
        // Initialisation des mocks
        MockitoAnnotations.openMocks(this);

        // Création de l'instance du service avec le mock injecté
        communityEmailService = new CommunityEmailService(dataService);
    }
    /**
     * Teste la méthode  CommunityEmailService#getEmailsByCity
     * pour une ville valide afin de s'assurer qu'elle retourne les e-mails des habitants correspondants.
     */
    @Test
    void testGetEmailsByCity() {
        // Données simulées
        String city = "Springfield";
        List<Person> mockPersons = Arrays.asList(
                new Person("John", "Doe", "123 Main St", city, "12345", "123-456-7890", "john.doe@example.com"),
                new Person("Jane", "Doe", "456 Elm St", city, "12345", "123-456-7891", "jane.doe@example.com"),
                new Person("Jake", "Doe", "789 Pine St", "OtherCity", "12345", "123-456-7892", "jake.doe@example.com")
        );
        List<Firestation> emptyFirestations = Collections.emptyList(); // Liste vide
        List<MedicalRecord> emptyMedicalRecords = Collections.emptyList();

        DataWrapper mockData = new DataWrapper(mockPersons, emptyFirestations, emptyMedicalRecords);

        // Comportement simulé pour le service de données
        when(dataService.getData()).thenReturn(mockData);

        // Appel de la méthode à tester
        List<String> result = communityEmailService.getEmailsByCity(city);

        // Résultat attendu
        List<String> expected = Arrays.asList("john.doe@example.com", "jane.doe@example.com");

        // Vérification
        assertEquals(expected, result, "Les e-mails retournés ne correspondent pas aux attentes.");
    }
}
