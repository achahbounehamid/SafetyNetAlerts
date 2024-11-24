package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.dto.FirestationResponseDTO;
import com.safetynet.alerts.model.Firestation;

import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FirestationService {
    private final List<Person> persons;


    private final List<Firestation> firstations;
    private final List<MedicalRecord> medicalRecords;
    public  FirestationService(List<Person> persons, List<Firestation> firstations,List<MedicalRecord> medicalRecords){
        this.persons = persons;
        this.firstations = firstations;
        this.medicalRecords = medicalRecords;
    }
//    public FirestationResponseDTO getPersonByStation (int stationNumber){
//     // 1. Filtrer les adresses desservies par cette caserne
//
//
//
//        // 2. Filtrer les personnes vivant à ces adresses
//
//
//
//        // 3. Calculer le nombre d'adultes et d'enfants
//
//        // 4. Créer et retourner le DTO de réponse
//    }
}
