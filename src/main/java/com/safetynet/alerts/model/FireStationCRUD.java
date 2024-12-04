package com.safetynet.alerts.model;

public class FireStationCRUD {
    private String address;
    private int StationNumber;

    public FireStationCRUD(String address, int stationNumber) {
        this.address = address;
        this.StationNumber = stationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStationNumber() {
        return StationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.StationNumber = stationNumber;
    }
}
