package com.safetynet.alerts.controller;
import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.service.FireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
/**
 * Classe de test pour le contrôleur {@link FireController}.
 * Vérifie le bon fonctionnement de l'endpoint {@code /fire}.
 */
class FireControllerTest {
    @Mock
    private FireService fireService;

    @InjectMocks
    private FireController fireController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    /**
     * Teste la méthode {@link FireController#getFireInfo(String)} pour s'assurer qu'elle retourne les données attendues.
     */
    @Test
    void testGetFireInfo() {
        // Données simulées
        String address = "123 Main St";
        List<FireDTO> mockFireDTOs = Arrays.asList(
                new FireDTO("John", "Doe", 33, "123-456-7890", Arrays.asList("Aspirin"), Arrays.asList("Peanuts"), "1")
        );
        // Simuler le comportement du service
        when(fireService.getFireInfo(address)).thenReturn(mockFireDTOs);
        // Appel de la méthode du contrôleur
        List<FireDTO> result = fireController.getFireInfo(address);
        // Vérification
        assertEquals(mockFireDTOs, result, "Les données retournées par le contrôleur doivent correspondre à celles du service.");
    }
}

