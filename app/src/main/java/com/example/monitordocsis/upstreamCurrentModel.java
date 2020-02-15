package com.example.monitordocsis;

public class upstreamCurrentModel {
    private String node;
    private String ifdesc;
    private String snr;
    private String mer;
    private String fec;
    private String unfec;
    private String tx;
    private String rx;
    private String act;
    private String total;
    private String reg;
    private String fre;
    private String wth;
    private String micref;
    private String mod;
    private String time;
    private String cmtsid;
    private String ifindex;

    public upstreamCurrentModel(String node, String ifdesc, String snr, String mer, String fec, String unfec, String tx, String rx, String act, String total, String reg, String fre, String wth, String micref, String mod, String time, String cmtsid, String ifindex) {
        this.node = node;
        this.ifdesc = ifdesc;
        this.snr = snr;
        this.mer = mer;
        this.fec = fec;
        this.unfec = unfec;
        this.tx = tx;
        this.rx = rx;
        this.act = act;
        this.total = total;
        this.reg = reg;
        this.fre = fre;
        this.wth = wth;
        this.micref = micref;
        this.mod = mod;
        this.time = time;
        this.cmtsid = cmtsid;
        this.ifindex = ifindex;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getIfdesc() {
        return ifdesc;
    }

    public void setIfdesc(String ifdesc) {
        this.ifdesc = ifdesc;
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

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getRx() {
        return rx;
    }

    public void setRx(String rx) {
        this.rx = rx;
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

    public String getWth() {
        return wth;
    }

    public void setWth(String wth) {
        this.wth = wth;
    }

    public String getMicref() {
        return micref;
    }

    public void setMicref(String micref) {
        this.micref = micref;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
