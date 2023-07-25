package com.mohqmmedfatih.mychatapp.activities;


import static com.mohqmmedfatih.mychatapp.fragment.ListUserfragment.welcomingUsers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mohqmmedfatih.mychatapp.R;
import com.mohqmmedfatih.mychatapp.fragment.ChatUserFragment;
import com.mohqmmedfatih.mychatapp.fragment.DialogFragment;
import com.mohqmmedfatih.mychatapp.fragment.ListUserfragment;
import com.mohqmmedfatih.mychatapp.interfaces.InputListener;

public class ChattingActivity extends AppCompatActivity implements InputListener , ChatUserFragment.ButtonVisibilityListener {


    private final String TAG = "ChattingActivity";
    private FloatingActionButton addUserbutton;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private ListUserfragment userListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        addUserbutton = findViewById(R.id.addUsers_btn);
        addUserbutton.setOnClickListener(event -> {
            alertDialogBuilder();
        });
        userListFragment = new ListUserfragment();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragmentplace, userListFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    private void alertDialogBuilder(){
        DialogFragment tt = new DialogFragment();
        tt.show(getSupportFragmentManager(),"dialog input");
    }


    @Override
    public void onClickListener(String ip) {
        welcomingUsers(ip);
    }




    @Override
    public void onButtonVisibilityChanged(boolean isVisible) {
        if (isVisible) {
            addUserbutton.setVisibility(View.VISIBLE);
        } else {
            addUserbutton.setVisibility(View.GONE);
        }
    }
}