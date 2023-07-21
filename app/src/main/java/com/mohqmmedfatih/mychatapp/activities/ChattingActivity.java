package com.mohqmmedfatih.mychatapp.activities;

import static com.mohqmmedfatih.mychatapp.tools.Config.WHOLECHAT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mohqmmedfatih.mychatapp.R;
import com.mohqmmedfatih.mychatapp.fragment.ChatUserFragment;
import com.mohqmmedfatih.mychatapp.fragment.DialogFragment;
import com.mohqmmedfatih.mychatapp.fragment.ListUserfragment;
import com.mohqmmedfatih.mychatapp.interfaces.InputListener;
import com.mohqmmedfatih.mychatapp.models.Message;
import com.mohqmmedfatih.mychatapp.models.MessageType;
import com.mohqmmedfatih.mychatapp.models.User;
import com.mohqmmedfatih.mychatapp.services.ClientConnectionHandler;
import com.mohqmmedfatih.mychatapp.services.ServerConnectionsHandler;
import com.mohqmmedfatih.mychatapp.tools.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChattingActivity extends AppCompatActivity implements InputListener , ChatUserFragment.ButtonVisibilityListener {

    private FloatingActionButton addUserbutton;
    private final String TAG = "ChattingActivity";
    private ServerSocket serverSocket;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private ListUserfragment userListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        addUserbutton = findViewById(R.id.addUsers_btn);
        WHOLECHAT.put(Config.me, new ArrayList<>());
       startServerSocketListening();
        addUserbutton.setOnClickListener(event -> {
            alertDialogBuilder();
        });
        userListFragment = new ListUserfragment();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragmentplace, userListFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }



    private void startServerSocketListening(){
        try {
            serverSocket = new ServerSocket(Config.MAINPORT);

            Log.e(TAG,"we have create serverSocket without Error ");
        } catch (IOException e) {
            Log.e(TAG,"we have some error in the creation of the serverSocket and the error is :  "+e.getMessage());
            throw new RuntimeException(e);
        }

        @SuppressLint("StaticFieldLeak")
        ServerConnectionsHandler server = new ServerConnectionsHandler() {
            @Override
            public void newMessageIsRecieved(Message message) {
                messageFiltering(message);
            }
        };


        server.execute(serverSocket);
    }


    private void alertDialogBuilder(){
        DialogFragment tt = new DialogFragment();
        tt.show(getSupportFragmentManager(),"dialog input");
    }

    private void messageFiltering( Message message){
        switch(message.getMessageType()){
            case MESSAGE:
               // Toast.makeText(ChattingActivity.this,"we have recived a message",Toast.LENGTH_SHORT);
                if (WHOLECHAT.isUserExist(message.getSender())){
                    WHOLECHAT.get(message.getSender()).add(message);
                    runOnUiThread(()->{
                        userListFragment.chatUserFragment.onReceivedNewMessage(message);

                    });
                }else {
                    welcomingUsers(message.getSender());
                }

                break;

            case NEWCONNECTION:
               // Toast.makeText(ChattingActivity.this,"we have recived a user",Toast.LENGTH_SHORT);

                Log.e(TAG,"the message recieved is  : "+message);
                if(WHOLECHAT.isUserExist(message.getSender())){


                }else {
                    welcomingUsers(message.getSender());
                    WHOLECHAT.put(message.getSender(),new ArrayList<>());
                    runOnUiThread(()->{
                        userListFragment.onReceivedNewUser(message.getSender());

                    });
                }



                break;


            case CLOSECONNCTION:


                break;
        }
    }


    private void sendMessage(User recepient,Message message){
        new Thread(new ClientConnectionHandler(recepient,message)).start();
    }




    @Override
    public void onClickListener(String ip) {
        welcomingUsers(new User(ip,"user"));
    }


    public void welcomingUsers(User user){
        Message message = new Message(MessageType.NEWCONNECTION,null,Config.me);
        new Thread(new ClientConnectionHandler(user,message)).start();

    }

    @Override
    public void onButtonVisibilityChanged(boolean isVisible) {
        if (isVisible) {
            addUserbutton.setVisibility(View.VISIBLE);
        } else {
            addUserbutton.setVisibility(View.GONE);
        }
    }
}