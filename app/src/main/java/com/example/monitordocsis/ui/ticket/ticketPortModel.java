package com.example.monitordocsis.ui.ticket;

public class ticketPortModel  {
    private String onuActive;
    private String onuInActive;
    private String onuTotal;
    private String onuPercentInActive;
    private String onuAvgActive;
    private String createDate;

    public ticketPortModel(String onuActive, String onuInActive, String onuTotal, String onuPercentInActive, String onuAvgActive, String createDate) {
        this.onuActive = onuActive;
        this.onuInActive = onuInActive;
        this.onuTotal = onuTotal;
        this.onuPercentInActive = onuPercentInActive;
        this.onuAvgActive = onuAvgActive;
        this.createDate = createDate;
    }

    public String getOnuActive() {
        return onuActive;
    }

    public void setOnuActive(String onuActive) {
        this.onuActive = onuActive;
    }

    public String getOnuInActive() {
        return onuInActive;
    }

    public void setOnuInActive(String onuInActive) {
        this.onuInActive = onuInActive;
    }

    public String getOnuTotal() {
        return onuTotal;
    }

    public void setOnuTotal(String onuTotal) {
        this.onuTotal = onuTotal;
    }

    public String getOnuPercentInActive() {
        return onuPercentInActive;
    }

    public void setOnuPercentInActive(String onuPercentInActive) {
        this.onuPercentInActive = onuPercentInActive;
    }

    public String getOnuAvgActive() {
        return onuAvgActive;
    }

    public void setOnuAvgActive(String onuAvgActive) {
        this.onuAvgActive = onuAvgActive;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
