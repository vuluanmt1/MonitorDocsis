package com.example.monitordocsis.congcu;

public class pppoeModel {
    private String adminstate;
    private String status;
    private String authtype;
    private String username;
    private String password;
    private String macaddress;
    private String host1;
    private String host2;
    private String configmask;
    private String configgetway;
    private String configprimarydns;
    private String configsecondarydns;

    public pppoeModel(String adminstate, String status, String authtype, String username, String password, String macaddress, String host1, String host2, String configmask, String configgetway, String configprimarydns, String configsecondarydns) {
        this.adminstate = adminstate;
        this.status = status;
        this.authtype = authtype;
        this.username = username;
        this.password = password;
        this.macaddress = macaddress;
        this.host1 = host1;
        this.host2 = host2;
        this.configmask = configmask;
        this.configgetway = configgetway;
        this.configprimarydns = configprimarydns;
        this.configsecondarydns = configsecondarydns;
    }

    public String getAdminstate() {
        return adminstate;
    }

    public void setAdminstate(String adminstate) {
        this.adminstate = adminstate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthtype() {
        return authtype;
    }

    public void setAuthtype(String authtype) {
        this.authtype = authtype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

    public String getHost1() {
        return host1;
    }

    public void setHost1(String host1) {
        this.host1 = host1;
    }

    public String getHost2() {
        return host2;
    }

    public void setHost2(String host2) {
        this.host2 = host2;
    }

    public String getConfigmask() {
        return configmask;
    }

    public void setConfigmask(String configmask) {
        this.configmask = configmask;
    }

    public String getConfiggetway() {
        return configgetway;
    }

    public void setConfiggetway(String configgetway) {
        this.configgetway = configgetway;
    }

    public String getConfigprimarydns() {
        return configprimarydns;
    }

    public void setConfigprimarydns(String configprimarydns) {
        this.configprimarydns = configprimarydns;
    }

    public String getConfigsecondarydns() {
        return configsecondarydns;
    }

    public void setConfigsecondarydns(String configsecondarydns) {
        this.configsecondarydns = configsecondarydns;
    }
}
