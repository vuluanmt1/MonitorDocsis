package com.example.monitordocsis.systemdocsis_mb.CableModem;

public class ModelCMHis {
    private String snr;
    private String mer;
    private String fec;
    private String unfec;
    private String txpower;
    private String rxpower;
    private String status;
    private String time;

    public ModelCMHis(String snr, String mer, String fec, String unfec, String txpower, String rxpower, String status, String time) {
        this.snr = snr;
        this.mer = mer;
        this.fec = fec;
        this.unfec = unfec;
        this.txpower = txpower;
        this.rxpower = rxpower;
        this.status = status;
        this.time = time;
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

    public String getTxpower() {
        return txpower;
    }

    public void setTxpower(String txpower) {
        this.txpower = txpower;
    }

    public String getRxpower() {
        return rxpower;
    }

    public void setRxpower(String rxpower) {
        this.rxpower = rxpower;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
