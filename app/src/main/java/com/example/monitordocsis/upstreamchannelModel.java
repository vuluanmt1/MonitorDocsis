package com.example.monitordocsis;

public class upstreamchannelModel {
    private String node ;
    private String inteface ;
    private String branch;
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
    private String nameCMTS;
    private String modifileDate;
    private String cmtsid;
    private String ifindex;

    public upstreamchannelModel(){
    }

    public upstreamchannelModel(String node, String inteface, String branch, String snr, String mer, String fec, String unfec, String txpower, String rxpower, String act, String total, String reg, String fre, String with, String nameCMTS, String modifileDate, String cmtsid, String ifindex) {

        this.node = node;
        this.inteface = inteface;
        this.branch = branch;
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
        this.nameCMTS = nameCMTS;
        this.modifileDate = modifileDate;
        this.cmtsid =cmtsid;
        this.ifindex =ifindex;

    }
    public String getNode() {
        return node;
    }
    public void setNode(String node) {
        this.node = node;
    }

    public String getInteface() {
        return inteface;
    }

    public void setInteface(String inteface) {
        this.inteface = inteface;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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

    public String getNameCMTS() {
        return nameCMTS;
    }

    public void setNameCMTS(String nameCMTS) {
        this.nameCMTS = nameCMTS;
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
