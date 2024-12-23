package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStationCRUD;
import com.safetynet.alerts.repository.FireStationRepositoryCRUD;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Service offrant des opérations CRUD (Create, Read, Update, Delete) pour les casernes de pompiers.
 * Il délègue les opérations au {@link FireStationRepositoryCRUD}.
 */
@Service
public class FireStationServiceCRUD {
    private static final Logger logger = LoggerFactory.getLogger(FireStationServiceCRUD.class);
    @Autowired
    private FireStationRepositoryCRUD fireStationRepositoryCRUD;
    /**
     * Ajoute une nouvelle caserne de pompiers.
     *
     * @param fireStationCRUD l'entité représentant la caserne à ajouter
     * @return la caserne ajoutée
     */
    public FireStationCRUD addFireStationCRUD(FireStationCRUD fireStationCRUD) {
        return fireStationRepositoryCRUD.save(fireStationCRUD);
    }
    /**
     * Recherche une station de pompiers par son adresse.
     *
     * @param address l'adresse de la station à rechercher
     * @return un Optional contenant la station si elle est trouvée, ou un Optional vide sinon
     */
    public Optional<FireStationCRUD> findByAddress(String address) {
        logger.info("Recherche de la station avec l'adresse : {}", address);
        return fireStationRepositoryCRUD.findByAddress(address);
    }

    /**
     * Met à jour une station de pompiers en modifiant son adresse et/ou son numéro de caserne.
     *
     * @param currentAddress l'adresse actuelle de la station de pompiers
     * @param updatedFireStation un objet contenant les nouvelles valeurs pour l'adresse et/ou le numéro de caserne
     * @return {@code true} si la mise à jour a réussi (station trouvée et modifiée), {@code false} sinon
     */
    public boolean updateStation(String currentAddress, FireStationCRUD updatedFireStation) {
        logger.info("Début de la mise à jour de la station avec l'adresse actuelle : {}", currentAddress);

        // Recherche de la station correspondant à l'adresse actuelle
        Optional<FireStationCRUD> fireStation = findByAddress(currentAddress);

        if (fireStation.isEmpty()) {
            logger.warn("Aucune station trouvée pour l'adresse : {}", currentAddress);
            return false; // Retourne false si aucune station n'a été trouvée
        }

        fireStation.ifPresent(f -> {
            // Mise à jour de l'adresse si une nouvelle adresse est spécifiée
            if (updatedFireStation.getAddress() != null) {
                logger.info("Mise à jour de l'adresse : {} -> {}", f.getAddress(), updatedFireStation.getAddress());
                f.setAddress(updatedFireStation.getAddress());
            }

            // Mise à jour du numéro de caserne si une valeur valide est spécifiée (> 0)
            if (updatedFireStation.getStationNumber() > 0) {
                logger.info("Mise à jour du numéro de caserne : {} -> {}", f.getStationNumber(), updatedFireStation.getStationNumber());
                f.setStationNumber(updatedFireStation.getStationNumber());
            }
        });

        logger.info("Mise à jour réussie pour l'adresse actuelle : {}", currentAddress);
        return true;
    }

    /**
     * Supprime une caserne de pompiers en fonction de son adresse.
     *
     * @param address l'adresse de la caserne à supprimer
     * @return {@code true} si la suppression a réussi, {@code false} sinon
     */
    public boolean deleteFireStationByAddress(String address) {
        return fireStationRepositoryCRUD.deleteByAddress(address);
    }


    /**
     * Récupère la liste de toutes les casernes de pompiers.
     *
     * @return une liste de toutes les {@link FireStationCRUD} enregistrées
     */
    public List<FireStationCRUD> getAllFireStation(){
        return  fireStationRepositoryCRUD.findAll();
    }
}
