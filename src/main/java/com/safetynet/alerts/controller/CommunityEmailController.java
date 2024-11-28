package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.CommunityEmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
public class CommunityEmailController {

    private final CommunityEmailService communityEmailService;

    public CommunityEmailController(CommunityEmailService communityEmailService) {
        this.communityEmailService = communityEmailService;
    }

    @GetMapping("/communityEmail")
    public List<String> getEmailsByCity(@RequestParam String city) {
        // Log pour suivre la requête
//        logger.info("Requête reçue pour les e-mails des habitants de la ville : {}", city);



        // Appel du service
        return communityEmailService.getEmailsByCity(city);
    }
}

