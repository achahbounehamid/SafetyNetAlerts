import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.SafetyNetAlertsApplication;
import com.safetynet.alerts.model.MedicalRecordCRUD;
import com.safetynet.alerts.service.MedicalRecordServiceCRUD;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe de test pour le contrôleur MedicalRecordControllerCRUD
 * Vérifie le bon fonctionnement des opérations CRUD dans le contrôleur MedicalRecordController.
 */
@SpringBootTest(classes = SafetyNetAlertsApplication.class)
@AutoConfigureMockMvc
public class MedicalRecordControllerCRUDTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordServiceCRUD medicalRecordService;

    @Autowired
    private ObjectMapper objectMapper;
    /**
     * Teste l'ajout d'un dossier médical via l'endpoint POST /medicalRecordCRUD.
     * Vérifie que le service est appelé et que la réponse est conforme aux attentes.
     *
     * @throws Exception en cas d'erreur lors de la requête
     */
    @Test
    public void testAddMedicalRecord() throws Exception {
        // Pour correspondre aux assertions (lastName = "Doe"), on met "Doe".
        MedicalRecordCRUD record =  new MedicalRecordCRUD(
                "John",
                "Doe",
                "03/06/1984",
                Arrays.asList("aznol:350mg","hydrapermazol:100mg"),
                Arrays.asList("nillacilan")
        );

        when(medicalRecordService.addMedicalRecord(any(MedicalRecordCRUD.class))).thenReturn(record);

        mockMvc.perform(post("/medicalRecordCRUD")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(medicalRecordService, times(1)).addMedicalRecord(any(MedicalRecordCRUD.class));
    }
    /**
     * Teste la mise à jour d'un dossier médical existant via l'endpoint PUT /medicalRecordCRUD/{firstName}/{lastName}.
     * Vérifie que lorsque le dossier existe, il est mis à jour avec succès.
     *
     * @throws Exception en cas d'erreur lors de la requête
     */
    @Test
    public void testUpdateMedicalRecord_found() throws Exception {
        // On utilise "Doe" pour rester cohérent avec l'URL (John/Doe)
        MedicalRecordCRUD updatedRecord = new MedicalRecordCRUD(
                "John",
                "Doe",
                "03/06/1984",
                Arrays.asList("pharmacol:5000mg"),
                Arrays.asList("peanut")
        );

        when(medicalRecordService.updateMedicalRecord(eq("John"), eq("Doe"), any(MedicalRecordCRUD.class))).thenReturn(true);

        mockMvc.perform(put("/medicalRecordCRUD/John/Doe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecord)))
                .andExpect(status().isOk());

        verify(medicalRecordService, times(1)).updateMedicalRecord(eq("John"), eq("Doe"), any(MedicalRecordCRUD.class));
    }
    /**
     * Teste la mise à jour d'un dossier médical non existant via l'endpoint PUT /medicalRecordCRUD/{firstName}/{lastName}.
     * Vérifie que lorsque le dossier n'existe pas, un statut 404 Not Found est retourné.
     *
     * @throws Exception en cas d'erreur lors de la requête
     */
    @Test
    public void testUpdateMedicalRecord_notFound() throws Exception {
        MedicalRecordCRUD updatedRecord = new MedicalRecordCRUD(
                "NonExistent",
                "User",
                "01/01/2000",
                Arrays.asList("medA"),
                Arrays.asList("allergyX")
        );

        when(medicalRecordService.updateMedicalRecord(eq("NonExistent"), eq("User"), any(MedicalRecordCRUD.class))).thenReturn(false);

        mockMvc.perform(put("/medicalRecordCRUD/NonExistent/User")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecord)))
                .andExpect(status().isNotFound());

        verify(medicalRecordService, times(1)).updateMedicalRecord(eq("NonExistent"), eq("User"), any(MedicalRecordCRUD.class));
    }
    /**
     * Teste la suppression d'un dossier médical existant via l'endpoint DELETE /medicalRecordCRUD/{firstName}/{lastName}.
     * Vérifie que lorsque le dossier existe, il est supprimé avec succès.
     *
     * @throws Exception en cas d'erreur lors de la requête
     */
    @Test
    public void testDeleteMedicalRecord_found() throws Exception {
        when(medicalRecordService.deleteMedicalRecord("John", "Doe")).thenReturn(true);

        mockMvc.perform(delete("/medicalRecordCRUD/John/Doe"))
                .andExpect(status().isNoContent());

        verify(medicalRecordService, times(1)).deleteMedicalRecord("John", "Doe");
    }
    /**
     * Teste la suppression d'un dossier médical non existant via l'endpoint DELETE /medicalRecordCRUD/{firstName}/{lastName}.
     * Vérifie que lorsque le dossier n'existe pas, un statut 404 Not Found est retourné.
     *
     * @throws Exception en cas d'erreur lors de la requête
     */
    @Test
    public void testDeleteMedicalRecord_notFound() throws Exception {
        when(medicalRecordService.deleteMedicalRecord("Unknown", "Person")).thenReturn(false);

        mockMvc.perform(delete("/medicalRecordCRUD/Unknown/Person"))
                .andExpect(status().isNotFound());

        verify(medicalRecordService, times(1)).deleteMedicalRecord("Unknown", "Person");
    }
    /**
     * Teste la récupération d'un dossier médical existant via l'endpoint GET /medicalRecordCRUD/{firstName}/{lastName}.
     * Vérifie que lorsque le dossier existe, il est récupéré avec succès.
     *
     * @throws Exception en cas d'erreur lors de la requête
     */
    @Test
    public void testGetMedicalRecordByFullName_found() throws Exception {
        MedicalRecordCRUD record = new MedicalRecordCRUD(
                "John",
                "Doe",
                "01/01/1980",
                Arrays.asList("med1", "med2"),
                Arrays.asList("allergy1")
        );

        when(medicalRecordService.getMedicalRecord("John", "Doe")).thenReturn(record);

        mockMvc.perform(get("/medicalRecordCRUD/John/Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(medicalRecordService, times(1)).getMedicalRecord("John", "Doe");
    }
    /**
     * Teste la récupération d'un dossier médical non existant via l'endpoint GET /medicalRecordCRUD/{firstName}/{lastName}.
     * Vérifie que lorsque le dossier n'existe pas, un statut 404 Not Found est retourné.
     *
     * @throws Exception en cas d'erreur lors de la requête
     */
    @Test
    public void testGetMedicalRecordByFullName_notFound() throws Exception {
        when(medicalRecordService.getMedicalRecord("Unknown", "Person")).thenReturn(null);

        mockMvc.perform(get("/medicalRecordCRUD/Unknown/Person"))
                .andExpect(status().isNotFound());

        verify(medicalRecordService, times(1)).getMedicalRecord("Unknown", "Person");
    }
}
