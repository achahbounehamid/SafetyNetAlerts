package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStationCRUD;
import com.safetynet.alerts.repository.FireStationRepositoryCRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Service offrant des opérations CRUD (Create, Read, Update, Delete) pour les casernes de pompiers.
 * Il délègue les opérations au {@link FireStationRepositoryCRUD}.
 */
@Service
public class FireStationServiceCRUD {
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
     * Met à jour l'adresse d'une caserne de pompiers existante.
     *
     * @param currentAddress l'adresse actuelle de la caserne
     * @param newAddress la nouvelle adresse à attribuer à la caserne
     * @return {@code true} si la mise à jour a réussi, {@code false} sinon
     */
    public boolean updateAddress(String currentAddress, String newAddress) {
        return fireStationRepositoryCRUD.updateAddress(currentAddress, newAddress);
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
