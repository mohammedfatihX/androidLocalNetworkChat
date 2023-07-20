package com.mohqmmedfatih.mychatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mohqmmedfatih.mychatapp.R;
import com.mohqmmedfatih.mychatapp.models.User;
import com.mohqmmedfatih.mychatapp.tools.Config;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private EditText usernameInput;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.startchatting);
        usernameInput =findViewById(R.id.usernameinput);



        start.setOnClickListener(event ->{
            if (usernameInput.getText() == null || "".equalsIgnoreCase(String.valueOf(usernameInput.getText()))){
                Toast.makeText(this,"plase enter an Username to start chatting",Toast.LENGTH_SHORT).show();
                return;
            }
            Config.username = String.valueOf(usernameInput.getText());
            startServices();
        });



    }




    public void startServices (){
        Config.me = new User(Config.getMyIp(MainActivity.this),Config.username);

        Toast.makeText(this, "your ip is "+Config.me.getIp(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this,ChattingActivity.class));
    }
}