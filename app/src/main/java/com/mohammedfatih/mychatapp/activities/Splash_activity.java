package com.mohammedfatih.mychatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.mohammedfatih.mychatapp.R;
import com.mohammedfatih.mychatapp.models.Sender;
import com.mohammedfatih.mychatapp.repository.SenderRepository;
import com.mohammedfatih.mychatapp.tools.Config;

import java.util.Objects;
import java.util.UUID;

public class Splash_activity extends AppCompatActivity {
    private ProgressBar loadingProgress ;
    private SenderRepository senderRepository;
    private final String TAG ="Splash_activity";
    private Handler handler;
    private String  tagME;

    @SuppressLint({"MissingInflatedId", "HardwareIds"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tagME = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        senderRepository = new SenderRepository(getApplication());
        if (!isConnectedToWifi(this)){
            startActivity(new Intent(Splash_activity.this,ConnectionIsNotSetUp.class));
            return;
        }
        Config.me = getMainUser();
        loadingProgress = findViewById(R.id.progressbar);
        loadingProgress.setVisibility(View.VISIBLE);
        handler = new Handler();
        handler.postDelayed(this::nextActivity,10000);

    }

    @SuppressLint("HardwareIds")
    private void nextActivity(){
        loadingProgress.setVisibility(View.INVISIBLE);
        if (Objects.equals(Config.me.getUsername(),"super Me")){
            startActivity(new Intent(Splash_activity.this,AppFeautre.class));
            Log.e(TAG, "the time one the app ");
        }else {
            Log.e(TAG, "is not the first time opend the app");
            startActivity(new Intent(Splash_activity.this,ChattingActivity.class ));
        }

        Log.e(TAG,"the tag is : " +Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
    }


    private Sender getMainUser(){
        Sender sender = (Sender) senderRepository.findSenderBytag(tagME);
        if (sender == null ){
            Log.e(TAG,"Config me is null");
            sender = senderRepository.insertSender(new Sender("super Me",Config.getMyIp(Splash_activity.this),tagME, UUID.randomUUID()));

        }
        return sender;
    }

    public static boolean isConnectedToWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else {
            return false;
        }
    }

}