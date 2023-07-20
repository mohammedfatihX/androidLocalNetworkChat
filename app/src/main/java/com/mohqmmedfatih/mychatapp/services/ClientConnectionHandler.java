package com.mohqmmedfatih.mychatapp.services;

import android.util.Log;

import com.mohqmmedfatih.mychatapp.models.Message;
import com.mohqmmedfatih.mychatapp.models.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.SocketHandler;

public class ClientConnectionHandler implements Runnable {
    private User user;
    private Message message;
    private final String TAG = "ClientConnectionHandler";


    public ClientConnectionHandler(User user , Message message){
        this.user = user;
        this.message = message;
    }
    @Override
    public void run() {
        Socket socket = MessagingService.getInstance(user);
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
            Log.e(TAG,"the message is send to receiver");
            objectOutputStream.flush();
            objectOutputStream.close();
            socket.close();
            Log.e(TAG,"the message is send to receiver");


        }catch(IOException e){
            Log.e(TAG,"error in the sending message and error is : "+e.getMessage());
            e.printStackTrace();
        }
    }



}
