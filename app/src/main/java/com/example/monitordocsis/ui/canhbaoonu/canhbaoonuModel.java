package com.example.monitordocsis.ui.canhbaoonu;

import androidx.lifecycle.ViewModel;

public class canhbaoonuModel  extends ViewModel {
    private String maolt;
    private String maonu;
    private String port;
    private String onuid;
    private String status;
    private String mode;
    private String profile;
    private String firmware;
    private String rx;
    private String deactiveReason;
    private String inactTime;
    private String model;
    private String distance;
    private String datetime;

    public canhbaoonuModel(String maolt, String maonu, String port, String onuid, String status, String mode, String profile, String firmware, String rx, String deactiveReason, String inactTime, String model, String distance, String datetime) {
        this.maolt = maolt;
        this.maonu = maonu;
        this.port = port;
        this.onuid = onuid;
        this.status = status;
        this.mode = mode;
        this.profile = profile;
        this.firmware = firmware;
        this.rx = rx;
        this.deactiveReason = deactiveReason;
        this.inactTime = inactTime;
        this.model = model;
        this.distance = distance;
        this.datetime = datetime;
    }

    public String getMaolt() {
        return maolt;
    }

    public void setMaolt(String maolt) {
        this.maolt = maolt;
    }

    public String getMaonu() {
        return maonu;
    }

    public void setMaonu(String maonu) {
        this.maonu = maonu;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getOnuid() {
        return onuid;
    }

    public void setOnuid(String onuid) {
        this.onuid = onuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public String getRx() {
        return rx;
    }

    public void setRx(String rx) {
        this.rx = rx;
    }

    public String getDeactiveReason() {
        return deactiveReason;
    }

    public void setDeactiveReason(String deactiveReason) {
        this.deactiveReason = deactiveReason;
    }

    public String getInactTime() {
        return inactTime;
    }

    public void setInactTime(String inactTime) {
        this.inactTime = inactTime;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
