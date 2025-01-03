package com.safetynet.alerts.controller;


import com.safetynet.alerts.model.FireStationCRUD;
import com.safetynet.alerts.service.FireStationServiceCRUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST offrant des opérations CRUD (Create, Read, Update, Delete)
 * sur les entités {@link FireStationCRUD}.
 */

@RestController
@RequestMapping("/firestationCRUD")
public class FireStationControllerCRUD {
    private static final Logger logger = LoggerFactory.getLogger(FireStationControllerCRUD.class);

    @Autowired
    private FireStationServiceCRUD fireStationServiceCRUD;

    @PostMapping
    /**
     * Ajoute une nouvelle station de pompiers.
     *
     * @param fireStationCRUD l'entité représentant la nouvelle station de pompiers
     * @return une réponse HTTP contenant l'entité créée
     */
    public ResponseEntity<FireStationCRUD> addFireStationCRUD(@RequestBody FireStationCRUD fireStationCRUD) {
        FireStationCRUD savedFireStationCRUD = fireStationServiceCRUD.addFireStationCRUD(fireStationCRUD);
        return ResponseEntity.ok(savedFireStationCRUD);
    }
    @PutMapping("/{address}")
    /**
     * Met à jour l'adresse d'une station de pompiers existante.
     *
     * @param address l'adresse actuelle de la station
     * @param newAddress la nouvelle adresse à associer à la station
     * @return une réponse HTTP 200 si la mise à jour a réussi, 404 si l'adresse n'a pas été trouvée
     */
    public ResponseEntity<Void> updateStation(
            @PathVariable String address,
            @RequestBody FireStationCRUD updatedFireStation) {
        logger.info("Requête PUT reçue pour mettre à jour : {}", address);

        boolean updated = fireStationServiceCRUD.updateStation(address, updatedFireStation);
        if (updated) {
            logger.info("Station mise à jour avec succès : {} -> {}", address, updatedFireStation);
            return ResponseEntity.ok().build();
        }
        logger.warn("Adresse non trouvée : {}", address);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{address}")
    /**
     * Supprime une station de pompiers en fonction de son adresse.
     *
     * @param address l'adresse de la station à supprimer
     * @return une réponse HTTP 204 si la suppression a réussi, 404 sinon
     */
    public ResponseEntity<Void> deleteFireStationByAddress(@PathVariable String address) {
        logger.info("Requête DELETE reçue pour supprimer l'adresse: {}", address);
        if (fireStationServiceCRUD.deleteFireStationByAddress(address)) {
            logger.info("Adresse supprimée avec succès: {}", address);
            return ResponseEntity.noContent().build();
        }
        logger.warn("Adresse non trouvée pour suppression: {}", address);
        return ResponseEntity.notFound().build();

    }

    @GetMapping
    /**
     * Récupère la liste de toutes les stations de pompiers.
     *
     * @return une réponse HTTP 200 contenant la liste des entités {@link FireStationCRUD}
     */
    public ResponseEntity<List<FireStationCRUD>> getAllFireStationCRUD() {
        List<FireStationCRUD> fireStations = fireStationServiceCRUD.getAllFireStation();
        return ResponseEntity.ok(fireStations);
    }

}
