package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecordCRUD;
import com.safetynet.alerts.service.MedicalRecordServiceCRUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalRecordCRUD")
public class MedicalRecordControllerCRUD {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordControllerCRUD.class);
    @Autowired
    private MedicalRecordServiceCRUD medicalRecordService;

    @PostMapping
    public ResponseEntity<MedicalRecordCRUD> addMedicalRecord(@RequestBody MedicalRecordCRUD medicalRecord) {
        MedicalRecordCRUD savedRecord = medicalRecordService.addMedicalRecord(medicalRecord);
        return ResponseEntity.ok(savedRecord);
    }

    @PutMapping("/{firstName}/{lastName}")
    public ResponseEntity<Void> updateMedicalRecord(

            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody MedicalRecordCRUD updatedRecord) {
        logger.info("Received PUT request for firstName: {}, lastName: {}", firstName, lastName);
        logger.info("Payload: {}", updatedRecord);
        if (medicalRecordService.updateMedicalRecord(firstName, lastName, updatedRecord)) {
            logger.info("Mise à jour réussie pour le dossier médical de : {} {}", firstName, lastName);
            return ResponseEntity.ok().build();
        }
        logger.warn("Dossier médical non trouvé pour : {} {}", firstName, lastName);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {
        if (medicalRecordService.deleteMedicalRecord(firstName, lastName)) {
            logger.info("Dossier médical supprimé avec succès pour : {} {}", firstName, lastName);
            return ResponseEntity.noContent().build();
        }
        logger.warn("Impossible de trouver le dossier médical pour suppression : {} {}", firstName, lastName);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecordCRUD> getMedicalRecordByFullName(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        logger.info("Received GET request for firstName: {}, lastName: {}", firstName, lastName);
        MedicalRecordCRUD medicalRecord = medicalRecordService.getMedicalRecord(firstName, lastName);
        if (medicalRecord != null) {
            logger.info("Dossier médical trouvé pour : {} {}", firstName, lastName);
            logger.debug("Dossier médical : {}", medicalRecord);
            return ResponseEntity.ok(medicalRecord);
        }
        logger.warn("Dossier médical non trouvé pour : {} {}", firstName, lastName);
        return ResponseEntity.notFound().build();
    }

}
