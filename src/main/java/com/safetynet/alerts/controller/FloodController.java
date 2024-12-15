package com.safetynet.alerts.controller;


import com.safetynet.alerts.dto.FloodResponseDTO;
import com.safetynet.alerts.service.FloodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * Contrôleur REST permettant de récupérer des informations sur la situation d'inondation
 * pour une ou plusieurs stations de pompiers.
 */

@RestController

public class FloodController {
    private final FloodService floodService;
    private static final Logger logger = LoggerFactory.getLogger(FloodController.class);

    public FloodController(FloodService floodService) {
        this.floodService = floodService;
    }

    @GetMapping("/flood/stations")
    /**
     * Récupère les informations liées à l'inondation pour les stations spécifiées.
     *
     * @param stations une liste d'identifiants de stations pour lesquelles obtenir les informations d'inondation
     * @return une liste de {@link FloodResponseDTO} contenant les informations sur les foyers potentiellement affectés par l'inondation
     */
    public List<FloodResponseDTO> getFloodInfo(@RequestParam List<Integer> stations) {
        logger.info("Requête reçue pour les stations : {}", stations);
        return floodService.getFloodInfo(stations);
    }
}

