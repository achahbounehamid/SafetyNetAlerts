package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.FloodResponseDTO;
import com.safetynet.alerts.service.FloodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
/**
 * Classe de test pour le contrôleur {@link FloodController}.
 * Vérifie le bon fonctionnement des méthodes de l'endpoint {@code /flood}.
 */
class FloodControllerTest {

    @Mock
    private FloodService floodService;

    @InjectMocks
    private FloodController floodController;

    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    /**
     * Teste la méthode {@link FloodController#getFloodInfo(List)}
     * pour des numéros de casernes valides afin de s'assurer qu'elle retourne les données attendues.
     */
    @Test
    void testGetFloodInfo_ValidStations() {
        // Simulation des données
        List<FloodResponseDTO> mockResponse = Arrays.asList(
                new FloodResponseDTO("1509 Culver St", Arrays.asList(
                        new FloodDTO("Boyd", "John", Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), Arrays.asList("nillacilan"), "841-874-6512", 40, "1509 Culver St")
                )),
                new FloodResponseDTO("29 4th Ave", Arrays.asList(
                        new FloodDTO("Doe", "Jane", Arrays.asList("ibuprofen:200mg"), Collections.emptyList(), "841-874-1234", 33, "29 4th Ave")
                ))
        );
        // Simulation du comportement du service
        when(floodService.getFloodInfo(Arrays.asList(3))).thenReturn(mockResponse);

        // Appel de la méthode à tester
        List<FloodResponseDTO> result = floodController.getFloodInfo(Arrays.asList(3));

        // Vérifications
        assertEquals(2, result.size(), "Le nombre de foyers doit être 2.");
        assertEquals("1509 Culver St", result.get(0).getAddress(), "L'adresse doit être correcte.");
        assertEquals("29 4th Ave", result.get(1).getAddress(), "L'adresse doit être correcte.");
    }
    @Test
    void testGetFloodInfo_InvalidStations() {
        // Simulation du comportement pour une station invalide
        when(floodService.getFloodInfo(Arrays.asList(99))).thenReturn(Collections.emptyList());

        // Appel de la méthode
        List<FloodResponseDTO> result = floodController.getFloodInfo(Arrays.asList(99));

        // Vérifications
        assertEquals(0, result.size(), "Le résultat doit être une liste vide pour des stations invalides.");
    }
}
