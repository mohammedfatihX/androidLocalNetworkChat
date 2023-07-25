package com.mohqmmedfatih.mychatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mohqmmedfatih.mychatapp.R;
import com.mohqmmedfatih.mychatapp.models.Sender;
import com.mohqmmedfatih.mychatapp.models.User;
import com.mohqmmedfatih.mychatapp.repository.SenderRepository;
import com.mohqmmedfatih.mychatapp.tools.Config;

public class MainActivity extends AppCompatActivity {
    private SenderRepository senderRepository;
    private Button start;
    private EditText usernameInput;
    private final String TAG ="MAinActivity";
    private String tagME;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.startchatting);
        usernameInput =findViewById(R.id.usernameinput);
        senderRepository = new SenderRepository(getApplication());
        tagME = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        start.setOnClickListener(event ->{
            if (usernameInput.getText() == null || "".equalsIgnoreCase(String.valueOf(usernameInput.getText()))){
                Toast.makeText(this,"please enter an Username to start chatting",Toast.LENGTH_SHORT).show();
                return;
            }
            Sender sender = senderRepository.findSenderBytag(tagME);
            sender.setUsername(usernameInput.getText().toString());
            senderRepository.updateSender(sender);
            startServices();
        });



    }




    public void startServices (){
//        Toast.makeText(this, "your ip is "+Config.me.getIp(), Toast.LENGTH_SHORT).show();
        Config.me =senderRepository.findSenderBytag(tagME);
        startActivity(new Intent(MainActivity.this,ChattingActivity.class));
        finish();
    }
}