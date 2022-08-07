package com.example.homely.Model;

public class BidStatus {
    String amount;
    String date;
    String projectName;
    String status;
    String userName;



    public BidStatus() {
    }


    public BidStatus(String amount, String date, String projectName, String status, String userName) {
        this.amount = amount;
        this.date = date;
        this.projectName = projectName;
        this.status = status;
        this.userName = userName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
