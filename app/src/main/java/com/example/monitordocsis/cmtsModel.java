package com.example.monitordocsis;

public class cmtsModel {
    private String cmtsID;
    private String cmtsName;

    public cmtsModel(String cmtsID, String cmtsName) {
        this.cmtsID = cmtsID;
        this.cmtsName = cmtsName;
    }

    public String getCmtsID() {
        return cmtsID;
    }

    public void setCmtsID(String cmtsID) {
        this.cmtsID = cmtsID;
    }

    public String getCmtsName() {
        return cmtsName;
    }

    public void setCmtsName(String cmtsName) {
        this.cmtsName = cmtsName;
    }
}
