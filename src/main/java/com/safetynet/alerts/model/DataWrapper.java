package com.safetynet.alerts.model;

import java.util.List;

public class DataWrapper {
    private List<Person> persons;
    private  List<Firestation> firestations;
    private List<MedicalRecord> medicalRecords;

    public DataWrapper(List<Person> persons, List<Firestation> firestations, List<MedicalRecord> medicalRecords) {
        this.persons = persons;
        this.firestations = firestations;
        this.medicalRecords = medicalRecords;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<Firestation> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }
}
