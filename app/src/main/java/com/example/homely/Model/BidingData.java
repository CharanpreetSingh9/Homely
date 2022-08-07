package com.example.homely.Model;

public class BidingData {

    String amount;
    String companyName;
    String fullname;
    String id;
    String status;
    String projectName;
    String Date;

    public BidingData(String amount, String companyName, String fullname, String id, String status, String projectName, String date) {
        this.amount = amount;
        this.companyName = companyName;
        this.fullname = fullname;
        this.id = id;
        this.status = status;
        this.projectName = projectName;
        Date = date;
    }

    public BidingData() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
