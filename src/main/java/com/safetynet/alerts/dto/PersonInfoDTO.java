package com.safetynet.alerts.dto;

public class PersonInfoDTO {
    private  String firstName;
    private String lastName;
    private String address;
    private  String phone;

    // Constructeur pour initialiser les champs
    public PersonInfoDTO(String firstName,String lastName, String address,String phone){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;

    }
    // Getters et Setters pour accéder et modifier les données
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
