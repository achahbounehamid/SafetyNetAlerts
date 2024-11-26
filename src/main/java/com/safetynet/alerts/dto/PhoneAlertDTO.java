package com.safetynet.alerts.dto;

import java.util.List;

public class PhoneAlertDTO {
    public List<String> phoneNumbers;// Liste des numéros de téléphone

    public PhoneAlertDTO(List<String> phoneNumbers){
        this.phoneNumbers = phoneNumbers;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
