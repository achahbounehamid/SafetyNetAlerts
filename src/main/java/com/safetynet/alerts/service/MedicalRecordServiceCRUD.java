package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecordCRUD;
import com.safetynet.alerts.repository.MedicalRecordRepositoryCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceCRUD {
    @Autowired
    private MedicalRecordRepositoryCRUD medicalRecordRepository;

    public MedicalRecordCRUD addMedicalRecord(MedicalRecordCRUD medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public boolean updateMedicalRecord(String firstName, String lastName, MedicalRecordCRUD updatedRecord) {
        return medicalRecordRepository.update(firstName, lastName, updatedRecord);
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        return medicalRecordRepository.delete(firstName, lastName);
    }

    public MedicalRecordCRUD getMedicalRecord(String firstName, String lastName) {
        return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName).orElse(null);
    }

}
