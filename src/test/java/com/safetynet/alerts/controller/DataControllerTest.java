package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.service.DataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
/**
 * Classe de test pour le contrôleur {@link DataController}.
 * Vérifie le bon fonctionnement de l'endpoint {@code /data}.
 */
class DataControllerTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private DataController dataController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    /**
     * Teste la méthode {@link DataController#getData()} pour s'assurer qu'elle renvoie des données non nulles.
     *
     * @throws Exception en cas d'erreur lors de l'exécution de la requête
     */
    @Test
    void testGetData() {
        // Mock des données retournées par le service
        DataWrapper mockData = new DataWrapper(
                java.util.Collections.emptyList(),
                java.util.Collections.emptyList(),
                java.util.Collections.emptyList()
        );
        when(dataService.getData()).thenReturn(mockData);

        // Appel de la méthode du contrôleur
        DataWrapper result = dataController.getData();

        // Vérification
        assertNotNull(result, "Les données retournées par le contrôleur ne doivent pas être nulles.");
    }
}

