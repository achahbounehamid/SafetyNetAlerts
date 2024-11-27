package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.model.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.safetynet.alerts.model.Firestation;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireService {

    private final DataService dataService;
    private static final Logger logger = LoggerFactory.getLogger(FireService.class);

    public FireService(DataService dataService) {
        this.dataService = dataService;
    }

    public List<FireDTO> getFireInfo(String address) {
        // Récupère les données JSON
        DataWrapper data = dataService.getData();

        logger.info("Recherche des habitants pour l'adresse : {}", address);

        // Trouve la caserne associée à l'adresse
        String stationNumber = data.getFirestations().stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .map(Firestation::getStation)
                .findFirst()
                .orElse(null);

        if (stationNumber == null) {
            logger.warn("Aucune station trouvée pour l'adresse {}", address);
            return Collections.emptyList();
        }

        logger.info("Station associée : {}", stationNumber);

        // Récupère les résidents à l'adresse
        return data.getPersons().stream()
                .filter(person -> person.getAddress().equals(address))
                .map(person -> {
                    MedicalRecord record = data.getMedicalRecords().stream()
                            .filter(medicalRecord -> medicalRecord.getFirstName().equals(person.getFirstName())
                                    && medicalRecord.getLastName().equals(person.getLastName()))
                            .findFirst()
                            .orElse(null);

                    return new FireDTO(
                            person.getFirstName(),
                            person.getLastName(),
                            calculateAge(record != null ? record.getBirthdate() : null),
                            person.getPhone(),
                            record != null ? record.getMedications() : Collections.emptyList(),
                            record != null ? record.getAllergies() : Collections.emptyList(),
                            stationNumber
                    );
                })
                .collect(Collectors.toList());
    }

    // Méthode pour calculer l'âge
    private int calculateAge(String birthdate) {
        if (birthdate == null) return 0;
        LocalDate birthDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}

