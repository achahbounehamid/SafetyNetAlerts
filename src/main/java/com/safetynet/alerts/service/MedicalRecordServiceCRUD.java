package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecordCRUD;
import com.safetynet.alerts.repository.MedicalRecordRepositoryCRUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordServiceCRUD {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordServiceCRUD.class);
    @Autowired
    private MedicalRecordRepositoryCRUD medicalRecordRepository;

    public MedicalRecordCRUD addMedicalRecord(MedicalRecordCRUD medicalRecord) {
        logger.info("Ajout du dossier médical : {}", medicalRecord);
        return medicalRecordRepository.save(medicalRecord);
    }

    public boolean updateMedicalRecord(String firstName, String lastName, MedicalRecordCRUD updatedRecord) {
        logger.info("Tentative de mise à jour du dossier médical pour : {} {}", firstName, lastName);
        // Recherche le dossier médical existant
        Optional<MedicalRecordCRUD> existingRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);

        if (existingRecord.isPresent()) {
            MedicalRecordCRUD record = existingRecord.get();
            logger.debug("Dossier médical existant trouvé : {}", record);
            // Mise à jour des champs
            record.setBirthdate(updatedRecord.getBirthdate());
            record.setMedications(updatedRecord.getMedications());
            record.setAllergies(updatedRecord.getAllergies());

            // Appel de save() pour persister les changements
            medicalRecordRepository.save(record);

            logger.info("Dossier médical mis à jour avec succès pour : {} {}", firstName, lastName);
            return true; // Indique que la mise à jour a réussi
        }
        logger.warn("Aucun dossier médical trouvé pour : {} {}", firstName, lastName);
        return false; // Retourne false si le dossier n'existe pas
    }


    public boolean deleteMedicalRecord(String firstName, String lastName) {
        logger.info("Suppression du dossier médical pour : {} {}", firstName, lastName);
        return medicalRecordRepository.delete(firstName, lastName);
    }

    public MedicalRecordCRUD getMedicalRecord(String firstName, String lastName) {
        logger.info("Recherche du dossier médical pour : {} {}", firstName, lastName);
        return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName).orElse(null);
    }

}
