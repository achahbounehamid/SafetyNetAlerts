package com.safetynet.alerts.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
/**
 * Classe de test pour le service
 * Vérifie le bon fonctionnement des méthodes liées au chargement et à la récupération des données.
 */
@SpringBootTest
class DataServiceTest {

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private ObjectMapper objectMapper;

    private DataService dataService;
    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dataService = new DataService(resourceLoader, objectMapper);
    }
    /**
     * Teste la méthode  DataService#getData()
     * pour s'assurer qu'elle retourne les données correctement chargées.
     *
     * @throws IOException en cas d'erreur lors de la lecture du fichier
     */
    @Test
    void testLoadData() throws IOException {
        // Données JSON simulées
        String mockJson = """
            {
                "persons": [],
                "firestations": [],
                "medicalRecords": []
            }
            """;

        // Mock du fichier JSON
        Resource mockResource = org.mockito.Mockito.mock(Resource.class);
        when(resourceLoader.getResource("classpath:data.json")).thenReturn(mockResource);
        when(mockResource.getInputStream()).thenReturn(new ByteArrayInputStream(mockJson.getBytes()));

        // Mock du mapper JSON
        DataWrapper mockDataWrapper = new DataWrapper(
                java.util.Collections.emptyList(),
                java.util.Collections.emptyList(),
                java.util.Collections.emptyList()
        );
        when(objectMapper.readValue(mockResource.getInputStream(), DataWrapper.class)).thenReturn(mockDataWrapper);

        // Appel de la méthode privée loadData via init
        dataService.init();

        // Vérification des données chargées
        DataWrapper result = dataService.getData();
        assertNotNull(result, "Les données chargées ne doivent pas être nulles.");
    }
}
