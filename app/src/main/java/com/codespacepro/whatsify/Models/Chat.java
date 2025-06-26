package com.codespacepro.whatsify.Models;

public class Chat {
    String profile, fullname, lastmessage;
    String username, email, uid;
    Long pic;
    boolean online;


    public Chat() {
    }

//    public Chat(String profile, String fullname) {
//        this.profile = profile;
//        this.fullname = fullname;
//    }


    public Chat(String uid, String email, String fullname) {
        this.uid = uid;
        this.email = email;
        this.fullname = fullname;
    }

    public Chat(String uid, String email, String fullname, boolean online) {
        this.uid = uid;
        this.email = email;
        this.fullname = fullname;
        this.online = online;
    }


    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
