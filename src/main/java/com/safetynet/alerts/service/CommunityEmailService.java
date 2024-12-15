package com.safetynet.alerts.service;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.DataWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Service permettant de récupérer la liste des adresses e-mail
 * de tous les habitants d'une ville spécifiée.
 */
@Service
public class CommunityEmailService {

    private final DataService dataService;

    public CommunityEmailService(DataService dataService) {

        this.dataService = dataService;
    }
    /**
     * Récupère la liste des adresses e-mail des habitants de la ville indiquée.
     *
     * @param city la ville pour laquelle on souhaite obtenir la liste d'e-mails
     * @return une liste de chaînes de caractères représentant les adresses e-mail, sans doublons
     */
    public List<String> getEmailsByCity(String city) {
        // Récupérer les données JSON
        DataWrapper data = dataService.getData();

        // Filtrer les personnes par ville et extraire les e-mails
        return data.getPersons().stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city)) // Filtrer par ville
                .map(Person::getEmail) // Extraire les e-mails
                .distinct() // Supprimer les doublons
                .collect(Collectors.toList()); // Collecter les e-mails
    }
}

