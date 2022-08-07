package com.example.homely.Model;

public class Data {

    String id;
    String fullname;
    String email;
    String password;
    String phoneno;
    String date;
    int UserTypes;
    String companyName;
    String companyFound;
    String companyBuild;


    public Data(String id, String fullname, String email, String password, String phoneno, String date, int userTypes) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phoneno = phoneno;
        this.date = date;
        UserTypes = userTypes;
    }

    public Data(String id, String fullname, String email, String password, String phoneno, String date, int userTypes, String companyName, String companyFound, String companyBuild) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phoneno = phoneno;
        this.date = date;
        UserTypes = userTypes;
        this.companyName = companyName;
        this.companyFound = companyFound;
        this.companyBuild = companyBuild;
    }

    public Data() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserTypes() {
        return UserTypes;
    }

    public void setUserTypes(int userTypes) {
        UserTypes = userTypes;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyFound() {
        return companyFound;
    }

    public void setCompanyFound(String companyFound) {
        this.companyFound = companyFound;
    }

    public String getCompanyBuild() {
        return companyBuild;
    }

    public void setCompanyBuild(String companyBuild) {
        this.companyBuild = companyBuild;
    }
}
