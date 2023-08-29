package com.mohammedfatih.mychatapp.tools;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Tools {
    final static  String TAG = "TOOLS";
    public static String getDate(){

            @SuppressLint("SimpleDateFormat")
            DateFormat dateFormat = new SimpleDateFormat("y-M-d H:m:s");

        Date date = new Date();
        return dateFormat.format(date);
    }
    public static  String getHourANDSecond(String stringDate){
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("H:m");
        String result ="err";
        try{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("y-M-d H:m:s");
                LocalDate date = LocalDate.parse(stringDate,dateTimeFormatter);
                result = dateFormat.format(date);
            }
        }catch (Exception e){
            Log.e(TAG,"error in the translate date from long date to short one");
        }
        return result;
    }

    public static boolean isIpReceiverConnected (String ip){

        return true;
    }



}