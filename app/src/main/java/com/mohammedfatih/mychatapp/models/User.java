package com.mohammedfatih.mychatapp.models;

import androidx.annotation.Nullable;
import androidx.room.Ignore;

import java.io.Serializable;

public class User implements Serializable {
    @Ignore
    private static final long serialVersionUID = 6529685098267757691L;
    private String username;
    private String tag;
    private String ip;



    public User(String username ,String ip , String tag){
        this.username = username;
        this.ip = ip;
        this.tag = tag;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof User))return false;
        User tempUser =(User) obj;
        return this.ip.equalsIgnoreCase(tempUser.getIp());
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}