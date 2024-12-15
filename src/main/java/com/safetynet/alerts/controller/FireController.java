package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireDTO;

import com.safetynet.alerts.service.FireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * Contrôleur REST permettant de récupérer des informations sur les incendies à une adresse donnée.
 */

@RestController
public class FireController {

    private final FireService fireService;
    private static final Logger logger = LoggerFactory.getLogger(FireController.class);

    public FireController(FireService fireService) {
        this.fireService = fireService;
    }

    @GetMapping("/fire")
    /**
     * Récupère la liste des informations relatives aux incendies pour une adresse spécifique.
     *
     * @param address l'adresse pour laquelle on souhaite obtenir les informations sur les incendies
     * @return une liste de {@link FireDTO} contenant les informations sur les incendies
     */

    public List<FireDTO> getFireInfo(@RequestParam String address) {
        logger.info("Requête reçue pour l'adresse : {}", address);
        return fireService.getFireInfo(address);
    }
}

