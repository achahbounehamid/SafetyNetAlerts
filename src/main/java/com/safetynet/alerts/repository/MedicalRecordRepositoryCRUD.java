package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecordCRUD;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Repository en mémoire pour gérer les opérations CRUD sur les dossiers médicaux {@link MedicalRecordCRUD}.
 * Permet l'ajout, la recherche, la mise à jour et la suppression de dossiers médicaux.
 */
@Repository
public class MedicalRecordRepositoryCRUD {
    private List<MedicalRecordCRUD> medicalRecords = new ArrayList<>();
    /**
     * Ajoute un nouveau dossier médical.
     *
     * @param medicalRecord le dossier médical à ajouter
     * @return le dossier médical ajouté
     */
    public MedicalRecordCRUD save(MedicalRecordCRUD medicalRecord) {
        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }
    /**
     * Recherche un dossier médical en fonction du prénom et du nom de famille.
     *
     * @param firstName le prénom associé au dossier médical
     * @param lastName le nom de famille associé au dossier médical
     * @return un {@link Optional} contenant le dossier médical s'il est trouvé, ou {@link Optional#empty()} sinon
     */
    public Optional<MedicalRecordCRUD> findByFirstNameAndLastName(String firstName, String lastName) {
        return medicalRecords.stream()
                .filter(record -> record.getFirstName().equalsIgnoreCase(firstName) &&
                        record.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
    }

    /**
     * Met à jour un dossier médical existant, identifié par le prénom et le nom.
     *
     * @param firstName le prénom associé au dossier médical à mettre à jour
     * @param lastName le nom de famille associé au dossier médical à mettre à jour
     * @param updatedRecord le dossier médical contenant les informations mises à jour
     * @return {@code true} si la mise à jour a réussi (dossier trouvé), {@code false} sinon
     */
    public boolean update(String firstName, String lastName, MedicalRecordCRUD updatedRecord) {
        Optional<MedicalRecordCRUD> recordOptional = findByFirstNameAndLastName(firstName, lastName);
        recordOptional.ifPresent(record -> {
            record.setBirthdate(updatedRecord.getBirthdate());
            record.setMedications(updatedRecord.getMedications());
            record.setAllergies(updatedRecord.getAllergies());
        });
        return recordOptional.isPresent();
    }
    /**
     * Supprime un dossier médical en fonction du prénom et du nom.
     *
     * @param firstName le prénom associé au dossier médical à supprimer
     * @param lastName le nom de famille associé au dossier médical à supprimer
     * @return {@code true} si le dossier a été supprimé, {@code false} si aucun dossier correspondant n'a été trouvé
     */
    public boolean delete(String firstName, String lastName) {
        return medicalRecords.removeIf(record ->
                record.getFirstName().equalsIgnoreCase(firstName)
                        && record.getLastName().equalsIgnoreCase(lastName));
    }
    /**
     * Récupère la liste de tous les dossiers médicaux.
     *
     * @return une liste contenant tous les {@link MedicalRecordCRUD}
     */
    public List<MedicalRecordCRUD> findAll() {
        return new ArrayList<>(medicalRecords);
    }
}
