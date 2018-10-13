package com.example.ddetector.dronedetector;

/**
 * Created by ZHI on 13/10/2018.
 */
public class detector {
    public  String detectorUID;
    public  String detectorBatteryStatus;
    public  String detectorBatteryLevel;
    public  String detectorLocationProvider;
    public  String detectorLocationLongitude;
    public  String detectorLocationLatitude;
    public  String detectorLocationAtitude;
    public  String detectorLocationAccuracy;
    public  String detectorLocationSpeed;

    public detector () {

    }

    public detector(String detectorUID, String detectorBatteryStatus, String detectorBatteryLevel
                     ,String detectorLocationProvider, String detectorLocationLongitude, String detectorLocationLatitude
                     ,String detectorLocationAtitude, String detectorLocationAccuracy, String detectorLocationSpeed) {
        this.detectorUID = detectorUID;
        this.detectorBatteryStatus = detectorBatteryStatus;
        this.detectorBatteryLevel = detectorBatteryLevel;
        this.detectorLocationProvider = detectorLocationProvider;
        this.detectorLocationLongitude = detectorLocationLongitude;
        this.detectorLocationLatitude = detectorLocationLatitude;
        this.detectorLocationAtitude = detectorLocationAtitude;
        this.detectorLocationAccuracy = detectorLocationAccuracy;
        this.detectorLocationSpeed = detectorLocationSpeed;
    }

    public String getdetectorUID() {
        return detectorUID;
    }

    public String getdetectorBatteryStatus() {
        return detectorBatteryStatus;
    }

    public String getdetectorBatteryLevel() {
        return detectorBatteryLevel;
    }

    public String getdetectorLocationProvider() {
        return detectorLocationProvider;
    }

    public String getdetectorLocationLongitude() {
        return detectorLocationLongitude;
    }

    public String getdetectorLocationLatitude() {
        return detectorLocationLatitude;
    }

    public String getdetectorLocationAtitude() {
        return detectorLocationAtitude;
    }

    public String getdetectorLocationAccuracy() {
        return detectorLocationAccuracy;
    }

    public String getdetectorLocationSpeed() {
        return detectorLocationSpeed;
    }
}
