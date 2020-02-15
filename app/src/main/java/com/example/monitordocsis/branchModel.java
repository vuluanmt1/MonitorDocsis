package com.example.monitordocsis;

public class branchModel {
    private String code;
    private String description;
    private int merchantID;

    public branchModel(String code, String description, int merchantID) {
        this.code = code;
        this.description = description;
        this.merchantID = merchantID;
    }
    public branchModel(String code, String description) {
        this.code =code;
        this.description = description;

    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(int merchantID) {
        this.merchantID = merchantID;
    }
}
