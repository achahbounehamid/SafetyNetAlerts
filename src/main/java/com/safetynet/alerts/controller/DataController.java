package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/firestation")
public class DataController {

    private final DataService dataService;
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/data")
    public DataWrapper getData() {
        logger.info("Requête reçue pour récupérer les données JSON.");
        return dataService.getData(); // Renvoie les données chargées en mémoire
    }
}





