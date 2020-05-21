package com.example.monitordocsis.systemdocsis_mn.cmts;

public class cmtsModel {
    private String cmtsID;
    private String cmtsTitle;

    public cmtsModel(String cmtsID, String cmtsTitle) {
        this.cmtsID = cmtsID;
        this.cmtsTitle = cmtsTitle;
    }

    public String getCmtsID() {
        return cmtsID;
    }

    public void setCmtsID(String cmtsID) {
        this.cmtsID = cmtsID;
    }

    public String getCmtsTitle() {
        return cmtsTitle;
    }

    public void setCmtsTitle(String cmtsTitle) {
        this.cmtsTitle = cmtsTitle;
    }
}
