package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecordCRUD;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MedicalRecordRepositoryCRUD {
    private List<MedicalRecordCRUD> medicalRecords = new ArrayList<>();

    public MedicalRecordCRUD save(MedicalRecordCRUD medicalRecord) {
        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }

    public Optional<MedicalRecordCRUD> findByFirstNameAndLastName(String firstName, String lastName) {
        return medicalRecords.stream()
                .filter(record -> record.getFirstName().equalsIgnoreCase(firstName) &&
                        record.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
    }


    public boolean update(String firstName, String lastName, MedicalRecordCRUD updatedRecord) {
        Optional<MedicalRecordCRUD> recordOptional = findByFirstNameAndLastName(firstName, lastName);
        recordOptional.ifPresent(record -> {
            record.setBirthdate(updatedRecord.getBirthdate());
            record.setMedications(updatedRecord.getMedications());
            record.setAllergies(updatedRecord.getAllergies());
        });
        return recordOptional.isPresent();
    }

    public boolean delete(String firstName, String lastName) {
        return medicalRecords.removeIf(record ->
                record.getFirstName().equalsIgnoreCase(firstName)
                        && record.getLastName().equalsIgnoreCase(lastName));
    }

    public List<MedicalRecordCRUD> findAll() {
        return new ArrayList<>(medicalRecords);
    }
}
