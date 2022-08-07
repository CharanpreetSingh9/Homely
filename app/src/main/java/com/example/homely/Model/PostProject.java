package com.example.homely.Model;

public class PostProject {

    String name;
    String area;
    String budget;
    String location;
    String material;
    String id;
    String date;
    String pushid;
    String endDate;
    String postUser;

    public PostProject() {
    }

    public PostProject(String name, String area, String budget, String location, String material, String id, String date, String pushid, String endDate, String postUser) {
        this.name = name;
        this.area = area;
        this.budget = budget;
        this.location = location;
        this.material = material;
        this.id = id;
        this.date = date;
        this.pushid = pushid;
        this.endDate = endDate;
        this.postUser = postUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPushid() {
        return pushid;
    }

    public void setPushid(String pushid) {
        this.pushid = pushid;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPostUser() {
        return postUser;
    }

    public void setPostUser(String postUser) {
        this.postUser = postUser;
    }
}

