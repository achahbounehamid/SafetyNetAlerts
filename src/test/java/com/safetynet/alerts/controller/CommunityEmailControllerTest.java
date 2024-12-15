package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.CommunityEmailService;
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
 * Classe de test pour le contrôleur {@link CommunityEmailController}.
 * Vérifie le bon fonctionnement de l'endpoint {@code /communityEmail}.
 */
class CommunityEmailControllerTest {

    @Mock
    private CommunityEmailService communityEmailService;

    @InjectMocks
    private CommunityEmailController communityEmailController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    /**
     * Teste l'endpoint {@code /communityEmail} pour vérifier que les e-mails des habitants
     * d'une ville spécifique sont correctement retournés.
     *
     * @throws Exception en cas d'erreur lors de l'exécution de la requête
     */
    void testGetEmailsByCity() {
        // Données simulées
        String city = "Springfield";
        List<String> mockEmails = Arrays.asList("john.doe@example.com", "jane.doe@example.com");

        // Comportement simulé pour le service
        when(communityEmailService.getEmailsByCity(city)).thenReturn(mockEmails);

        // Appel de la méthode du contrôleur
        List<String> result = communityEmailController.getEmailsByCity(city);

        // Vérification
        assertEquals(mockEmails, result, "Les e-mails retournés ne correspondent pas aux attentes.");
    }
}
