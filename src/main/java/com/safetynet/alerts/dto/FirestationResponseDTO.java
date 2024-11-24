package com.safetynet.alerts.dto;

import java.util.List;

public class FirestationResponseDTO {

    private List<PersonInfoDTO> persons;
    private  int numberOfAdults;
    private  int numberOfChildren;

    public List<PersonInfoDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonInfoDTO> persons) {
        this.persons = persons;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }
}
