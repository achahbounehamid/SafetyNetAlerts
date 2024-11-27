package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireDTO;

import com.safetynet.alerts.service.FireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FireController {

    private final FireService fireService;
    private static final Logger logger = LoggerFactory.getLogger(FireController.class);

    public FireController(FireService fireService) {
        this.fireService = fireService;
    }

    @GetMapping("/fire")
    public List<FireDTO> getFireInfo(@RequestParam String address) {
        logger.info("Requête reçue pour l'adresse : {}", address);
        return fireService.getFireInfo(address);
    }
}

