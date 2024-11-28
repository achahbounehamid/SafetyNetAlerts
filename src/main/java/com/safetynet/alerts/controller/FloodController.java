package com.safetynet.alerts.controller;


import com.safetynet.alerts.dto.FloodResponseDTO;
import com.safetynet.alerts.service.FloodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FloodController {
    private final FloodService floodService;
    private static final Logger logger = LoggerFactory.getLogger(FloodController.class);

    public FloodController(FloodService floodService) {
        this.floodService = floodService;
    }

    @GetMapping("/flood/stations")
    public List<FloodResponseDTO> getFloodInfo(@RequestParam List<Integer> stations) {
        logger.info("Requête reçue pour les stations : {}", stations);
        return floodService.getFloodInfo(stations);
    }
}

