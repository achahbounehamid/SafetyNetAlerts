package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;
import com.safetynet.alerts.service.ChildAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * Récupère les informations sur les enfants vivant à l'adresse spécifiée.
 *
 * @param address l'adresse pour laquelle on souhaite obtenir la liste des enfants
 * @return un objet ChildAlertResponseDTO contenant les informations sur les enfants et les adultes
 */


@RestController
/**
 * Contrôleur REST chargé de fournir des informations sur les enfants résidant à une adresse.
 */

public class ChildAlertController {

    private final ChildAlertService childAlertService;
    private static final Logger logger = LoggerFactory.getLogger(ChildAlertController.class);

    public ChildAlertController(ChildAlertService childAlertService) {
        this.childAlertService = childAlertService;
    }

@GetMapping("/childAlert")
public ChildAlertResponseDTO getChildAlert(@RequestParam String address) {
    logger.info("Requête reçue pour les enfants habitant à l'adresse : {}", address);

    // Appelez le service une seule fois et stockez la réponse
    ChildAlertResponseDTO response = childAlertService.getChildrenAtAddress(address);

    logger.info("Réponse générée pour l'adresse {} : {}", address, response);
    return response; // Retournez directement la réponse stockée
}

}

