package com.example.monitordocsis;

import android.app.Application;

public  class permissionUser extends Application {
    private  String userGroup;
    private String unit;
    private String position;
    private String username;
    private String privilege;
    private String area;
    private String branch;
    private String email;
    private String systemGpon;
    private String systemDocsis;
    private  String version;
    private  String version_docsis_mb;
    private  String version_docsis_mn;

    public String getSystemGpon() {
        return systemGpon;
    }

    public void setSystemGpon(String systemGpon) {
        this.systemGpon = systemGpon;
    }

    public String getSystemDocsis() {
        return systemDocsis;
    }

    public void setSystemDocsis(String systemDocsis) {
        this.systemDocsis = systemDocsis;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrivilege(String privilege) {
        return this.privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
    public String getArea() {
        return this.area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    public String getBranch() {
        return this.branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion_docsis_mb() {
        return version_docsis_mb;
    }

    public void setVersion_docsis_mb(String version_docsis_mb) {
        this.version_docsis_mb = version_docsis_mb;
    }

    public String getVersion_docsis_mn() {
        return version_docsis_mn;
    }

    public void setVersion_docsis_mn(String version_docsis_mn) {
        this.version_docsis_mn = version_docsis_mn;
    }
}
