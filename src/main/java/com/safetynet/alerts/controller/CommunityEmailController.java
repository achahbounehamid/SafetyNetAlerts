package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.CommunityEmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
/**
 * Récupère la liste des adresses e-mail de tous les habitants de la ville spécifiée.
 *
 * @param 'city la ville pour laquelle on souhaite obtenir la liste d'e-mails
 * @return une liste de chaînes de caractères représentant les adresses e-mail des habitants
 */

@RestController
/**
 * Contrôleur REST permettant de récupérer la liste des e-mails des habitants d'une ville donnée.
 */

public class CommunityEmailController {
    private static final Logger logger = LoggerFactory.getLogger(CommunityEmailController.class);
    private final CommunityEmailService communityEmailService;

    public CommunityEmailController(CommunityEmailService communityEmailService) {
        this.communityEmailService = communityEmailService;
    }

    @GetMapping("/communityEmail")
    public List<String> getEmailsByCity(@RequestParam String city) {
        // Log pour suivre la requête
        logger.info("Requête reçue pour les e-mails des habitants de la ville : {}", city);
        // Appel du service
        return communityEmailService.getEmailsByCity(city);
    }
}

