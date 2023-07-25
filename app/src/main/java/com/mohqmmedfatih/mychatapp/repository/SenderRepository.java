package com.mohqmmedfatih.mychatapp.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import com.mohqmmedfatih.mychatapp.Dao.MessagesDAO;
import com.mohqmmedfatih.mychatapp.Dao.SenderDAO;
import com.mohqmmedfatih.mychatapp.models.Sender;
import com.mohqmmedfatih.mychatapp.models.User;
import com.mohqmmedfatih.mychatapp.services.Appdatabase;

import java.util.List;

public class SenderRepository {

    private static final  String TAG = "SenderReposotory";

    private SenderDAO senderDAO;

    public SenderRepository(Application application){
        Appdatabase appdatabase = Appdatabase.getInstance(application);
        senderDAO = appdatabase.senderDAO();
    }

    public void insertSender(Sender sender){
        new InsertSenderAsyncTask(senderDAO).execute(sender);
    }

    public void updateSender(Sender sender){
        new UpdateSenderAsyncTAsk(senderDAO).execute(sender);
    }
    public void deleteSender(Sender sender){
        new DeleteSenderAsyncTask(senderDAO).execute(sender);
    }
    public LiveData<List<Sender>> getAllSenders(){
        try{
            return  new GetAllSenders(senderDAO).execute().get();
        }catch (Exception e){
            Log.e(TAG,
                    "error while getting all senders and error is : "+e.getMessage());
            return null;
        }
    }

    public Sender findSenderBytag(String tag){
        Sender sender = null;
        try{
            sender = new FindSenderByTag(senderDAO).execute(tag).get();
            Log.e(TAG,"funnction get me is done successfully : "+sender);

        }catch (Exception e){
            Log.e(TAG,"funnction findSenderbytag is failed by error : "+e.getMessage() );

        }
        return sender;
    }


    private static  class InsertSenderAsyncTask extends AsyncTask<Sender,Void,Void>{
        private SenderDAO senderDAO;
        public InsertSenderAsyncTask(SenderDAO senderDAO){
            this.senderDAO = senderDAO;
        }
        @Override
        protected Void doInBackground(Sender... senders) {
            Log.e(TAG,"we have instert the Sender me");
            senderDAO.insertSender(senders[0]);
            return null;
        }
    }
    private static class UpdateSenderAsyncTAsk extends AsyncTask <Sender,Void,Void>{
        private SenderDAO senderDAO;
        public UpdateSenderAsyncTAsk(SenderDAO senderDAO){
            this.senderDAO = senderDAO;
        }
        @Override
        protected Void doInBackground(Sender... senders) {
            Log.e(TAG, " the senders is updated");
            senderDAO.updateSender(senders[0]);
            return null;
        }
    }

    private static  class DeleteSenderAsyncTask extends AsyncTask<Sender,Void,Void>{
        private SenderDAO senderDAO;
        public DeleteSenderAsyncTask(SenderDAO senderDAO){
            this.senderDAO = senderDAO;
        }
        @Override
        protected Void doInBackground(Sender... senders) {
            senderDAO.deleteSender(senders[0]);
            return null;
        }
    }

    private static class GetAllSenders extends AsyncTask<Void,Void,LiveData<List<Sender>>>{

        private SenderDAO senderDAO;

        public GetAllSenders(SenderDAO senderDAO){
            this.senderDAO = senderDAO;
        }

        @Override
        protected LiveData<List<Sender>> doInBackground(Void... voids) {
            Log.e(TAG,"get all senders ");
           return senderDAO.getAllSenders();

        }
    }

    private static  class FindSenderByTag extends AsyncTask<String,Void,Sender>{
        private SenderDAO senderDAO;
        public FindSenderByTag(SenderDAO senderDAO){
            this.senderDAO = senderDAO;
        }
        @Override
        protected Sender doInBackground(String... strings) {
            return senderDAO.getSenderByTag(strings[0]);

        }
    }

}
