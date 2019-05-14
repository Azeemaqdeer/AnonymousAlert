package com.example.azeee.mob;

public class ChatMessage {
private String sender;
private  String receiver;
private String mesage;
private  boolean isseen;
public ChatMessage(){}
    public ChatMessage(String sender , String receiver, String mesage, boolean isseen) {
        this.mesage = mesage;
        this.receiver=receiver;
        this.sender=sender;
        this.isseen = isseen;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public String getMesage() {
        return mesage;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
