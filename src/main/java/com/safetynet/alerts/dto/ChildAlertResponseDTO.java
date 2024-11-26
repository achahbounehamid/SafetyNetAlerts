package com.safetynet.alerts.dto;

import java.util.List;

public class ChildAlertResponseDTO {
    private List<ChildDTO> children; // Liste des enfants
    private List<String> otherHouseholdMembers; // Liste des membres adultes du foyer

    public ChildAlertResponseDTO(List<ChildDTO> children, List<String> otherHouseholdMembers) {
        this.children = children;
        this.otherHouseholdMembers = otherHouseholdMembers;
    }

    public List<ChildDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ChildDTO> children) {
        this.children = children;
    }

    public List<String> getOtherHouseholdMembers() {
        return otherHouseholdMembers;
    }

    public void setOtherHouseholdMembers(List<String> otherHouseholdMembers) {
        this.otherHouseholdMembers = otherHouseholdMembers;
    }
}
