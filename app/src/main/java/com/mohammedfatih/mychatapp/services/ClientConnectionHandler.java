package com.mohammedfatih.mychatapp.services;

import android.util.Log;

import com.mohammedfatih.mychatapp.interfaces.MessageListener;
import com.mohammedfatih.mychatapp.models.Message;
import com.mohammedfatih.mychatapp.models.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {
    private User user;
    private Message message;
    private String ip;
    private MessageListener messageListener;
    private static final String TAG = "ClientConnectionHandler";

    public ClientConnectionHandler(String ip, Message message,MessageListener messageListener) {
       this.messageListener = messageListener;
        this.user = null;
        this.message = message;
        this.ip = ip;
    }

    public ClientConnectionHandler(User user, Message message, MessageListener messageListener) {
        this.messageListener = messageListener;
        this.user = user;
        this.message = message;
        this.ip = null;
    }

    @Override
    public void run() {
        if (ip != null) {
            send(ip);
        } else if (user != null) {
            send(user);
        }
    }

    private void send(String ip) {
        try (Socket socket = MessagingService.getInstance(ip)) {
            if (socket == null){
                messageListener.onMessageSendingFailed();
                return;
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
            Log.e(TAG, "The message is sent to the receiver");
        } catch (IOException e) {
            Log.e(TAG, "Error in sending message. Error is: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void send(User user) {
        try (Socket socket = MessagingService.getInstance(user)) {
            if (socket == null){
                messageListener.onMessageSendingFailed();
                return;
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
            Log.e(TAG, "The message is sent to the receiver");
            messageListener.onMessageSendingSuccessfully(message);
        } catch (IOException e) {
            Log.e(TAG, "Error in sending message. Error is: " + e.getMessage());
           // e.printStackTrace();
            messageListener.onMessageSendingFailed();
        }
    }
}
