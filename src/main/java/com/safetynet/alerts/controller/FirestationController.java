package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FirestationResponseDTO;
import com.safetynet.alerts.service.FirestationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * Contrôleur REST permettant de récupérer des informations sur les personnes couvertes par une caserne de pompiers donnée.
 */

@RestController // Déclare cette classe comme un contrôleur REST
public class  FirestationController {

    private final FirestationService firestationService; // Service pour les casernes
    private static final Logger logger = LoggerFactory.getLogger(FirestationController.class); // Logger pour les requêtes

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService; // Injection du service FirestationService
    }

    // Endpoint pour récupérer les personnes par numéro de caserne
    @GetMapping("/firestation")
    /**
     * Récupère une liste de personnes ainsi que le nombre d'adultes et d'enfants couverts par une caserne de pompiers spécifique.
     *
     * @param stationNumber le numéro de la station de pompiers pour laquelle on souhaite obtenir les informations
     * @return une réponse HTTP contenant un objet {@link FirestationResponseDTO} avec les informations,
     *         ou un statut 404 si aucune personne n’est trouvée
     */

    public ResponseEntity<FirestationResponseDTO> getPersonsByStationNumber(@RequestParam int stationNumber) {
        logger.info("Requête reçue pour /firestation avec stationNumber={}", stationNumber);

        FirestationResponseDTO response = firestationService.getPersonsByStation(stationNumber); // Appelle le service

        if (response.getPersons().isEmpty()) {
            logger.warn("Aucune personne trouvée pour la station {}", stationNumber);
            return ResponseEntity.notFound().build(); // Retourne 404 si aucune personne trouvée
        }

        return ResponseEntity.ok(response); // Retourne 200 avec la réponse
    }
}
