package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStationCRUD;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Repository
public class FireStationRepositoryCRUD {

    private List<FireStationCRUD> fireStations = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(FireStationRepositoryCRUD.class);

    //Ajouter un mapping
    public FireStationCRUD save(FireStationCRUD fireStationCRUD) {
        findByAddress(fireStationCRUD.getAddress())
                .ifPresent(existing -> fireStations.remove(existing));
        fireStations.add(fireStationCRUD);
        return fireStationCRUD;
    }
    //Trouver un mapping par adresse
    public Optional<FireStationCRUD> findByAddress(String address) {
        return fireStations.stream().filter(f-> f.getAddress().equalsIgnoreCase(address)).findFirst();
    }

    //Mettre à jour mapping par adress
    public boolean updateAddress(String currentAddress, String newAddress) {
        Optional<FireStationCRUD> fireStation = findByAddress(currentAddress);
        fireStation.ifPresent(f -> f.setAddress(newAddress));
        return fireStation.isPresent();
    }


    //Supprimer un maping par adress
    public boolean deleteByAddress(String address){
        return fireStations.removeIf(f -> f.getAddress().equalsIgnoreCase(address));

    }
    public List<FireStationCRUD> findAll() {
        return  new ArrayList<>(fireStations);
    }
}
