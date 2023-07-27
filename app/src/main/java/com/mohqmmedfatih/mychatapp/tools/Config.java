package com.mohqmmedfatih.mychatapp.tools;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import com.mohqmmedfatih.mychatapp.models.Sender;

public class Config {
    private static final String TAG = "Config";
    public static String username;
    public static final int MAINPORT = 20000;
    public static volatile String MainReceiver;
    public static volatile boolean isAppWorking = true;
    public static Sender me ;

    //public  volatile static UsersChat WHOLECHAT = new UsersChat();


    public static String getMyIp(Context context) {
        //app support write now just 192.168.0.0 local network

        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        /*  try {
          Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface currentinterface = interfaces.nextElement();
                if ((!currentinterface.isUp()) || (currentinterface.isLoopback() || currentinterface.isVirtual())) {
                    Log.e(TAG,"non working network in android : " +currentinterface);
//                    System.out.print("this is a non working network : ");
//                    System.out.println(currentinterface.getName());
                    continue;
                }

                Enumeration<InetAddress> adresses = currentinterface.getInetAddresses();
                while (adresses.hasMoreElements()) {

                    Log.e(TAG,"++++++++++++++++++++++++++ working network in android : " +currentinterface);

                    InetAddress address = adresses.nextElement();
                    Log.e(TAG," ip working/nonworking  : "+address.getHostAddress() );

                    if (address.isReachable(1000)) {
                        Log.e(TAG," ip working  : "+address.getHostAddress() );

                        // System.out.println(InetAddress);
                        if (address.getHostAddress().contains("192.168")){
                            myIp = address.getHostAddress();
                            System.out.println("host name if this user is " + address.getHostName());
                            System.out.println("Active IP: " + address.getHostAddress());
                        }

                    }
                }
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        }*/
        return ip;
    }

}


