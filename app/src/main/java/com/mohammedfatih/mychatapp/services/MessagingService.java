package com.mohammedfatih.mychatapp.services;

import android.util.Log;

import com.mohammedfatih.mychatapp.models.User;
import com.mohammedfatih.mychatapp.tools.Config;

import java.io.IOException;
import java.net.Socket;

public class MessagingService {
    private final static String TAG ="MessagingService";
    private static User previousUser;
    private static Socket socket;
    private static  String ip;

    private  MessagingService (){

    }


    public static synchronized Socket getInstance(User user ){
        try{
            if (socket == null ||!(previousUser.equals(user))){
                previousUser =user;
                return new Socket(user.getIp(), Config.MAINPORT);
            }
        }catch (IOException e){
          //  e.printStackTrace();
            Log.e(TAG,"error in getting instance of Socket amd it give this error : "+e.getMessage());
        }
        return socket;
    }
    public static synchronized Socket getInstance(String newIp ){
        try{
            if (socket == null ||!(ip.equals(newIp))){
                ip =newIp;
                return new Socket(newIp, Config.MAINPORT);
            }
        }catch (IOException e){
            e.printStackTrace();
            Log.e(TAG,"error in getting instance of Socket amd it give this error : "+e.getMessage());
        }
        return socket;
    }
}
