package com.mohqmmedfatih.mychatapp.models;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 6529685098267757691L;

    private String username;
    private String ip;
     public User(String ip,String username){
         this.username = username;
         this.ip = ip;
     }

     public String getUsername(){
         return this.username;
     }
     public String  getIp(){
         return this.ip;
     }


     public void setUsername (String username){
         this.username = username;
     }

     public void setIp (String ip){
         this.ip  = ip;
     }


    @Override
    public boolean equals(@Nullable Object obj) {
         if (!(obj instanceof User))return false;
         User tempUser =(User) obj;
        return this.ip.equalsIgnoreCase(tempUser.getIp());
    }
}
