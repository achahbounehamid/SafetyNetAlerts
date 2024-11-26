package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;
import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildAlertService {
    private static final Logger logger = LoggerFactory.getLogger(ChildAlertService.class);
    private final DataService dataService;

    public ChildAlertService(DataService dataService) {
        this.dataService = dataService;
    }

    public ChildAlertResponseDTO getChildrenAtAddress(String address) {
        // Récupère les données JSON
        DataWrapper data = dataService.getData();

        logger.info("Recherche des enfants pour l'adresse : {}", address);

        // Filtre les personnes vivant à l'adresse donnée
        List<Person> personsAtAddress = data.getPersons().stream()
                .filter(person -> person.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());

        // Sépare les enfants et les adultes
        List<ChildDTO> children = personsAtAddress.stream()
                .filter(person -> isChild(data.getMedicalRecords(), person))
                .map(person -> {
                    MedicalRecord medicalRecord = findMedicalRecord(data.getMedicalRecords(), person);
                    int age = calculateAge(medicalRecord.getBirthdate());
                    return new ChildDTO(person.getFirstName(), person.getLastName(), age);
                })
                .collect(Collectors.toList());

        List<String> otherHouseholdMembers = personsAtAddress.stream()
                .filter(person -> !isChild(data.getMedicalRecords(), person)) // Récupère les adultes
                .map(person -> person.getFirstName() + " " + person.getLastName())
                .collect(Collectors.toList());

        // Retourne le DTO avec les enfants et les autres membres
        return new ChildAlertResponseDTO(children, otherHouseholdMembers);
    }

    // Vérifie si une personne est un enfant
    private boolean isChild(List<MedicalRecord> medicalRecords, Person person) {
        MedicalRecord medicalRecord = findMedicalRecord(medicalRecords, person);
        return medicalRecord != null && calculateAge(medicalRecord.getBirthdate()) <= 18;
    }

    // Trouve le dossier médical correspondant à une personne
    private MedicalRecord findMedicalRecord(List<MedicalRecord> medicalRecords, Person person) {
        return medicalRecords.stream()
                .filter(medicalRecord ->
                        medicalRecord.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                                medicalRecord.getLastName().equalsIgnoreCase(person.getLastName()))
                .findFirst()
                .orElse(null);
    }

    // Calcule l'âge à partir de la date de naissance
    private int calculateAge(String birthdate) {
        LocalDate birthDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
