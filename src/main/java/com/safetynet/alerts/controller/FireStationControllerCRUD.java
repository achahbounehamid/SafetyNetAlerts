package com.safetynet.alerts.controller;


import com.safetynet.alerts.model.FireStationCRUD;
import com.safetynet.alerts.service.FireStationServiceCRUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/firestationCRUD")
public class FireStationControllerCRUD {
    private static final Logger logger = LoggerFactory.getLogger(FireStationControllerCRUD.class);

    @Autowired
    private FireStationServiceCRUD fireStationServiceCRUD;

    @PostMapping
    public ResponseEntity<FireStationCRUD> addFireStationCRUD(@RequestBody FireStationCRUD fireStationCRUD) {
        FireStationCRUD savedFireStationCRUD = fireStationServiceCRUD.addFireStationCRUD(fireStationCRUD);
        return ResponseEntity.ok(savedFireStationCRUD);
    }


    @PutMapping("/{address}")
    public ResponseEntity<Void> updateAddress(
            @PathVariable String address,
            @RequestParam String newAddress) {
        if (fireStationServiceCRUD.updateAddress(address, newAddress)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }



    @DeleteMapping("/{address}")
    public ResponseEntity<Void> deleteFireStationByAddress(@PathVariable String address) {
        if (fireStationServiceCRUD.deleteFireStationByAddress(address)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<FireStationCRUD>> getAllFireStationCRUD() {
        List<FireStationCRUD> fireStations = fireStationServiceCRUD.getAllFireStation();
        return ResponseEntity.ok(fireStations);
    }

}
