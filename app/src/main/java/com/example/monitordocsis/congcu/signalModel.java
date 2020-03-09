package com.example.monitordocsis.congcu;

public class signalModel {
    private String portpon;
    private String onuid;
    private String status;
    private String mode;
    private String snonu;
    private String uptime;
    private String rxpower;
    private String optical;
    private String totalrf;
    private String ping;

    public signalModel(String portpon, String onuid, String status, String mode, String snonu, String uptime, String rxpower,String optical,String totalrf, String ping) {
        this.portpon = portpon;
        this.onuid = onuid;
        this.status = status;
        this.mode = mode;
        this.snonu = snonu;
        this.uptime = uptime;
        this.rxpower = rxpower;
        this.optical =optical;
        this.totalrf =totalrf;
        this.ping =ping;
    }

    public String getPortpon() {
        return portpon;
    }

    public void setPortpon(String portpon) {
        this.portpon = portpon;
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

    public String getSnonu() {
        return snonu;
    }

    public void setSnonu(String snonu) {
        this.snonu = snonu;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getRxpower() {
        return rxpower;
    }

    public void setRxpower(String rxpower) {
        this.rxpower = rxpower;
    }

    public String getPing() {
        return ping;
    }

    public void setPing(String ping) {
        this.ping = ping;
    }

    public String getOptical() {
        return optical;
    }

    public void setOptical(String optical) {
        this.optical = optical;
    }

    public String getTotalrf() {
        return totalrf;
    }

    public void setTotalrf(String totalrf) {
        this.totalrf = totalrf;
    }
}
