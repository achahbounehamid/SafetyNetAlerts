package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.Firestation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Service permettant de récupérer des informations sur les personnes vivant à une adresse spécifique,
 * ainsi que le numéro de la caserne de pompiers les desservant.
 * Les informations renvoyées incluent l'âge, le téléphone, les médicaments et les allergies des résidents.
 */
@Service
public class FireService {

    private final DataService dataService;
    private static final Logger logger = LoggerFactory.getLogger(FireService.class);

    public FireService(DataService dataService) {
        this.dataService = dataService;
    }
    /**
     * Récupère les informations sur les résidents d'une adresse spécifique, ainsi que la station de pompiers associée.
     *
     * @param address l'adresse pour laquelle on souhaite obtenir les informations
     * @return une liste de {@link FireDTO} contenant les données relatives aux résidents et à la caserne de pompiers
     */
    public List<FireDTO> getFireInfo(String address) {
        // Récupère les données JSON
        DataWrapper data = dataService.getData();

        logger.info("Recherche des habitants pour l'adresse : {}", address);

        // Trouve le numéro de la station associée à l'adresse
        String stationNumber = data.getFirestations().stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .map(Firestation::getStation)
                .findFirst()
                .orElse(null);

        if (stationNumber == null) {
            logger.warn("Aucune caserne trouvée pour l'adresse {}", address);
            return Collections.emptyList();
        }

        logger.info("Station associée : {}", stationNumber);

        // Récupère les résidents à l'adresse et mappe les données en FireDTO
        return data.getPersons().stream()
                .filter(person -> person.getAddress().equals(address))
                .map(person -> {
                    // Recherche du dossier médical pour chaque personne
                    MedicalRecord record = data.getMedicalRecords().stream()
                            .filter(medicalRecord -> medicalRecord.getFirstName().equals(person.getFirstName())
                                    && medicalRecord.getLastName().equals(person.getLastName()))
                            .findFirst()
                            .orElse(null);

                    // Crée un FireDTO avec les données collectées
                    return new FireDTO(
                            person.getFirstName(),
                            person.getLastName(),
                            calculateAge(record != null ? record.getBirthdate() : null),
                            person.getPhone(),
                            record != null ? record.getMedications() : Collections.emptyList(),
                            record != null ? record.getAllergies() : Collections.emptyList(),
                            stationNumber // Ajoute le numéro de station
                    );
                })
                .collect(Collectors.toList());
    }
    /**
     * Calcule l'âge d'une personne à partir de sa date de naissance, au format MM/dd/yyyy.
     *
     * @param birthdate la date de naissance sous forme de chaîne de caractères (MM/dd/yyyy), ou null
     * @return l'âge en années, ou 0 si aucune date de naissance n'est fournie
     */
    private int calculateAge(String birthdate) {
        if (birthdate == null) return 0;
        LocalDate birthDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
