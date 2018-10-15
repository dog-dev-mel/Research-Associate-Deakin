package com.example.ddetector.dronedetector;

/**
 * Created by ZHI on 13/10/2018.
 */
public class detectorlocationinfo {
    public String detectorUID;
    public String detectorBrand;
    public String detectorTimestamp;
    public String detectorBatteryStatus;
    public String detectorBatteryLevel;
    public String detectorLocationProvider;
    public String detectorLocationLongitude;
    public String detectorLocationLatitude;
    public String detectorLocationAtitude;
    public String detectorLocationAccuracy;
    public String detectorLocationSpeed;

    public detectorlocationinfo() {

    }

    public detectorlocationinfo(String detectorUID,
                                String detectorBrand,
                                String detectorTimestamp,
                                String detectorBatteryStatus,
                                String detectorBatteryLevel,
                                String detectorLocationProvider,
                                String detectorLocationLongitude,
                                String detectorLocationLatitude,
                                String detectorLocationAtitude,
                                String detectorLocationAccuracy,
                                String detectorLocationSpeed) {
        this.detectorUID = detectorUID;
        this.detectorBrand = detectorBrand;
        this.detectorTimestamp = detectorTimestamp;
        this.detectorBatteryStatus = detectorBatteryStatus;
        this.detectorBatteryLevel = detectorBatteryLevel;
        this.detectorLocationProvider = detectorLocationProvider;
        this.detectorLocationLongitude = detectorLocationLongitude;
        this.detectorLocationLatitude = detectorLocationLatitude;
        this.detectorLocationAtitude = detectorLocationAtitude;
        this.detectorLocationAccuracy = detectorLocationAccuracy;
        this.detectorLocationSpeed = detectorLocationSpeed;
    }

    public String getDetectorUID() {
        return detectorUID;
    }

    public void setDetectorUID(String detectorUID) {
        this.detectorUID = detectorUID;
    }

    public String getDetectorBrand() {
        return detectorBrand;
    }

    public void setDetectorBrand(String detectorBrand) {
        this.detectorBrand = detectorBrand;
    }

    public String getDetectorTimestamp() {
        return detectorTimestamp;
    }

    public void setDetectorTimestamp(String detectorTimestamp) {
        this.detectorTimestamp = detectorTimestamp;
    }

    public String getDetectorBatteryStatus() {
        return detectorBatteryStatus;
    }

    public void setDetectorBatteryStatus(String detectorBatteryStatus) {
        this.detectorBatteryStatus = detectorBatteryStatus;
    }

    public String getDetectorBatteryLevel() {
        return detectorBatteryLevel;
    }

    public void setDetectorBatteryLevel(String detectorBatteryLevel) {
        this.detectorBatteryLevel = detectorBatteryLevel;
    }

    public String getDetectorLocationProvider() {
        return detectorLocationProvider;
    }

    public void setDetectorLocationProvider(String detectorLocationProvider) {
        this.detectorLocationProvider = detectorLocationProvider;
    }

    public String getDetectorLocationLongitude() {
        return detectorLocationLongitude;
    }

    public void setDetectorLocationLongitude(String detectorLocationLongitude) {
        this.detectorLocationLongitude = detectorLocationLongitude;
    }

    public String getDetectorLocationLatitude() {
        return detectorLocationLatitude;
    }

    public void setDetectorLocationLatitude(String detectorLocationLatitude) {
        this.detectorLocationLatitude = detectorLocationLatitude;
    }

    public String getDetectorLocationAtitude() {
        return detectorLocationAtitude;
    }

    public void setDetectorLocationAtitude(String detectorLocationAtitude) {
        this.detectorLocationAtitude = detectorLocationAtitude;
    }

    public String getDetectorLocationAccuracy() {
        return detectorLocationAccuracy;
    }

    public void setDetectorLocationAccuracy(String detectorLocationAccuracy) {
        this.detectorLocationAccuracy = detectorLocationAccuracy;
    }

    public String getDetectorLocationSpeed() {
        return detectorLocationSpeed;
    }

    public void setDetectorLocationSpeed(String detectorLocationSpeed) {
        this.detectorLocationSpeed = detectorLocationSpeed;
    }
}
