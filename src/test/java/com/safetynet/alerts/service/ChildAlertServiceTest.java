package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@SpringBootTest
public class MedicalRecordServiceTest {

    @MockBean
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Test
    public void testAddMedicalRecord() {
        MedicalRecord newRecord = new MedicalRecord("John", "Doe", "01/01/2000", List.of("aspirin:100mg"), List.of("peanut"));
        when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(newRecord);

        MedicalRecord result = medicalRecordService.addMedicalRecord(newRecord);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }
}

