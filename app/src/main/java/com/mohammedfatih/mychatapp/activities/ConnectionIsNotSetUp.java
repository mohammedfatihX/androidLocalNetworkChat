package com.mohammedfatih.mychatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.mohammedfatih.mychatapp.R;

public class ConnectionIsNotSetUp extends AppCompatActivity {
    private Button restart;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_is_not_set_up);
        restart = findViewById(R.id.restartbutton);

        restart.setOnClickListener(event ->{
            startActivity(new Intent(ConnectionIsNotSetUp.this,Splash_activity.class));
        });

    }
}