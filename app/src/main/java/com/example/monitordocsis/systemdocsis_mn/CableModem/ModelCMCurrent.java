package com.example.monitordocsis.systemdocsis_mn.CableModem;

public class ModelCMCurrent {
    private String status;
    private String snr;
    private String mer;
    private String fec;
    private String unfec;
    private String txpower;
    private String rxpower;
    private String cmIPaddress;
    private String numberOfCPE;
    private String CPEIp;
    private String CPEAddress;
    private String nameCMTS;
    private String nameNode;
    private String ifdecs;
    private String address;
    private String time;

    public ModelCMCurrent(String status, String snr, String mer, String fec, String unfec, String txpower, String rxpower, String cmIPaddress, String numberOfCPE, String CPEIp, String CPEAddress, String nameCMTS, String nameNode, String ifdecs, String address, String time) {
        this.status = status;
        this.snr = snr;
        this.mer = mer;
        this.fec = fec;
        this.unfec = unfec;
        this.txpower = txpower;
        this.rxpower = rxpower;
        this.cmIPaddress = cmIPaddress;
        this.numberOfCPE = numberOfCPE;
        this.CPEIp = CPEIp;
        this.CPEAddress = CPEAddress;
        this.nameCMTS = nameCMTS;
        this.nameNode = nameNode;
        this.ifdecs = ifdecs;
        this.address = address;
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCmIPaddress() {
        return cmIPaddress;
    }

    public void setCmIPaddress(String cmIPaddress) {
        this.cmIPaddress = cmIPaddress;
    }

    public String getNumberOfCPE() {
        return numberOfCPE;
    }

    public void setNumberOfCPE(String numberOfCPE) {
        this.numberOfCPE = numberOfCPE;
    }

    public String getCPEIp() {
        return CPEIp;
    }

    public void setCPEIp(String CPEIp) {
        this.CPEIp = CPEIp;
    }

    public String getCPEAddress() {
        return CPEAddress;
    }

    public void setCPEAddress(String CPEAddress) {
        this.CPEAddress = CPEAddress;
    }

    public String getNameCMTS() {
        return nameCMTS;
    }

    public void setNameCMTS(String nameCMTS) {
        this.nameCMTS = nameCMTS;
    }

    public String getNameNode() {
        return nameNode;
    }

    public void setNameNode(String nameNode) {
        this.nameNode = nameNode;
    }

    public String getIfdecs() {
        return ifdecs;
    }

    public void setIfdecs(String ifdecs) {
        this.ifdecs = ifdecs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
