package com.example.monitordocsis.systemdocsis_mb.CableModem;

public class ModelCMHisNode {
    private String mac;
    private String address;
    private String status;
    private String snr;
    private String mer;
    private String fec;
    private String unfec;
    private String txPower;
    private String rxPower;
    private String time;

    public ModelCMHisNode(String mac, String address, String status, String snr, String mer, String fec, String unfec, String txPower, String rxPower, String time) {
        this.mac = mac;
        this.address = address;
        this.status = status;
        this.snr = snr;
        this.mer = mer;
        this.fec = fec;
        this.unfec = unfec;
        this.txPower = txPower;
        this.rxPower = rxPower;
        this.time = time;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    public String getMer() {
        return mer;
    }

    public void setMer(String mer) {
        this.mer = mer;
    }

    public String getFec() {
        return fec;
    }

    public void setFec(String fec) {
        this.fec = fec;
    }

    public String getUnfec() {
        return unfec;
    }

    public void setUnfec(String unfec) {
        this.unfec = unfec;
    }

    public String getTxPower() {
        return txPower;
    }

    public void setTxPower(String txPower) {
        this.txPower = txPower;
    }

    public String getRxPower() {
        return rxPower;
    }

    public void setRxPower(String rxPower) {
        this.rxPower = rxPower;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
