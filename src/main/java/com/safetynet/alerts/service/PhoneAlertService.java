package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PhoneAlertDTO;
import com.safetynet.alerts.model.DataWrapper;
import com.safetynet.alerts.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Service permettant de récupérer les numéros de téléphone des personnes
 * couvertes par une caserne de pompiers spécifique.
 */
@Service
public class PhoneAlertService {
    private static final Logger logger = LoggerFactory.getLogger(PhoneAlertService.class);

    private final DataService dataService;

    public PhoneAlertService(DataService dataService) {
        this.dataService = dataService;
    }
    /**
     * Récupère la liste des numéros de téléphone associés aux habitants
     * des adresses couvertes par une caserne de pompiers donnée.
     *
     * @param firestationNumber le numéro de la caserne de pompiers
     * @return un objet {@link PhoneAlertDTO} contenant la liste des numéros de téléphone sans doublons
     */
    public PhoneAlertDTO getPhoneNumbersByFirestation(int firestationNumber) {
        // Récupère les données JSON
        DataWrapper data = dataService.getData();
        logger.info("Recherche des numéros de téléphone pour la caserne : {}", firestationNumber);

        // Récupère les adresses couvertes par la caserne donnée
        List<String> addresses = data.getFirestations().stream()
                .filter(f -> f.getStation().equals(String.valueOf(firestationNumber))) // Filtre par numéro de caserne
                .map(f -> f.getAddress()) // Récupère les adresses
                .collect(Collectors.toList());

        // Récupère les numéros de téléphone des résidents aux adresses filtrées
        List<String> phoneNumbers = data.getPersons().stream()
                .filter(p -> addresses.contains(p.getAddress())) // Vérifie que l'adresse correspond
                .map(Person::getPhone) // Récupère les numéros de téléphone
                .distinct() // Supprime les doublons
                .collect(Collectors.toList());

        return new PhoneAlertDTO(phoneNumbers); // Retourne les numéros sous forme de DTO
    }
}
