package com.mohammedfatih.mychatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mohammedfatih.mychatapp.R;
import com.mohammedfatih.mychatapp.models.Sender;
import com.mohammedfatih.mychatapp.repository.SenderRepository;
import com.mohammedfatih.mychatapp.tools.Config;

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