package com.safetynet.alerts.dto;

import java.util.List;

public class FloodResponseDTO {
    private String address;
    private List<FloodDTO> residents;

    public FloodResponseDTO(String address, List<FloodDTO> residents) {
        this.address = address;
        this.residents = residents;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FloodDTO> getResidents() {
        return residents;
    }

    public void setResidents(List<FloodDTO> residents) {
        this.residents = residents;
    }
}
