package com.example.monitordocsis.khuvuc;

public class areaModel {
    private String codeArea;
    private String nameArea;

    public areaModel(String codeArea, String nameArea) {
        this.codeArea = codeArea;
        this.nameArea = nameArea;
    }

    public String getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(String codeArea) {
        this.codeArea = codeArea;
    }

    public String getNameArea() {
        return nameArea;
    }

    public void setNameArea(String nameArea) {
        this.nameArea = nameArea;
    }
}
