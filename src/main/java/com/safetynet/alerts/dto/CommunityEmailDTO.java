package com.safetynet.alerts.dto;

import java.util.List;

public class CommunityEmailDTO {
    private List<String> emails;

    public CommunityEmailDTO(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}

