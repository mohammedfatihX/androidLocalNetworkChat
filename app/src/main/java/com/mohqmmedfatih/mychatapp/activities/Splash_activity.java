package com.mohqmmedfatih.mychatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.mohqmmedfatih.mychatapp.R;
import com.mohqmmedfatih.mychatapp.models.Sender;
import com.mohqmmedfatih.mychatapp.repository.ReceiverRepository;
import com.mohqmmedfatih.mychatapp.repository.SenderRepository;
import com.mohqmmedfatih.mychatapp.services.Appdatabase;
import com.mohqmmedfatih.mychatapp.tools.Config;

import java.util.UUID;

public class Splash_activity extends AppCompatActivity {
    private ProgressBar loadingProgress ;
    private SenderRepository senderRepository;
    private final String TAG ="Splash_activity";
    private Handler handler ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        @SuppressLint("HardwareIds")
        String tagME = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        senderRepository = new SenderRepository(getApplication());
        loadingProgress = findViewById(R.id.progressbar);
        loadingProgress.setVisibility(View.VISIBLE);
        Config.me= (Sender) senderRepository.findSenderBytag(tagME);
        if (Config.me == null){
            Log.e(TAG,"Config me is null");
            senderRepository.insertSender(new Sender("super Me",Config.getMyIp(Splash_activity.this),tagME, UUID.randomUUID()));
        }
        handler = new Handler();
        handler.postDelayed(this::nextActivity,10000);

    }

    @SuppressLint("HardwareIds")
    private void nextActivity(){
        loadingProgress.setVisibility(View.INVISIBLE);
        if (Config.me.getUsername().equalsIgnoreCase("super Me")){
            startActivity(new Intent(Splash_activity.this,AppFeautre.class));
            Log.e(TAG, "the time one the app ");
        }else {
            Log.e(TAG, "is not the first time opend the app");
            startActivity(new Intent(Splash_activity.this,ChattingActivity.class ));
        }

        Log.e(TAG,"the tag is : " +Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
    }
}