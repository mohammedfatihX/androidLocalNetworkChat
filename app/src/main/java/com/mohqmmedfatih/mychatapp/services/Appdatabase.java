package com.mohqmmedfatih.mychatapp.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mohqmmedfatih.mychatapp.Dao.MessagesDAO;
import com.mohqmmedfatih.mychatapp.Dao.ReceiverDAO;
import com.mohqmmedfatih.mychatapp.Dao.SenderDAO;
import com.mohqmmedfatih.mychatapp.models.Message;
import com.mohqmmedfatih.mychatapp.models.Receiver;
import com.mohqmmedfatih.mychatapp.models.Sender;
import com.mohqmmedfatih.mychatapp.tools.Config;

import java.util.UUID;

@Database(entities = {Message.class, Sender.class , Receiver.class} , version = 1,exportSchema = false)

public abstract class Appdatabase extends RoomDatabase {
    private static final String TAG = "Appdatabase";
    private static Appdatabase instance;
    public  abstract MessagesDAO messageDAO();
    public abstract SenderDAO senderDAO();
    public abstract ReceiverDAO receiverDAO();
    public static synchronized Appdatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Appdatabase.class,"chatdatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }
        return instance;
    }


    private static RoomDatabase.Callback roomCallback = new  RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Log.e(TAG,"call room callback is start mow and onCreate function ");
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new addusers(instance.receiverDAO()).execute();
            Log.e(TAG,"call room callback is start mow and onOpen function ");
           // new OpenDataAsyncTask(instance).execute();
        }
    };


    private static class addusers extends AsyncTask<Void,Void,Void>{

        private ReceiverDAO receiverDAO;
        public addusers(ReceiverDAO receiverDAO){
            this.receiverDAO = receiverDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            receiverDAO.inserReceiver(new Receiver("simo","192.168.1.7",null,UUID.randomUUID()));
            receiverDAO.inserReceiver(new Receiver("krimo","192.168.1.8",null,UUID.randomUUID()));
            receiverDAO.inserReceiver(new Receiver("rayanm","192.168.1.9",null,UUID.randomUUID()));
            return null;
        }
    }

}
