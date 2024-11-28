package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PersonInfoLastNameDTO;
import com.safetynet.alerts.service.PersonInfoLastNameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonInfoLastNameController {

    private final PersonInfoLastNameService personInfoLastNameService;

    public PersonInfoLastNameController(PersonInfoLastNameService personInfoLastNameService) {
        this.personInfoLastNameService = personInfoLastNameService;
    }

    @GetMapping("/personInfoLastName={lastName}")
    public List<PersonInfoLastNameDTO> getPersonByLastName(@RequestParam String lastName) {
        return personInfoLastNameService.getPersonByLastName(lastName);
    }
}

