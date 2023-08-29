package com.mohammedfatih.mychatapp.activities;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.mohammedfatih.mychatapp.R;
import com.mohammedfatih.mychatapp.fragment.ListUserfragment;

public class ChattingActivity extends AppCompatActivity {


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






}