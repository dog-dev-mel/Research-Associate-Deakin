package com.example.ddetector.dronedetector;

/**
 * Created by ZHI on 15/10/2018.
 */
public class droneinfo {
    public String droneTimestamp;
    public String droneUID;
    public String droneSSID;
    public String droneLevel;
    public String droneFrequency;
    public String droneCapabilities;

    public droneinfo() {

    }

    public droneinfo(String droneTimestamp,
            String droneUID,
            String droneSSID,
            String droneLevel,
            String droneFrequency,
            String droneCapabilities) {
        this.droneTimestamp = droneTimestamp;
        this.droneUID = droneUID;
        this.droneSSID = droneSSID;
        this.droneLevel = droneLevel;
        this.droneFrequency = droneFrequency;
        this.droneCapabilities = droneCapabilities;
    }

    public String getDroneTimestamp() {
        return droneTimestamp;
    }

    public void setDroneTimestamp(String droneTimestamp) {
        this.droneTimestamp = droneTimestamp;
    }

    public String getDroneUID() {
        return droneUID;
    }

    public void setDroneUID(String droneUID) {
        this.droneUID = droneUID;
    }

    public String getDroneSSID() {
        return droneSSID;
    }

    public void setDroneSSID(String droneSSID) {
        this.droneSSID = droneSSID;
    }

    public String getDroneLevel() {
        return droneLevel;
    }

    public void setDroneLevel(String droneLevel) {
        this.droneLevel = droneLevel;
    }

    public String getDroneFrequency() {
        return droneFrequency;
    }

    public void setDroneFrequency(String droneFrequency) {
        this.droneFrequency = droneFrequency;
    }

    public String getDroneCapabilities() {
        return droneCapabilities;
    }

    public void setDroneCapabilities(String droneCapabilities) {
        this.droneCapabilities = droneCapabilities;
    }
}
