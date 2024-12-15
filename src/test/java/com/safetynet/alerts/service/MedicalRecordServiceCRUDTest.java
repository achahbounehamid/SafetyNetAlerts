package com.safetynet.alerts.service;
import com.safetynet.alerts.SafetyNetAlertsApplication;
import com.safetynet.alerts.model.MedicalRecordCRUD;
import com.safetynet.alerts.repository.MedicalRecordRepositoryCRUD;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/**
 * Classe de test pour le service {@link MedicalRecordServiceCRUD}.
 * Vérifie le bon fonctionnement des opérations CRUD sur les dossiers médicaux.
 */
@SpringBootTest(classes = SafetyNetAlertsApplication.class)
public class MedicalRecordServiceCRUDTest {

    @MockBean
    private MedicalRecordRepositoryCRUD medicalRecordRepository;

    @Autowired
    private MedicalRecordServiceCRUD medicalRecordService;
    /**
     * Teste la méthode {@link MedicalRecordServiceCRUD#addMedicalRecord(MedicalRecordCRUD)}
     * pour s'assurer qu'elle ajoute correctement un dossier médical.
     */
    @Test
    public void testAddMedicalRecord() {
        MedicalRecordCRUD record = new MedicalRecordCRUD(
                "John",
                "Doe",
                "01/01/1980",
                Arrays.asList("med1", "med2"),
                Arrays.asList("allergy1")
        );

        when(medicalRecordRepository.save(any(MedicalRecordCRUD.class))).thenReturn(record);

        MedicalRecordCRUD result = medicalRecordService.addMedicalRecord(record);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(medicalRecordRepository, times(1)).save(record);
    }
    /**
     * Teste la méthode {@link MedicalRecordServiceCRUD#updateMedicalRecord(String, String, MedicalRecordCRUD)}
     * lorsqu'un dossier médical existe et peut être mis à jour.
     */
    @Test
    public void testUpdateMedicalRecord_found() {
        MedicalRecordCRUD existingRecord = new MedicalRecordCRUD(
                "John",
                "Doe",
                "01/01/1980",
                Arrays.asList("med1", "med2"),
                Arrays.asList("allergy1")
        );

        MedicalRecordCRUD updatedRecord = new MedicalRecordCRUD(
                "John",
                "Doe",
                "02/02/1985",
                Arrays.asList("med3"),
                Arrays.asList("allergy2")
        );
        // Simulation du comportement du repository
        when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(existingRecord));
        when(medicalRecordRepository.save(any(MedicalRecordCRUD.class))).thenReturn(existingRecord);
        // Appel de la méthode à tester
        boolean result = medicalRecordService.updateMedicalRecord("John", "Doe", updatedRecord);
        // Vérifications
        assertTrue(result);
        verify(medicalRecordRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
        verify(medicalRecordRepository, times(1)).save(existingRecord);
        assertEquals("02/02/1985", existingRecord.getBirthdate());
        assertEquals(Arrays.asList("med3"), existingRecord.getMedications());
        assertEquals(Arrays.asList("allergy2"), existingRecord.getAllergies());
    }
    /**
     * Teste la méthode {@link MedicalRecordServiceCRUD#updateMedicalRecord(String, String, MedicalRecordCRUD)}
     * lorsqu'un dossier médical n'existe pas.
     */
    @Test
    public void testUpdateMedicalRecord_notFound() {
        MedicalRecordCRUD updatedRecord = new MedicalRecordCRUD(
                "NonExistent",
                "User",
                "01/01/2000",
                Arrays.asList("medA"),
                Arrays.asList("allergyX")
        );
        // Simulation du comportement du repository
        when(medicalRecordRepository.findByFirstNameAndLastName("NonExistent", "User")).thenReturn(Optional.empty());
        // Appel de la méthode à tester
        boolean result = medicalRecordService.updateMedicalRecord("NonExistent", "User", updatedRecord);
          // Vérifications
        assertFalse(result);
        verify(medicalRecordRepository, times(1)).findByFirstNameAndLastName("NonExistent", "User");
        verify(medicalRecordRepository, never()).save(any(MedicalRecordCRUD.class));
    }
    /**
     * Teste la méthode {@link MedicalRecordServiceCRUD#deleteMedicalRecord(String, String)}
     * pour s'assurer qu'elle supprime correctement un dossier médical existant.
     */
    @Test
    public void testDeleteMedicalRecord() {
        // Simulation du comportement du repository
        when(medicalRecordRepository.delete("John", "Doe")).thenReturn(true);
       // Appel de la méthode à tester
        boolean result = medicalRecordService.deleteMedicalRecord("John", "Doe");
       // Vérifications
        assertTrue(result);
        verify(medicalRecordRepository, times(1)).delete("John", "Doe");
    }
    /**
     * Teste la méthode {@link MedicalRecordServiceCRUD#deleteMedicalRecord(String, String)}
     * lorsqu'un dossier médical n'existe pas.
     */
    @Test
    public void testDeleteMedicalRecord_notFound() {
        // Simulation du comportement du repository
        when(medicalRecordRepository.delete("Unknown", "Person")).thenReturn(false);
       // Appel de la méthode à tester
        boolean result = medicalRecordService.deleteMedicalRecord("Unknown", "Person");

        assertFalse(result);
        verify(medicalRecordRepository, times(1)).delete("Unknown", "Person");
    }
    /**
     * Teste la méthode {@link MedicalRecordServiceCRUD#getMedicalRecord(String, String)}
     * pour s'assurer qu'elle retourne correctement un dossier médical existant.
     */
    @Test
    public void testGetMedicalRecord_found() {
        // Préparation des données simulées
        MedicalRecordCRUD record = new MedicalRecordCRUD(
                "John",
                "Doe",
                "01/01/1980",
                Arrays.asList("med1"),
                Arrays.asList("allergy1")
        );
        // Simulation du comportement du repository
        when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(record));
       // Appel de la méthode à tester
        MedicalRecordCRUD result = medicalRecordService.getMedicalRecord("John", "Doe");
       // Vérifications
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }
    /**
     * Teste la méthode {@link MedicalRecordServiceCRUD#getMedicalRecord(String, String)}
     * lorsqu'un dossier médical n'existe pas.
     */
    @Test
    public void testGetMedicalRecord_notFound() {
        // Simulation du comportement du repository
        when(medicalRecordRepository.findByFirstNameAndLastName("Unknown", "Person")).thenReturn(Optional.empty());
       // Appel de la méthode à tester
        MedicalRecordCRUD result = medicalRecordService.getMedicalRecord("Unknown", "Person");
      // Vérifications
        assertNull(result);
    }
}
