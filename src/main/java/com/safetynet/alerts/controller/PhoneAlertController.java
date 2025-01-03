package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PhoneAlertDTO;
import com.safetynet.alerts.service.PhoneAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * Contrôleur REST permettant de récupérer les numéros de téléphone associés à une caserne de pompiers spécifique.
 */
@RestController
public class PhoneAlertController {
   private final PhoneAlertService phoneAlertService;
    private static final Logger logger = LoggerFactory.getLogger(PhoneAlertController.class);
   public PhoneAlertController(PhoneAlertService phoneAlertService){
       this.phoneAlertService = phoneAlertService;
   }

    @GetMapping("/phoneAlert")
    /**
     * Récupère la liste des numéros de téléphone associés à une caserne de pompiers donnée.
     *
     * @param firestation le numéro de la caserne de pompiers
     * @return un objet {@link PhoneAlertDTO} contenant la liste des numéros de téléphone
     */
    public PhoneAlertDTO getPhoneNumbers(@RequestParam int firestation){
       logger.info("Requête reçue pour les numéros de téléphone de la caserne : {}", firestation);
       return  phoneAlertService.getPhoneNumbersByFirestation(firestation);// Appelle la méthode appropriée
    }
}

