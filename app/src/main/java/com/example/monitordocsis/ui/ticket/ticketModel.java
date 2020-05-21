package com.example.monitordocsis.ui.ticket;

public class ticketModel {
    private String stt;
    private String leve;
    private String time;
    private String branch;
    private String deact_time;
    private String maolt;
    private String portpon;
    private String onuActive;
    private String onuInactive;
    private String totalONU;
    private String percentOnuInAct;
    private String avgRxOnuAct;
    private String nameNodeQuang;
    private String maTicket;
    private String area;
    private String province;

    public ticketModel( String leve, String time, String branch, String deact_time, String maolt, String portpon, String onuActive, String onuInactive, String totalONU, String percentOnuInAct, String avgRxOnuAct, String nameNodeQuang, String maTicket, String area, String  province) {
        this.leve = leve;
        this.time = time;
        this.branch = branch;
        this.deact_time = deact_time;
        this.maolt = maolt;
        this.portpon = portpon;
        this.onuActive = onuActive;
        this.onuInactive = onuInactive;
        this.totalONU = totalONU;
        this.percentOnuInAct = percentOnuInAct;
        this.avgRxOnuAct = avgRxOnuAct;
        this.nameNodeQuang = nameNodeQuang;
        this.maTicket = maTicket;
        this.area=area;
        this.province=province;
    }

    public String getMaTicket() {
        return maTicket;
    }

    public void setMaTicket(String maTicket) {
        this.maTicket = maTicket;
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

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
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

    public String getDeact_time() {
        return deact_time;
    }

    public void setDeact_time(String deact_time) {
        this.deact_time = deact_time;
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

    public String getNameNodeQuang() {
        return nameNodeQuang;
    }

    public void setNameNodeQuang(String nameNodeQuang) {
        this.nameNodeQuang = nameNodeQuang;
    }
}
