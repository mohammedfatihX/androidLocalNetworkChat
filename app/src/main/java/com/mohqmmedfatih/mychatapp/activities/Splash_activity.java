package com.mohqmmedfatih.mychatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import com.mohqmmedfatih.mychatapp.R;

public class Splash_activity extends AppCompatActivity {
    private ProgressBar loadingProgress ;
    private Handler handler ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadingProgress = findViewById(R.id.progressbar);
        loadingProgress.setVisibility(View.VISIBLE);
        handler = new Handler();
        handler.postDelayed(this::nextActivity,2000);
    }

    private void nextActivity(){
        loadingProgress.setVisibility(View.INVISIBLE);
        startActivity(new Intent(Splash_activity.this,AppFeautre.class));
    }
}