package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PersonInfoLastNameDTO;
import com.safetynet.alerts.service.PersonInfoLastNameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * Contrôleur REST permettant de récupérer les informations de personnes
 * à partir de leur nom de famille.
 */
@RestController
public class PersonInfoLastNameController {

    private final PersonInfoLastNameService personInfoLastNameService;

    public PersonInfoLastNameController(PersonInfoLastNameService personInfoLastNameService) {
        this.personInfoLastNameService = personInfoLastNameService;
    }

    @GetMapping("/personInfo")
    /**
     * Récupère la liste des informations de personnes correspondant au nom de famille spécifié.
     *
     * @param lastName le nom de famille pour lequel on souhaite obtenir les informations
     * @return une liste de {@link PersonInfoLastNameDTO} contenant les informations des personnes trouvées
     */
    public List<PersonInfoLastNameDTO> getPersonByLastName(@RequestParam String lastName) {
        return personInfoLastNameService.getPersonByLastName(lastName);
    }
}

