package com.safetynet.alerts.dto;

import java.util.List;

public class FloodDTO {
    private String lastName;
    private String firstName;
    private int age;
    private  String phone;
    private List<String> medications;
    private List<String> allergies;
    private String address;

    public FloodDTO(String lastName, String firstName, List<String> allergies, List<String> medications, String phone, int age, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.allergies = allergies;
        this.medications = medications;
        this.phone = phone;
        this.age = age;
        this.address = address;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
