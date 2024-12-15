package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PhoneAlertDTO;
import com.safetynet.alerts.service.PhoneAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
/**
 * Classe de test pour le contrôleur {@link PhoneAlertController}.
 * Vérifie le bon fonctionnement des méthodes liées à la récupération des numéros de téléphone par numéro de caserne.
 */
class PhoneAlertControllerTest {

    @Mock
    private PhoneAlertService phoneAlertService;

    @InjectMocks
    private PhoneAlertController phoneAlertController;
    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    /**
     * Teste la récupération des numéros de téléphone via l'endpoint GET /personInfo
     * pour un numéro de caserne valide afin de s'assurer qu'elle retourne les numéros attendus.
     */
    @Test
    void testGetPhoneNumbers_ValidStation() {
        // Données simulées
        PhoneAlertDTO mockResponse = new PhoneAlertDTO(Arrays.asList("841-874-6512", "841-874-1234"));

        when(phoneAlertService.getPhoneNumbersByFirestation(3)).thenReturn(mockResponse);

        // Appel de la méthode
        PhoneAlertDTO result = phoneAlertController.getPhoneNumbers(3);

        // Vérifications
        assertEquals(2, result.getPhoneNumbers().size(), "Le nombre de numéros doit être 2.");
        assertEquals("841-874-6512", result.getPhoneNumbers().get(0));
        assertEquals("841-874-1234", result.getPhoneNumbers().get(1));
    }
    /**
     * Teste la récupération des numéros de téléphone via l'endpoint GET /personInfo
     * pour un numéro de caserne invalide afin de s'assurer qu'elle retourne une liste vide.
     */
    @Test
    void testGetPhoneNumbers_InvalidStation() {
        // Données simulées
        PhoneAlertDTO mockResponse = new PhoneAlertDTO(Collections.emptyList());

        when(phoneAlertService.getPhoneNumbersByFirestation(99)).thenReturn(mockResponse);

        // Appel de la méthode
        PhoneAlertDTO result = phoneAlertController.getPhoneNumbers(99);

        // Vérifications
        assertEquals(0, result.getPhoneNumbers().size(), "Le résultat doit être une liste vide pour une station invalide.");
    }
}

