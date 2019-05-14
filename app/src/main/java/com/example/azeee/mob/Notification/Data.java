package com.example.azeee.mob.Notification;

public class Data {
public   String user;
public   int icon; public String sented;
public     String body; public String title;
    public  Data(){}
    public Data(String user,int icon,String body,String title,String sented){
        this.user=user;
        this.icon=icon;
        this.body=body;
        this.title=title;
        this.sented=sented;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getSented() {
        return sented;
    }

    public void setSented(String sented) {
        this.sented = sented;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
