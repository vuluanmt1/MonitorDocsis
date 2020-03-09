package com.example.monitordocsis.ui.quanlyolt;

public class quanlyOltModel  {
        private String portPon;
        private String onuActive;
        private String onuInActive;
        private String onuTotal;
        private String nameOLT;
        private String area;

    public quanlyOltModel(String portPon, String onuActive, String onuInActive, String onuTotal, String nameOLT, String area) {
        this.portPon = portPon;
        this.onuActive = onuActive;
        this.onuInActive = onuInActive;
        this.onuTotal = onuTotal;
        this.nameOLT =nameOLT;
        this.area = area;

    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNameOLT() {
        return nameOLT;
    }

    public void setNameOLT(String nameOLT) {
        this.nameOLT = nameOLT;
    }

    public String getPortPon() {
        return portPon;
    }

    public void setPortPon(String portPon) {
        this.portPon = portPon;
    }

    public String getOnuActive() {
        return onuActive;
    }

    public void setOnuActive(String onuActive) {
        this.onuActive = onuActive;
    }

    public String getOnuInActive() {
        return onuInActive;
    }

    public void setOnuInActive(String onuInActive) {
        this.onuInActive = onuInActive;
    }

    public String getOnuTotal() {
        return onuTotal;
    }

    public void setOnuTotal(String onuTotal) {
        this.onuTotal = onuTotal;
    }
}