package com.mohammedfatih.mychatapp.activities;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.mohammedfatih.mychatapp.R;
import com.mohammedfatih.mychatapp.fragment.ChatUserFragment;
import com.mohammedfatih.mychatapp.fragment.ListUserfragment;
import com.mohammedfatih.mychatapp.interfaces.SnakebarMessage;

public class ChattingActivity extends AppCompatActivity implements SnakebarMessage {


    private final String TAG = "ChattingActivity";

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private ListUserfragment userListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        userListFragment = new ListUserfragment();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragmentplace, userListFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    @Override
    public void snakeBarMessage(String message) {
        Snackbar.make(findViewById(R.id.rooView),message,Snackbar.LENGTH_SHORT).show();
    }
}