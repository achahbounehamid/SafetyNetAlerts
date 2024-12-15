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
        findByAddress(fireStationCRUD.getAddress())
                .ifPresent(existing -> fireStations.remove(existing));
        fireStations.add(fireStationCRUD);
        return fireStationCRUD;
    }
    /**
     * Recherche une station de pompiers par son adresse.
     *
     * @param address l'adresse de la station à rechercher
     * @return un {@link Optional} contenant la station si trouvée, ou un {@link Optional#empty()} sinon
     */
    public Optional<FireStationCRUD> findByAddress(String address) {
        return fireStations.stream().filter(f-> f.getAddress().equalsIgnoreCase(address)).findFirst();
    }
    /**
     * Met à jour l'adresse d'une station de pompiers existante.
     *
     * @param currentAddress l'adresse actuelle de la station de pompiers
     * @param newAddress la nouvelle adresse à affecter à la station
     * @return {@code true} si la mise à jour a réussi (station trouvée), {@code false} sinon
     */
    public boolean updateAddress(String currentAddress, String newAddress) {
        Optional<FireStationCRUD> fireStation = findByAddress(currentAddress);
        fireStation.ifPresent(f -> f.setAddress(newAddress));
        return fireStation.isPresent();
    }
    /**
     * Supprime une station de pompiers en fonction de son adresse.
     *
     * @param address l'adresse de la station à supprimer
     * @return {@code true} si la station a été supprimée, {@code false} si aucune station correspondant à l'adresse n'a été trouvée
     */
    public boolean deleteByAddress(String address){
        return fireStations.removeIf(f -> f.getAddress().equalsIgnoreCase(address));
    }
    /**
     * Récupère la liste de toutes les stations de pompiers.
     *
     * @return une liste de toutes les {@link FireStationCRUD} enregistrées
     */
    public List<FireStationCRUD> findAll() {
        return  new ArrayList<>(fireStations);
    }
}
