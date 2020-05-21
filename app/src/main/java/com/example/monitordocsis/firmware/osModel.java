package com.example.monitordocsis.firmware;

public class osModel {
    private String portPon;
    private String OnuID;
    private String upgradeStatus;
    private String statusOS1;
    private String statusOS2;

    public osModel(String portPon, String onuID, String upgradeStatus, String statusOS1, String statusOS2) {
        this.portPon = portPon;
        OnuID = onuID;
        this.upgradeStatus = upgradeStatus;
        this.statusOS1 = statusOS1;
        this.statusOS2 = statusOS2;
    }

    public String getPortPon() {
        return portPon;
    }

    public void setPortPon(String portPon) {
        this.portPon = portPon;
    }

    public String getOnuID() {
        return OnuID;
    }

    public void setOnuID(String onuID) {
        OnuID = onuID;
    }

    public String getUpgradeStatus() {
        return upgradeStatus;
    }

    public void setUpgradeStatus(String upgradeStatus) {
        this.upgradeStatus = upgradeStatus;
    }

    public String getStatusOS1() {
        return statusOS1;
    }

    public void setStatusOS1(String statusOS1) {
        this.statusOS1 = statusOS1;
    }

    public String getStatusOS2() {
        return statusOS2;
    }

    public void setStatusOS2(String statusOS2) {
        this.statusOS2 = statusOS2;
    }
}
