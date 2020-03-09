package com.example.monitordocsis;

public class nodeModel {
    private String ifindex;
    private String ifalias;

    public nodeModel(String ifindex, String ifalias) {
        this.ifindex = ifindex;
        this.ifalias = ifalias;
    }

    public String getIfindex() {
        return ifindex;
    }

    public void setIfindex(String ifindex) {
        this.ifindex = ifindex;
    }

    public String getIfalias() {
        return ifalias;
    }

    public void setIfalias(String ifalias) {
        this.ifalias = ifalias;
    }
}
