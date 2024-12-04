package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStationCRUD;
import com.safetynet.alerts.repository.FireStationRepositoryCRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FireStationServiceCRUD {
    @Autowired
    private FireStationRepositoryCRUD fireStationRepositoryCRUD;

    public FireStationCRUD addFireStationCRUD(FireStationCRUD fireStationCRUD) {
        return fireStationRepositoryCRUD.save(fireStationCRUD);
    }
    public boolean updateAddress(String currentAddress, String newAddress) {
        return fireStationRepositoryCRUD.updateAddress(currentAddress, newAddress);
    }

    public boolean deleteFireStationByAddress(String address) {
        return fireStationRepositoryCRUD.deleteByAddress(address);
    }
    public List<FireStationCRUD> getAllFireStation(){
        return  fireStationRepositoryCRUD.findAll();
    }
}
