package com.mohqmmedfatih.mychatapp.tools;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {


    public static String getDate(){

            @SuppressLint("SimpleDateFormat")
            DateFormat dateFormat = new SimpleDateFormat("y-M-d H:m:s");

        Date date = new Date();
        return dateFormat.format(date);
    }


    public static boolean isIpReceiverConnected (String ip){

        return true;
    }



}