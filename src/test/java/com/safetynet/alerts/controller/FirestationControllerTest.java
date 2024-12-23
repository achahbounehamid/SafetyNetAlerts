package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FirestationResponseDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.service.FirestationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import com.safetynet.alerts.model.FireStationCRUD;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


/**
 * Classe de test pour {@link FirestationController}.
 * Vérifie le bon fonctionnement du point de terminaison {@code /firestationCRUD}.
 */
class FirestationControllerTest {

    @Mock
    private FirestationService firestationService;

    @InjectMocks
    private FirestationController firestationController;
    /**
     * Teste la méthode {@link FirestationController#getPersonsByStationNumber(int)}
     * pour un numéro de poste de pompiers valide afin de garantir qu'elle renvoie les données attendues.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    /**
     * Teste la méthode {@link FirestationController#getPersonsByStationNumber(int)}
     * pour un numéro de poste de pompiers valide afin de garantir qu'elle renvoie les données attendues.
     */
    @Test
    void testGetPersonsByStationNumber_ValidStation() {
        // Données simulées
        FirestationResponseDTO mockResponse = new FirestationResponseDTO();
        mockResponse.setPersons(Arrays.asList(
                new PersonInfoDTO("John", "Boyd", "1509 Culver St", "841-874-6512"),
                new PersonInfoDTO("Jane", "Doe", "1509 Culver St", "841-874-6513")
        ));
        mockResponse.setNumberOfChildren(1);
        mockResponse.setNumberOfAdults(1);

        when(firestationService.getPersonsByStation(3)).thenReturn(mockResponse);

        // Appel de la méthode
        ResponseEntity<FirestationResponseDTO> response = firestationController.getPersonsByStationNumber(3);

        // Vérifications
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getPersons().size());
    }
    /**
     * Tests the {@link FirestationController#getPersonsByStationNumber(int)} method
     * for an invalid firestation number to ensure it returns a 404 status.
     */
    @Test
    void testGetPersonsByStationNumber_InvalidStation() {
        // Données simulées pour une station invalide
        FirestationResponseDTO mockResponse = new FirestationResponseDTO();
        mockResponse.setPersons(Collections.emptyList());

        when(firestationService.getPersonsByStation(99)).thenReturn(mockResponse);

        // Appel de la méthode
        ResponseEntity<FirestationResponseDTO> response = firestationController.getPersonsByStationNumber(99);

        // Vérifications
        assertEquals(404, response.getStatusCodeValue());
    }
    /**
     * Teste la méthode {@link FirestationController#
     * (String, FireStationCRUD)}
     * pour une mise à jour valide de l'adresse et/ou du numéro de caserne.
     */
    @Test
    void testUpdateStation_ValidRequest() {
        // Données simulées
        FireStationCRUD updatedFireStation = new FireStationCRUD("123 New St", 5);

        when(firestationService.updateStation("1509 Culver St", updatedFireStation)).thenReturn(true);

        // Appel de la méthode
        ResponseEntity<Void> response = firestationController.updateStation("1509 Culver St", updatedFireStation);

        // Vérifications
        assertEquals(200, response.getStatusCodeValue());
        verify(firestationService, times(1)).updateStation("1509 Culver St", updatedFireStation);
    }

    /**
     * Teste la méthode {@link FirestationController#updateStation(String, FireStationCRUD)}
     * pour une mise à jour invalide où l'adresse n'existe pas.
     */
    @Test
    void testUpdateStation_InvalidRequest() {
        // Données simulées
        FireStationCRUD updatedFireStation = new FireStationCRUD("123 New St", 5);

        when(firestationService.updateStation("Unknown Address", updatedFireStation)).thenReturn(false);

        // Appel de la méthode
        ResponseEntity<Void> response = firestationController.updateStation("Unknown Address", updatedFireStation);

        // Vérifications
        assertEquals(404, response.getStatusCodeValue());
        verify(firestationService, times(1)).updateStation("Unknown Address", updatedFireStation);
    }


}

