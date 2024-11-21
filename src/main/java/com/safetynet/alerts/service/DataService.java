package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataWrapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DataService {
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public DataService(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    public DataWrapper loadData() throws IOException {
        // Charge le fichier JSON depuis le classpath
        Resource resource = resourceLoader.getResource("classpath:data.json");

        // Convertit le JSON en objets Java
        return objectMapper.readValue(resource.getInputStream(), DataWrapper.class);
    }
}
