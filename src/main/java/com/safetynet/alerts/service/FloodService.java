package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.FloodResponseDTO;
import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.model.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FloodService {
    private final DataService dataService;
    private static final Logger logger = LoggerFactory.getLogger(FloodService.class);

    public FloodService(DataService dataService) {
        this.dataService = dataService;
    }
    public List<FloodResponseDTO> getFloodInfo(List<Integer> stationNumbers) {
        // Récupère les données JSON
        DataWrapper data = dataService.getData();

        logger.info("Recherche des foyers pour les stations : {}", stationNumbers);

        // Trouve les adresses associées aux numéros de stations
        List<String> addresses = data.getFirestations().stream()
                .filter(firestation -> stationNumbers.contains(Integer.parseInt(firestation.getStation())))
                .map(Firestation::getAddress)
                .collect(Collectors.toList());

        logger.info("Adresses trouvées : {}", addresses);

        // Regroupe les résidents par adresse
        List<FloodResponseDTO> result = addresses.stream()
                .map(address -> {
                    List<FloodDTO> residents = data.getPersons().stream()
                            .filter(person -> person.getAddress().equals(address))
                            .map(person -> {
                                // Recherche du dossier médical pour chaque personne
                                MedicalRecord record = data.getMedicalRecords().stream()
                                        .filter(medicalRecord -> medicalRecord.getFirstName().equals(person.getFirstName())
                                                && medicalRecord.getLastName().equals(person.getLastName()))
                                        .findFirst()
                                        .orElse(null);

                                return new FloodDTO(
                                        person.getLastName(),
                                        person.getFirstName(),
                                        record != null ? record.getMedications() : Collections.emptyList(),
                                        record != null ? record.getAllergies() : Collections.emptyList(),
                                        person.getPhone(),
                                        calculateAge(record != null ? record.getBirthdate() : null),
                                        person.getAddress()
                                );
                            })
                            .collect(Collectors.toList());

                    return new FloodResponseDTO(address, residents);
                })
                .collect(Collectors.toList());

        logger.info("Résultats pour les foyers : {}", result.size());
        return result;
    }

    // Méthode pour calculer l'âge
    private int calculateAge(String birthdate) {
        try {
            if (birthdate == null) return 0;
            LocalDate birthDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            return Period.between(birthDate, LocalDate.now()).getYears();
        } catch (DateTimeParseException e) {
            logger.error("Format de date invalide pour la date : {}", birthdate);
            return 0; // Retourne un âge par défaut en cas d'erreur
        }
    }
}
