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
/**
 * Service permettant de récupérer des informations sur les enfants résidant à une adresse donnée.
 * Il fournit un DTO contenant la liste des enfants ainsi que celle des autres membres du foyer.
 */
@Service
public class ChildAlertService {
    private static final Logger logger = LoggerFactory.getLogger(ChildAlertService.class);
    private final DataService dataService;

    public ChildAlertService(DataService dataService) {
        this.dataService = dataService;
    }
    /**
     * Récupère les informations sur les enfants et les autres personnes habitant à l'adresse spécifiée.
     *
     * @param address l'adresse pour laquelle on souhaite obtenir la liste des enfants et des autres membres du foyer
     * @return un objet {@link ChildAlertResponseDTO} contenant la liste des enfants (avec âge, prénom, nom)
     *         et la liste des autres membres du foyer (adultes)
     */
    public ChildAlertResponseDTO getChildrenAtAddress(String address) {
        logger.info("Recherche des enfants pour l'adresse : {}", address);
        // Récupère les données JSON
        DataWrapper data = dataService.getData();
        logger.debug("Données récupérées depuis le DataService : {}", data);//raj


        // Filtre les personnes vivant à l'adresse donnée
        List<Person> personsAtAddress = data.getPersons().stream()
                .filter(person -> person.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());
        logger.info("Nombre de personnes trouvées à l'adresse {} : {}", address, personsAtAddress.size());//raj

        // Sépare les enfants et les adultes
        List<ChildDTO> children = personsAtAddress.stream()
                .filter(person -> isChild(data.getMedicalRecords(), person))
                .map(person -> {
                    MedicalRecord medicalRecord = findMedicalRecord(data.getMedicalRecords(), person);
                    int age = calculateAge(medicalRecord.getBirthdate());
                    logger.debug("Enfant trouvé : {} {}, âge : {}", person.getFirstName(), person.getLastName(), age);//raj
                    return new ChildDTO(person.getFirstName(), person.getLastName(), age);
                })
                .collect(Collectors.toList());

        List<String> otherHouseholdMembers = personsAtAddress.stream()
                .filter(person -> !isChild(data.getMedicalRecords(), person)) // Récupère les adultes
                .map(person -> person.getFirstName() + " " + person.getLastName())
                .collect(Collectors.toList());
        logger.info("Nombre d'enfants : {}, Nombre d'adultes : {}", children.size(), otherHouseholdMembers.size());//raj
        // Retourne le DTO avec les enfants et les autres membres
        return new ChildAlertResponseDTO(children, otherHouseholdMembers);
    }

    /**
     * Détermine si la personne spécifiée est un enfant (âge ≤ 18 ans).
     *
     * @param medicalRecords la liste de tous les dossiers médicaux
     * @param person la personne dont on veut déterminer l'âge
     * @return {@code true} si la personne est un enfant, {@code false} sinon
     */
    private boolean isChild(List<MedicalRecord> medicalRecords, Person person) {
        MedicalRecord medicalRecord = findMedicalRecord(medicalRecords, person);
        return medicalRecord != null && calculateAge(medicalRecord.getBirthdate()) <= 18;
    }
    /**
     * Trouve le dossier médical correspondant à la personne indiquée.
     *
     * @param medicalRecords la liste de tous les dossiers médicaux
     * @param person la personne dont on souhaite trouver le dossier médical
     * @return le dossier médical correspondant, ou {@code null} si aucun dossier n'est trouvé
     */
    private MedicalRecord findMedicalRecord(List<MedicalRecord> medicalRecords, Person person) {
        return medicalRecords.stream()
                .filter(medicalRecord ->
                        medicalRecord.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                                medicalRecord.getLastName().equalsIgnoreCase(person.getLastName()))
                .findFirst()
                .orElse(null);
    }
    /**
     * Calcule l'âge en années à partir d'une date de naissance au format MM/dd/yyyy.
     *
     * @param birthdate la date de naissance en format chaîne de caractères (MM/dd/yyyy)
     * @return l'âge en années
     */
    private int calculateAge(String birthdate) {
        LocalDate birthDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
