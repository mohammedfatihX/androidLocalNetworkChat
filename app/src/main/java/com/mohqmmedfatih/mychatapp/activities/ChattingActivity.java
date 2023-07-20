package com.mohqmmedfatih.mychatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mohqmmedfatih.mychatapp.R;
import com.mohqmmedfatih.mychatapp.fragment.DialogFragment;
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

public class ChattingActivity extends AppCompatActivity implements InputListener {

    private FloatingActionButton addUserbutton;
    private final String TAG = "ChattingActivity";
    private ServerSocket serverSocket;
    public ArrayList<User> users ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        addUserbutton = findViewById(R.id.addUsers_btn);
        users = new ArrayList<>(10);
        users.add(Config.me);
       startServerSocketListening();
        addUserbutton.setOnClickListener(event -> {
            alertDialogBuilder();

           /* AddUserInputIpDialog dialog = new AddUserInputIpDialog(ChattingActivity.this) {
                @Override
                public void onClickListener(String ip) {
                    Log.e(TAG,"ip from Dialog is  : "+ip);
                }
            };*/
        });



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
                Toast.makeText(ChattingActivity.this,"we have recived a message",Toast.LENGTH_SHORT);
                break;

            case NEWCONNECTION:
                Toast.makeText(ChattingActivity.this,"we have recived a message",Toast.LENGTH_SHORT);


                break;


            case CLOSECONNCTION:
                Toast.makeText(ChattingActivity.this,"we have recived a message",Toast.LENGTH_SHORT);


                break;
        }
    }


    private void sendMessage(User recepient,Message message){
        new Thread(new ClientConnectionHandler(recepient,message)).start();
    }

    private void newConnectionMessage(User recepient){
        Message message = new Message(MessageType.NEWCONNECTION,null,recepient);
        new Thread(new ClientConnectionHandler(recepient,message)).start();
    }


    @Override
    public void onClickListener(String ip) {
        Toast.makeText(this, "ip add is : "+ip, Toast.LENGTH_SHORT).show();
    }
}