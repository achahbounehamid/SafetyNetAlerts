package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataWrapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
/**
 * Service chargé de charger et de fournir les données issues du fichier JSON.
 * Les données sont chargées au démarrage de l'application et stockées en mémoire.
 */
@Service
public class DataService {
    private static final Logger logger = LoggerFactory.getLogger(DataService.class);
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
    private DataWrapper dataWrapper; // Stocke les données chargées

    public DataService(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    // Méthode appelée automatiquement après l'initialisation du bean
    @PostConstruct
    public void init() {
        try {
            logger.info("Chargement des données JSON au démarrage de l'application...");
            this.dataWrapper = loadData();
            logger.info("Données JSON chargées avec succès !");
        } catch (IOException e) {
            logger.error("Erreur lors du chargement des données JSON : {}", e.getMessage());
            throw new RuntimeException("Impossible de charger le fichier JSON.", e);
        }
    }

    // Charge les données JSON depuis le fichier
    private DataWrapper loadData() throws IOException {
        logger.info("Chargement du fichier JSON...");
        Resource resource = resourceLoader.getResource("classpath:data.json");
        logger.info("Chemin du fichier : {}", resource.getFilename());
        return objectMapper.readValue(resource.getInputStream(), DataWrapper.class);
    }
    /**
     * Récupère l'ensemble des données chargées en mémoire depuis le fichier JSON.
     *
     * @return un objet {@link DataWrapper} contenant toutes les données chargées
     */
    public DataWrapper getData() {
        return dataWrapper; // Retourne les données chargées en mémoire
    }
}



