package com.mohammedfatih.mychatapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

import com.mohammedfatih.mychatapp.R;
import com.mohammedfatih.mychatapp.fragment.FirstFragment;
import com.mohammedfatih.mychatapp.fragment.SecondFragment;
import com.mohammedfatih.mychatapp.fragment.ThirdFragment;

public class AppFeautre extends AppCompatActivity {
    Button next ;
    int featurecounting =2;
    private FragmentManager fragmentManager;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appfeature);
        next = findViewById(R.id.nextbutton);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentfragment,new FirstFragment());
        transaction.addToBackStack(null);
        transaction.commit();



           next.setOnClickListener(event ->{
            System.out.println(featurecounting);

            switch (featurecounting){
              case 1 :
                  transaction = fragmentManager.beginTransaction();
                  transaction.add(R.id.contentfragment,new FirstFragment());
                  transaction.addToBackStack(null);
                  transaction.commit();
                  featurecounting++;
                  System.out.println(featurecounting);

                  break;

              case 2 :
                  transaction = fragmentManager.beginTransaction();
                  transaction.replace(R.id.contentfragment,new SecondFragment());
                  transaction.addToBackStack(null);
                  transaction.commit();
                  featurecounting++;

                  System.out.println(featurecounting);
                  break;


              case 3 :
                  transaction = fragmentManager.beginTransaction();
                  transaction.replace(R.id.contentfragment,new ThirdFragment());
                  transaction.addToBackStack(null);
                  transaction.commit();
                  System.out.println(featurecounting);

                  featurecounting++;


                  break;


              case 4 :
                  startActivity(new Intent(AppFeautre.this,MainActivity.class));

                  break;
          }
        });





    }




    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
}