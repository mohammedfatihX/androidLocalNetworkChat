package com.mohqmmedfatih.mychatapp.models;

import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class  UsersChat extends LinkedHashMap<User, ArrayList<Message>> {

    public  ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>(10);
        if (this.isEmpty() || null == this){
            new RuntimeException("UsersChat isnull or empty");
        }else {
            Iterator iter = this.keySet().iterator();
            while(iter.hasNext()){
                users.add((User) iter.next());
            }
        }

        return users;
    }

    public boolean isUserExist(User user){
      if (this.isEmpty()){
          return false;
        }
      return this.getUsers().contains((User)user);

    }

    public User getUser (int index){
        return getUsers().get(index);
    }

    public User getLastUser(){
        return getUser(getUsers().size()-1);
    }

    public int getLastindex(){
        return getUsers().size()-1;
    }
}
