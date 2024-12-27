package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;
import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.service.ChildAlertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.mockito.Mockito.when;
/**
 * Classe de test pour le contrôleur ChildAlertController.
 * Vérifie que l'endpoint /childAlert retourne les données attendues.
 */

@SpringBootTest
@AutoConfigureMockMvc
public class   ChildAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChildAlertService childAlertService;

    @Test
    /**
     * Teste le endpoint /childAlert pour une adresse spécifique.
     * Vérifie que la réponse contient la liste des enfants et autres résidents attendus.
     *
     * @throws Exception en cas d'erreur lors de l'exécution de la requête
     */
    public void testGetChildAlert() throws Exception {
        // Préparation des données simulées
        String address = "1509 Culver St";
        ChildAlertResponseDTO mockResponse = new ChildAlertResponseDTO(
                List.of(new ChildDTO("Tenley", "Boyd", 8)),
                List.of("Roger Boyd", "Felicia Boyd")
        );

        // Simulation de la réponse du service
        when(childAlertService.getChildrenAtAddress(address)).thenReturn(mockResponse);

        // Exécution de la requête GET et vérification de la réponse
        mockMvc.perform(get("/childAlert")
                        .param("address", address)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children[0].firstName").value("Tenley"))
                .andExpect(jsonPath("$.children[0].age").value(8))
                .andExpect(jsonPath("$.otherHouseholdMembers[0]").value("Roger Boyd"))
                .andExpect(jsonPath("$.otherHouseholdMembers[1]").value("Felicia Boyd"));

        // Vérification que le service a été appelé
        verify(childAlertService, times(1)).getChildrenAtAddress(address);
    }
}


