package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStationCRUD;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Repository en mémoire pour gérer les opérations CRUD sur les entités {@link FireStationCRUD}.
 * Cette classe permet d'ajouter, de trouver, de mettre à jour et de supprimer des stations de pompiers.
 */

@Repository
public class FireStationRepositoryCRUD {

    private List<FireStationCRUD> fireStations = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(FireStationRepositoryCRUD.class);
    /**
     * Ajoute ou met à jour une {@link FireStationCRUD}.
     * Si une station avec la même adresse existe déjà, elle est remplacée.
     *
     * @param fireStationCRUD l'entité représentant la station de pompiers à sauvegarder
     * @return la station de pompiers sauvegardée
     */
    public FireStationCRUD save(FireStationCRUD fireStationCRUD) {
        logger.info("Tentative de sauvegarde de la station: {}", fireStationCRUD);
        findByAddress(fireStationCRUD.getAddress())
                .ifPresent(existing -> fireStations.remove(existing));
        logger.info("Station existante remplacée: {}");
        fireStations.add(fireStationCRUD);
        logger.info("Station sauvegardée avec succès: {}", fireStationCRUD);
        return fireStationCRUD;
    }
    /**
     * Recherche une station de pompiers par son adresse.
     *
     * @param address l'adresse de la station à rechercher
     * @return un {@link Optional} contenant la station si trouvée, ou un {@link Optional#empty()} sinon
     */
    public Optional<FireStationCRUD> findByAddress(String address) {
        logger.info("Recherche de la station avec l'adresse: {}", address);
        return fireStations.stream().filter(f-> f.getAddress().equalsIgnoreCase(address)).findFirst();
    }
    /**
     * Met à jour une station de pompiers en modifiant son adresse et/ou son numéro de caserne dans le dépôt.
     *
     * @param currentAddress l'adresse actuelle de la station de pompiers
     * @param updatedFireStation un objet contenant les nouvelles valeurs pour l'adresse et/ou le numéro de caserne
     * @return {@code true} si la mise à jour a réussi (station trouvée et modifiée), {@code false} sinon
     */
    public boolean updateStation(String currentAddress, FireStationCRUD updatedFireStation) {
        logger.info("Début de la mise à jour dans le dépôt pour l'adresse actuelle : {}", currentAddress);

        // Parcours de la liste des stations pour trouver celle qui correspond à l'adresse actuelle
        for (FireStationCRUD fireStation : fireStations) {
            if (fireStation.getAddress().equalsIgnoreCase(currentAddress)) {
                // Mise à jour de l'adresse si une nouvelle adresse est spécifiée
                if (updatedFireStation.getAddress() != null) {
                    logger.info("Mise à jour de l'adresse dans le dépôt : {} -> {}", fireStation.getAddress(), updatedFireStation.getAddress());
                    fireStation.setAddress(updatedFireStation.getAddress());
                }

                // Mise à jour du numéro de caserne si une valeur valide est spécifiée (> 0)
                if (updatedFireStation.getStationNumber() > 0) {
                    logger.info("Mise à jour du numéro de caserne dans le dépôt : {} -> {}", fireStation.getStationNumber(), updatedFireStation.getStationNumber());
                    fireStation.setStationNumber(updatedFireStation.getStationNumber());
                }

                logger.info("Mise à jour réussie dans le dépôt pour l'adresse actuelle : {}", currentAddress);
                return true; // Retourne true après une mise à jour réussie
            }
        }

        logger.warn("Aucune station trouvée dans le dépôt pour l'adresse : {}", currentAddress);
        return false; // Retourne false si aucune station n'a été trouvée
    }

    /**
     * Supprime une station de pompiers en fonction de son adresse.
     *
     * @param address l'adresse de la station à supprimer
     * @return {@code true} si la station a été supprimée, {@code false} si aucune station correspondant à l'adresse n'a été trouvée
     */
    public boolean deleteByAddress(String address){
        logger.info("Tentative de suppression de la station avec l'adresse: {}", address);
        return fireStations.removeIf(f -> f.getAddress().equalsIgnoreCase(address));
    }


    /**
     * Récupère la liste de toutes les stations de pompiers.
     *
     * @return une liste de toutes les {@link FireStationCRUD} enregistrées
     */
    public List<FireStationCRUD> findAll() {
        logger.info("Récupération de toutes les stations enregistrées. Nombre total: {}", fireStations.size());
        return  new ArrayList<>(fireStations);
    }
}
