package com.example.monitordocsis.congcu;

public class hopdongModel {
    private String nameCustomer;
    private String branch;
    private String phone;
    private String address;
    private String typeContract;
    private String statusContract;
    private String subnum;


    public hopdongModel(String nameCustomer, String branch, String phone, String address, String typeContract, String statusContract,String subnum) {
        this.nameCustomer = nameCustomer;
        this.branch = branch;
        this.phone = phone;
        this.address = address;
        this.typeContract = typeContract;
        this.statusContract = statusContract;
        this.subnum =subnum;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTypeContract() {
        return typeContract;
    }

    public void setTypeContract(String typeContract) {
        this.typeContract = typeContract;
    }

    public String getStatusContract() {
        return statusContract;
    }

    public void setStatusContract(String statusContract) {
        this.statusContract = statusContract;
    }

    public String getSubnum() {
        return subnum;
    }

    public void setSubnum(String subnum) {
        this.subnum = subnum;
    }
}
