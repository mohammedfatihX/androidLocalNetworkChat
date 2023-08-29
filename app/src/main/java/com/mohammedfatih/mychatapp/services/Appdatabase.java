package com.mohammedfatih.mychatapp.services;

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

import com.mohammedfatih.mychatapp.Dao.MessagesDAO;
import com.mohammedfatih.mychatapp.Dao.ReceiverDAO;
import com.mohammedfatih.mychatapp.Dao.SenderDAO;
import com.mohammedfatih.mychatapp.models.Message;
import com.mohammedfatih.mychatapp.models.Receiver;
import com.mohammedfatih.mychatapp.models.Sender;

@Database(entities = {Message.class, Sender.class , Receiver.class} , version = 1,exportSchema = false)

public abstract class Appdatabase extends RoomDatabase {
    private static final String TAG = "Appdatabase";
    private static Appdatabase instance;
    private static String METAG;
    public  abstract MessagesDAO messageDAO();
    public abstract SenderDAO senderDAO();
    public abstract ReceiverDAO receiverDAO();
    @SuppressLint("HardwareIds")
    public static synchronized Appdatabase getInstance(Context context){
        METAG =Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
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
            new AddME(instance.senderDAO()).execute();
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
    private static class AddME extends AsyncTask<Void,Void,Void>{

        private SenderDAO senderDAO;
        public AddME(SenderDAO senderDAO){
            this.senderDAO = senderDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
       //     senderDAO.insertSender(new Sender("super Me","127.0.0.1",METAG, UUID.randomUUID()));
            return null;
        }
    }

    private static class addusers extends AsyncTask<Void,Void,Void>{

        private ReceiverDAO receiverDAO;
        public addusers(ReceiverDAO receiverDAO){
            this.receiverDAO = receiverDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
//            receiverDAO.inserReceiver(new Receiver("simo","192.168.1.7",null,UUID.randomUUID()));
//            receiverDAO.inserReceiver(new Receiver("krimo","192.168.1.8",null,UUID.randomUUID()));
//            receiverDAO.inserReceiver(new Receiver("rayanm","192.168.1.9",null,UUID.randomUUID()));
            return null;
        }
    }

}
