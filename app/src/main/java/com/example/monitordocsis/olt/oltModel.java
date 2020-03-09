package com.example.monitordocsis.olt;

public class oltModel {
    private String oltID;

    public oltModel(String oltID) {
        this.oltID = oltID;
    }

    public String getOltID() {
        return oltID;
    }

    public void setOltID(String oltID) {
        this.oltID = oltID;
    }
}
