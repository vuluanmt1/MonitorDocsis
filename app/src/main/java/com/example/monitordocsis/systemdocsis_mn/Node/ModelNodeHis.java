package com.example.monitordocsis.systemdocsis_mn.Node;

public class ModelNodeHis {
    private String snr;
    private String mer;
    private String fec ;
    private String unfec;
    private String txpower;
    private String rxpower;
    private String act ;
    private String total;
    private String reg;
    private String fre;
    private String with;
    private String modifileDate;
    private String cmtsid;
    private String ifindex;

    public ModelNodeHis(String snr, String mer, String fec, String unfec, String txpower, String rxpower, String act, String total, String reg, String fre, String with, String modifileDate, String cmtsid, String ifindex) {
        this.snr = snr;
        this.mer = mer;
        this.fec = fec;
        this.unfec = unfec;
        this.txpower = txpower;
        this.rxpower = rxpower;
        this.act = act;
        this.total = total;
        this.reg = reg;
        this.fre = fre;
        this.with = with;
        this.modifileDate = modifileDate;
        this.cmtsid = cmtsid;
        this.ifindex = ifindex;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    public String getMer() {
        return mer;
    }

    public void setMer(String mer) {
        this.mer = mer;
    }

    public String getFec() {
        return fec;
    }

    public void setFec(String fec) {
        this.fec = fec;
    }

    public String getUnfec() {
        return unfec;
    }

    public void setUnfec(String unfec) {
        this.unfec = unfec;
    }

    public String getTxpower() {
        return txpower;
    }

    public void setTxpower(String txpower) {
        this.txpower = txpower;
    }

    public String getRxpower() {
        return rxpower;
    }

    public void setRxpower(String rxpower) {
        this.rxpower = rxpower;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getFre() {
        return fre;
    }

    public void setFre(String fre) {
        this.fre = fre;
    }

    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }

    public String getModifileDate() {
        return modifileDate;
    }

    public void setModifileDate(String modifileDate) {
        this.modifileDate = modifileDate;
    }

    public String getCmtsid() {
        return cmtsid;
    }

    public void setCmtsid(String cmtsid) {
        this.cmtsid = cmtsid;
    }

    public String getIfindex() {
        return ifindex;
    }

    public void setIfindex(String ifindex) {
        this.ifindex = ifindex;
    }
}
