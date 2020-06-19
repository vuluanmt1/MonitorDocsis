package com.example.monitordocsis.ui.ticket.danhan;

public class ModelTicketReceive {
    private String leve;
    private String timeErrors;
    private String timeRecieve;
    private String unitProcess;
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
    private String timeRepeat;
    private String codeTicket;
    private String area;
    private String province;

    public ModelTicketReceive(String leve, String timeErrors, String timeRecieve, String unitProcess, String maolt, String portpon, String onuActive, String onuInactive, String totalONU, String slONU, String percentOnuInAct, String avgRxOnuAct, String deact_time, String nameNodeQuang, String timeRepeat,  String codeTicket, String area, String province) {
        this.leve = leve;
        this.timeErrors = timeErrors;
        this.timeRecieve = timeRecieve;
        this.unitProcess = unitProcess;
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
        this.timeRepeat = timeRepeat;
        this.codeTicket = codeTicket;
        this.area = area;
        this.province = province;
    }

    public String getLeve() {
        return leve;
    }

    public void setLeve(String leve) {
        this.leve = leve;
    }

    public String getTimeErrors() {
        return timeErrors;
    }

    public void setTimeErrors(String timeErrors) {
        this.timeErrors = timeErrors;
    }

    public String getTimeRecieve() {
        return timeRecieve;
    }

    public void setTimeRecieve(String timeRecieve) {
        this.timeRecieve = timeRecieve;
    }

    public String getUnitProcess() {
        return unitProcess;
    }

    public void setUnitProcess(String unitProcess) {
        this.unitProcess = unitProcess;
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

    public String getTimeRepeat() {
        return timeRepeat;
    }

    public void setTimeRepeat(String timeRepeat) {
        this.timeRepeat = timeRepeat;
    }
    public String getCodeTicket() {
        return codeTicket;
    }

    public void setCodeTicket(String codeTicket) {
        this.codeTicket = codeTicket;
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
