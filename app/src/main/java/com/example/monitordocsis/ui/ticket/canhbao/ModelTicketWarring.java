package com.example.monitordocsis.ui.ticket.canhbao;

public class ModelTicketWarring {
    private String leve;
    private String time;
    private String branch;
    private String maolt;
    private String portpon;
    private String onuActive;
    private String onuInactive;
    private String totalONU;
    private String slONU;
    private String percentOnuInAct;
    private String avgRxOnuAct;
    private String deact_time;
    private String nameNodeQuang;
    private String area;
    private String province;

    public ModelTicketWarring(String leve, String time, String branch, String maolt, String portpon, String onuActive, String onuInactive, String totalONU, String slONU, String percentOnuInAct, String avgRxOnuAct, String deact_time, String nameNodeQuang, String area, String province) {
        this.leve = leve;
        this.time = time;
        this.branch = branch;
        this.maolt = maolt;
        this.portpon = portpon;
        this.onuActive = onuActive;
        this.onuInactive = onuInactive;
        this.totalONU = totalONU;
        this.slONU = slONU;
        this.percentOnuInAct = percentOnuInAct;
        this.avgRxOnuAct = avgRxOnuAct;
        this.deact_time = deact_time;
        this.nameNodeQuang = nameNodeQuang;
        this.area = area;
        this.province = province;
    }

    public String getLeve() {
        return leve;
    }

    public void setLeve(String leve) {
        this.leve = leve;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getMaolt() {
        return maolt;
    }

    public void setMaolt(String maolt) {
        this.maolt = maolt;
    }

    public String getPortpon() {
        return portpon;
    }

    public void setPortpon(String portpon) {
        this.portpon = portpon;
    }

    public String getOnuActive() {
        return onuActive;
    }

    public void setOnuActive(String onuActive) {
        this.onuActive = onuActive;
    }

    public String getOnuInactive() {
        return onuInactive;
    }

    public void setOnuInactive(String onuInactive) {
        this.onuInactive = onuInactive;
    }

    public String getTotalONU() {
        return totalONU;
    }

    public void setTotalONU(String totalONU) {
        this.totalONU = totalONU;
    }

    public String getSlONU() {
        return slONU;
    }

    public void setSlONU(String slONU) {
        this.slONU = slONU;
    }

    public String getPercentOnuInAct() {
        return percentOnuInAct;
    }

    public void setPercentOnuInAct(String percentOnuInAct) {
        this.percentOnuInAct = percentOnuInAct;
    }

    public String getAvgRxOnuAct() {
        return avgRxOnuAct;
    }

    public void setAvgRxOnuAct(String avgRxOnuAct) {
        this.avgRxOnuAct = avgRxOnuAct;
    }

    public String getDeact_time() {
        return deact_time;
    }

    public void setDeact_time(String deact_time) {
        this.deact_time = deact_time;
    }

    public String getNameNodeQuang() {
        return nameNodeQuang;
    }

    public void setNameNodeQuang(String nameNodeQuang) {
        this.nameNodeQuang = nameNodeQuang;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
