package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FirestationResponseDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Service permettant de récupérer les informations sur les personnes couvertes par une caserne de pompiers donnée.
 * Il calcule également le nombre d'enfants et d'adultes parmi ces personnes.
 */
@Service // Déclare cette classe comme un service Spring
public class FirestationService {

    private final DataService dataService; // Service pour accéder aux données JSON

    public FirestationService(DataService dataService) {
        this.dataService = dataService; // Injection du service DataService
    }
    /**
     * Récupère la liste des personnes couvertes par une caserne de pompiers spécifique,
     * ainsi que le nombre d'enfants et d'adultes parmi elles.
     *
     * @param stationNumber le numéro de la caserne de pompiers
     * @return un objet {@link FirestationResponseDTO} contenant la liste des personnes
     *         (avec prénom, nom, adresse, téléphone), ainsi que le nombre d'enfants et d'adultes
     */
    public FirestationResponseDTO getPersonsByStation(int stationNumber) {
        DataWrapper data = dataService.getData(); // Récupère les données depuis le DataService

        // Récupère les adresses couvertes par la caserne donnée
        List<String> addresses = data.getFirestations().stream()
                .filter(f -> f.getStation().equals(String.valueOf(stationNumber))) // Filtre les casernes par numéro
                .map(Firestation::getAddress) // Récupère les adresses associées
                .collect(Collectors.toList()); // Transforme en liste

        // Récupère les personnes vivant à ces adresses
        List<Person> persons = data.getPersons().stream()
                .filter(p -> addresses.contains(p.getAddress())) // Vérifie si l'adresse de la personne correspond
                .collect(Collectors.toList()); // Transforme en liste

        // Compte le nombre d'enfants
        int numberOfChildren = (int) persons.stream()
                .filter(p -> isChild(data.getMedicalRecords(), p)) // Vérifie si la personne est un enfant
                .count();

        // Compte le nombre d'adultes
        int numberOfAdults = persons.size() - numberOfChildren;

        // Prépare la réponse
        FirestationResponseDTO response = new FirestationResponseDTO();
        response.setPersons(persons.stream()
                .map(p -> new PersonInfoDTO(p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone())) // Transforme chaque personne en DTO
                .collect(Collectors.toList()));
        response.setNumberOfChildren(numberOfChildren); // Définit le nombre d'enfants
        response.setNumberOfAdults(numberOfAdults); // Définit le nombre d'adultes

        return response; // Retourne la réponse
    }
    /**
     * Détermine si une personne est considérée comme un enfant (âgée de 18 ans ou moins)
     * en fonction de son dossier médical.
     *
     * @param medicalRecords la liste de tous les dossiers médicaux
     * @param person la personne dont on veut déterminer l'âge
     * @return {@code true} si la personne est un enfant (≤ 18 ans), {@code false} sinon
     */
    private boolean isChild(List<MedicalRecord> medicalRecords, Person person) {
        return medicalRecords.stream()
                .filter(m -> m.getFirstName().equals(person.getFirstName()) &&
                        m.getLastName().equals(person.getLastName())) // Associe le dossier médical à la personne
                .anyMatch(m -> {
                    LocalDate birthdate = LocalDate.parse(m.getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy")); // Parse la date de naissance
                    return Period.between(birthdate, LocalDate.now()).getYears() <= 18; // Vérifie si l'âge est ≤ 18
                });
    }
}
