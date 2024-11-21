package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.service.DataService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@RestController
public class DataController {

    private final DataService dataService; // Nom corrigé

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/data")
    public DataWrapper getData() throws IOException {
        return dataService.loadData();
    }
}
