package com.example.monitordocsis.ui.ticket;

public class ticketOnuModel {
    private String maonu;
    private String status;
    private String rxpower;
    private String address;
    private String serviceStatus;
    private String createDate;
    private String inactTime;
    private String deactReason;

    public ticketOnuModel(String maonu, String status, String rxpower, String address, String serviceStatus, String createDate, String inactTime, String deactReason) {
        this.maonu = maonu;
        this.status = status;
        this.rxpower = rxpower;
        this.address = address;
        this.serviceStatus = serviceStatus;
        this.createDate = createDate;
        this.inactTime = inactTime;
        this.deactReason = deactReason;
    }

    public String getMaonu() {
        return maonu;
    }

    public void setMaonu(String maonu) {
        this.maonu = maonu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRxpower() {
        return rxpower;
    }

    public void setRxpower(String rxpower) {
        this.rxpower = rxpower;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getInactTime() {
        return inactTime;
    }

    public void setInactTime(String inactTime) {
        this.inactTime = inactTime;
    }

    public String getDeactReason() {
        return deactReason;
    }

    public void setDeactReason(String deactReason) {
        this.deactReason = deactReason;
    }
}
