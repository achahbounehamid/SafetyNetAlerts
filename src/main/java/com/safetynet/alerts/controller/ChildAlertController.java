package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;
import com.safetynet.alerts.service.ChildAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChildAlertController {

    private final ChildAlertService childAlertService;
    private static final Logger logger = LoggerFactory.getLogger(ChildAlertController.class);

    public ChildAlertController(ChildAlertService childAlertService) {
        this.childAlertService = childAlertService;
    }

    @GetMapping("/childAlert")
    public ChildAlertResponseDTO getChildAlert(@RequestParam String address) {
        logger.info("Requête reçue pour les enfants habitant à l'adresse : {}", address);
        return childAlertService.getChildrenAtAddress(address);
    }
}

