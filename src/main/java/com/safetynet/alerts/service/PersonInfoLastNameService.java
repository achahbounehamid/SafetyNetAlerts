package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfoLastNameDTO;
import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
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
public class PersonInfoLastNameService {
    private final DataService dataService;
    private static final Logger logger = LoggerFactory.getLogger(PersonInfoLastNameService.class);

    public PersonInfoLastNameService(DataService dataService) {
        this.dataService = dataService;
    }

    public List<PersonInfoLastNameDTO> getPersonByLastName(String lastName) {
        DataWrapper data = dataService.getData();

        logger.info("Recherche les informations des habitants avec le nom : {}", lastName);

        return data.getPersons().stream()
                .filter(person -> person.getLastName().equalsIgnoreCase(lastName)) // Filtre par nom de famille
                .map(person -> {
                    MedicalRecord record = data.getMedicalRecords().stream()
                            .filter(mr -> mr.getFirstName().equalsIgnoreCase(person.getFirstName())
                                    && mr.getLastName().equalsIgnoreCase(person.getLastName()))
                            .findFirst()
                            .orElse(null);

                    return new PersonInfoLastNameDTO(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getAddress(),
                            person.getEmail(),
                            calculateAge(record != null ? record.getBirthdate() : null),
                            record != null ? record.getMedications() : Collections.emptyList(),
                            record != null ? record.getAllergies() : Collections.emptyList()
                    );

                })
                .collect(Collectors.toList());
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