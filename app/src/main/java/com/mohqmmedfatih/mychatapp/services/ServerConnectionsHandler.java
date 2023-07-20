package com.mohqmmedfatih.mychatapp.services;

import android.os.AsyncTask;
import android.util.Log;

import com.mohqmmedfatih.mychatapp.models.Message;
import com.mohqmmedfatih.mychatapp.tools.Config;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public abstract class ServerConnectionsHandler extends AsyncTask<ServerSocket,Void,Void> {
    private String TAG = "ServerConnectionsHandler";

    public abstract void newMessageIsRecieved(com.mohqmmedfatih.mychatapp.models.Message message);

    @Override
    protected Void doInBackground(ServerSocket... serverSockets) {

        try{

            while (Config.isAppWorking){
                Log.e(TAG,"server is listening ......");
                Socket socket = serverSockets[0].accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Log.e(TAG,"we have recieve something "+objectInputStream.available());
                Message messageReceived = (com.mohqmmedfatih.mychatapp.models.Message) objectInputStream.readObject();
                Log.e(TAG,"we have message ");
                objectInputStream.close();
                socket.close();
                Log.e(TAG,"the we close the socket to listening foir other reauest");
                newMessageIsRecieved(messageReceived);
            }

        }catch(Exception e){
            Log.e(TAG,"we have error in server listening and the error is :"+e.getMessage());
            e.printStackTrace();
        }


        return null;
    }
}
