package com.safetynet.alerts;
import com.safetynet.alerts.model.MedicalRecordCRUD;
import com.safetynet.alerts.repository.MedicalRecordRepositoryCRUD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe de test pour le repository {@link MedicalRecordRepositoryCRUD}.
 * Vérifie le bon fonctionnement des opérations CRUD sur les dossiers médicaux.
 */
public class MedicalRecordRepositoryCRUDTest {

    private MedicalRecordRepositoryCRUD medicalRecordRepository;
    /**
     * Initialise le repository avant chaque test en y ajoutant un enregistrement de base.
     */
    @BeforeEach
    public void setup() {
        medicalRecordRepository = new MedicalRecordRepositoryCRUD();
        // Insertion d'un enregistrement de base pour les tests
        MedicalRecordCRUD record = new MedicalRecordCRUD(
                "John",
                "Doe",
                "01/01/1980",
                Arrays.asList("med1", "med2"),
                Arrays.asList("allergy1")
        );
        medicalRecordRepository.save(record);
    }
    /**
     * Teste la méthode {@link MedicalRecordRepositoryCRUD#save(MedicalRecordCRUD)}
     * pour s'assurer qu'elle enregistre correctement un nouveau dossier médical.
     */
    @Test
    public void testSave() {
        // Préparation des données simulées
        MedicalRecordCRUD newRecord = new MedicalRecordCRUD(
                "Jane",
                "Smith",
                "02/02/1990",
                Arrays.asList("medA"),
                Arrays.asList("allergyX")
        );
        // Simulation de l'enregistrement
        MedicalRecordCRUD saved = medicalRecordRepository.save(newRecord);
        // Vérifications
        assertNotNull(saved);
        assertEquals("Jane", saved.getFirstName());
        assertEquals("Smith", saved.getLastName());

        // Vérifie que l'objet est bien dans la liste
        Optional<MedicalRecordCRUD> found = medicalRecordRepository.findByFirstNameAndLastName("Jane", "Smith");
        assertTrue(found.isPresent());
    }
    /**
     * Teste la méthode {@link MedicalRecordRepositoryCRUD#findByFirstNameAndLastName(String, String)}
     * pour s'assurer qu'elle retourne correctement un dossier médical existant.
     */
    @Test
    public void testFindByFirstNameAndLastName_found() {
        // On recherche l'enregistrement inséré dans le @BeforeEach
        Optional<MedicalRecordCRUD> found = medicalRecordRepository.findByFirstNameAndLastName("John", "Doe");
        assertTrue(found.isPresent());
        assertEquals("John", found.get().getFirstName());
        assertEquals("Doe", found.get().getLastName());
    }
    /**
     * Teste la méthode {@link MedicalRecordRepositoryCRUD#findByFirstNameAndLastName(String, String)}
     * pour s'assurer qu'elle retourne vide lorsqu'aucun dossier médical ne correspond.
     */
    @Test
    public void testFindByFirstNameAndLastName_notFound() {
        // Recherche d'un enregistrement inexistant
        Optional<MedicalRecordCRUD> found = medicalRecordRepository.findByFirstNameAndLastName("NonExistent", "User");
        assertFalse(found.isPresent());
    }
    /**
     * Teste la méthode {@link MedicalRecordRepositoryCRUD#update(String, String, MedicalRecordCRUD)}
     * pour s'assurer qu'elle met à jour correctement un dossier médical existant.
     */
    @Test
    public void testUpdate_found() {
        // Préparation des données simulées pour la mise à jour
        MedicalRecordCRUD updatedRecord = new MedicalRecordCRUD(
                "John",
                "Doe",
                "03/03/1990", // nouvelle date
                Arrays.asList("med3"), // nouvelle liste de meds
                Arrays.asList("allergy2") // nouvelle liste d'allergies
        );
        // Mise à jour de l'enregistrement
        boolean updated = medicalRecordRepository.update("John", "Doe", updatedRecord);
        assertTrue(updated, "La mise à jour du dossier médical devrait réussir.");

        // Vérifie que les modifications ont bien été effectuées
        Optional<MedicalRecordCRUD> found = medicalRecordRepository.findByFirstNameAndLastName("John", "Doe");
        assertTrue(found.isPresent());
        assertEquals("03/03/1990", found.get().getBirthdate());
        assertEquals(Arrays.asList("med3"), found.get().getMedications());
        assertEquals(Arrays.asList("allergy2"), found.get().getAllergies());
    }
    /**
     * Teste la méthode {@link MedicalRecordRepositoryCRUD#update(String, String, MedicalRecordCRUD)}
     * pour s'assurer qu'elle retourne false lorsqu'on tente de mettre à jour un dossier médical inexistant.
     */
    @Test
    public void testUpdate_notFound() {
        // Préparation des données simulées pour la mise à jour
        MedicalRecordCRUD updatedRecord = new MedicalRecordCRUD(
                "NonExistent",
                "User",
                "01/01/2000",
                Arrays.asList("medA"),
                Arrays.asList("allergyX")
        );
        // Tentative de mise à jour d'un enregistrement inexistant
        boolean updated = medicalRecordRepository.update("NonExistent", "User", updatedRecord);
        assertFalse(updated);
    }
    /**
     * Teste la méthode {@link MedicalRecordRepositoryCRUD#delete(String, String)}
     * pour s'assurer qu'elle supprime correctement un dossier médical existant.
     */
    @Test
    public void testDelete_found() {
        // Suppression de l'enregistrement existant
        boolean deleted = medicalRecordRepository.delete("John", "Doe");
        assertTrue(deleted);

        // Vérifie que l'enregistrement n'existe plus
        Optional<MedicalRecordCRUD> found = medicalRecordRepository.findByFirstNameAndLastName("John", "Doe");
        assertFalse(found.isPresent());
    }
    /**
     * Teste la méthode {@link MedicalRecordRepositoryCRUD#delete(String, String)}
     * pour s'assurer qu'elle retourne false lorsqu'on tente de supprimer un dossier médical inexistant.
     */
    @Test
    public void testDelete_notFound() {
        // Tentative de suppression d'un enregistrement inexistant
        boolean deleted = medicalRecordRepository.delete("Unknown", "Person");
        assertFalse(deleted, "La suppression devrait échouer car le dossier médical n'existe pas.");
    }
    /**
     * Teste la méthode {@link MedicalRecordRepositoryCRUD#findAll()}
     * pour s'assurer qu'elle retourne correctement tous les dossiers médicaux.
     */
    @Test
    public void testFindAll() {
        // Récupère tous les enregistrements après l'insertion initiale
        List<MedicalRecordCRUD> allRecords = medicalRecordRepository.findAll();
        // Dans le @BeforeEach, un enregistrement a été inséré.
        assertEquals(1, allRecords.size(),"Il devrait y avoir 1 dossier médical.");

        // Ajout d'un second enregistrement pour tester
        medicalRecordRepository.save(new MedicalRecordCRUD(
                "Jane",
                "Smith",
                "02/02/1990",
                Arrays.asList("medA"),
                Arrays.asList("allergyX")
        ));

        // Récupère tous les enregistrements après l'ajout
        allRecords = medicalRecordRepository.findAll();
        assertEquals(2, allRecords.size(), "Il devrait y avoir 2 dossiers médicaux après l'ajout.");
    }
}

