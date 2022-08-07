package com.example.homely.Model;

public class MessageModel {

    String uid, message;

    public MessageModel(String uid, String message) {
        this.uid = uid;
        this.message = message;
    }

    public MessageModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
