package com.safetynet.alerts.service;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.DataWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityEmailService {

    private final DataService dataService;

    public CommunityEmailService(DataService dataService) {
        this.dataService = dataService;
    }

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

