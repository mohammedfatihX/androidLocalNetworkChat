package com.mohqmmedfatih.mychatapp.services;

import android.annotation.SuppressLint;
import android.util.Log;
import com.mohqmmedfatih.mychatapp.models.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ServerConnectionsHandler {

    private static final String TAG = "ServerConnectionsHandler";
    private final int NUMBER_OF_THREADS = 1;
    private ExecutorService executorService;
    private ServerSocket serverSocket;

    public void startListening(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
      /*  Thread thread = new Thread(connectionTask);
        thread.setDaemon(true);*/
        executorService.execute(connectionTask);
    }

    @SuppressLint("LongLogTag")
    public void stopListening() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error closing server socket: " + e.getMessage());
            e.printStackTrace();
        }
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }

    private final Runnable connectionTask = new Runnable() {
        @SuppressLint("LongLogTag")
        @Override
        public void run() {
            try {
                while (!executorService.isShutdown()) {
                    Log.e(TAG, "Server is listening ......");
                    Socket socket = serverSocket.accept();
                    processSocket(socket);
                }
            } catch (IOException e) {
                if (!executorService.isShutdown()) {
                    Log.e(TAG, "Error accepting client connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    };

    @SuppressLint("LongLogTag")
    private void processSocket(Socket socket) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
            Log.d(TAG, "Received data from client");
            Message messageReceived = (Message) objectInputStream.readObject();
            objectInputStream.close();
            socket.close();
            Log.d(TAG, "Socket closed for client");
            newMessageIsReceived(messageReceived);
        } catch (Exception e) {
            Log.e(TAG, "Error processing client data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public abstract void newMessageIsReceived(Message message);
}
