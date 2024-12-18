package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecordCRUD;
import com.safetynet.alerts.service.MedicalRecordServiceCRUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Contrôleur REST offrant des opérations CRUD sur les dossiers médicaux
 * (ajout, mise à jour, suppression, et consultation).
 */

@RestController
@RequestMapping("/medicalRecordCRUD")
public class MedicalRecordControllerCRUD {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordControllerCRUD.class);
    @Autowired
    private MedicalRecordServiceCRUD medicalRecordService;

    @PostMapping
    /**
     * Ajoute un nouveau dossier médical.
     *
     * @param medicalRecord l'objet {@link MedicalRecordCRUD} représentant le dossier médical à ajouter
     * @return une réponse HTTP 200 contenant le dossier médical ajouté
     */

    public ResponseEntity<MedicalRecordCRUD> addMedicalRecord(@RequestBody MedicalRecordCRUD medicalRecord) {
        MedicalRecordCRUD savedRecord = medicalRecordService.addMedicalRecord(medicalRecord);
        return ResponseEntity.ok(savedRecord);
    }

    @PutMapping("/{firstName}/{lastName}")
    /**
     * Met à jour un dossier médical existant.
     *
     * @param firstName le prénom associé au dossier médical
     * @param lastName le nom de famille associé au dossier médical
     * @param updatedRecord l'objet {@link MedicalRecordCRUD} contenant les informations mises à jour
     * @return une réponse HTTP 200 si la mise à jour a réussi, 404 sinon
     */

    public ResponseEntity<Void> updateMedicalRecord(

            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody MedicalRecordCRUD updatedRecord) {
        logger.info("Requête PUT reçue pour firstName: {}, lastName: {}", firstName, lastName);
        logger.info("Payload: {}", updatedRecord);
        if (medicalRecordService.updateMedicalRecord(firstName, lastName, updatedRecord)) {
            logger.info("Mise à jour réussie pour le dossier médical de : {} {}", firstName, lastName);
            return ResponseEntity.ok().build();
        }
        logger.warn("Dossier médical non trouvé pour : {} {}", firstName, lastName);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{firstName}/{lastName}")
    /**
     * Supprime un dossier médical spécifique.
     *
     * @param firstName le prénom associé au dossier médical à supprimer
     * @param lastName le nom de famille associé au dossier médical à supprimer
     * @return une réponse HTTP 204 si la suppression a réussi, 404 sinon
     */
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {
        if (medicalRecordService.deleteMedicalRecord(firstName, lastName)) {
            logger.info("Dossier médical supprimé avec succès pour : {} {}", firstName, lastName);
            return ResponseEntity.noContent().build();
        }
        logger.warn("Impossible de trouver le dossier médical pour suppression : {} {}", firstName, lastName);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{firstName}/{lastName}")
    /**
     * Récupère un dossier médical spécifique en fonction du prénom et du nom de famille.
     *
     * @param firstName le prénom associé au dossier médical à récupérer
     * @param lastName le nom de famille associé au dossier médical à récupérer
     * @return une réponse HTTP 200 avec le dossier médical si trouvé, 404 sinon
     */
    public ResponseEntity<MedicalRecordCRUD> getMedicalRecordByFullName(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        logger.info("Requête GET reçue pour firstName: {}, lastName: {}", firstName, lastName);
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
