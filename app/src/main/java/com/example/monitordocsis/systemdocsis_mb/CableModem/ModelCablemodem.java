package com.example.monitordocsis.systemdocsis_mb.CableModem;

public class ModelCablemodem {
    private String mac;
    private String address;
    private String branch;
    private String status;
    private String nameCMTS;
    private String node;
    private String snr;
    private String mer;
    private String fec;
    private String unfec;
    private String txPower;
    private String rxPower;
    private String cmtsID;
    private String ifindex;
    private String time;

    public ModelCablemodem(String mac, String address, String branch, String status, String nameCMTS, String node, String snr, String mer, String fec, String unfec, String txPower, String rxPower, String cmtsID, String ifindex, String time) {
        this.mac = mac;
        this.address = address;
        this.branch = branch;
        this.status = status;
        this.nameCMTS = nameCMTS;
        this.node = node;
        this.snr = snr;
        this.mer = mer;
        this.fec = fec;
        this.unfec = unfec;
        this.txPower = txPower;
        this.rxPower = rxPower;
        this.cmtsID = cmtsID;
        this.ifindex = ifindex;
        this.time = time;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNameCMTS() {
        return nameCMTS;
    }

    public void setNameCMTS(String nameCMTS) {
        this.nameCMTS = nameCMTS;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
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

    public String getTxPower() {
        return txPower;
    }

    public void setTxPower(String txPower) {
        this.txPower = txPower;
    }

    public String getRxPower() {
        return rxPower;
    }

    public void setRxPower(String rxPower) {
        this.rxPower = rxPower;
    }

    public String getCmtsID() {
        return cmtsID;
    }

    public void setCmtsID(String cmtsID) {
        this.cmtsID = cmtsID;
    }

    public String getIfindex() {
        return ifindex;
    }

    public void setIfindex(String ifindex) {
        this.ifindex = ifindex;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
