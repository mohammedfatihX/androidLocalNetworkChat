package com.mohqmmedfatih.mychatapp.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.mohqmmedfatih.mychatapp.Dao.ReceiverDAO;
import com.mohqmmedfatih.mychatapp.models.Receiver;
import com.mohqmmedfatih.mychatapp.services.Appdatabase;

import org.w3c.dom.ls.LSInput;

import java.security.PublicKey;
import java.util.List;
import java.util.UUID;

public class ReceiverRepository {

    private final String TAG = "ReceiverRepository";
    private ReceiverDAO receiverDAO;

    public ReceiverRepository(Application application){
        Appdatabase appdatabase = Appdatabase.getInstance(application);
        receiverDAO = appdatabase.receiverDAO();
    }

    public void insertReceiver(Receiver receiver){
        new InsertReceiverAsyncTask(receiverDAO).execute(receiver);
    }

    public void updateReceiver(Receiver receiver){
        new UpdateReceiverAsyncTask(receiverDAO).execute(receiver);
    }


    public void deleteReceiver(Receiver receiver){
        new DeleteReceiverAsyncTask(receiverDAO).execute(receiver);

    }

    public LiveData<List<Receiver>> getallReceivers(){
       LiveData<List<Receiver>> getAllReciever = null;
       try{
           getAllReciever = new GetAllReceivers(receiverDAO).execute().get();
       }catch (Exception e){
            Log.e(TAG,"we have error in the RecieverRepositoory and the error is  : "+e.getMessage());
       }
       return getAllReciever;
    }

    public Receiver getReceiverByUuid(UUID uuid){
        try {
            return new  FindReceiverByUuid(receiverDAO).execute(uuid).get();
        }catch (Exception e){
            Log.e(TAG,"error finding Receiver : "+e.getMessage());
            return null;
        }

    }

    public Receiver getReceiverByIP(String ip){
        try{
            return new FindReceiverByIP(receiverDAO).execute(ip).get();
        }catch (Exception e){
            Log.e(TAG,"error finding Receiver : "+e.getMessage());
            return null;
        }
    }




    private static class InsertReceiverAsyncTask extends AsyncTask<Receiver,Void,Void>{
        private ReceiverDAO receiverDAO;
        public InsertReceiverAsyncTask(ReceiverDAO receiverDAO){
            this.receiverDAO=receiverDAO;
        }
        @Override
        protected Void doInBackground(Receiver... receivers) {
            receiverDAO.inserReceiver(receivers[0]);
            return null;
        }
    }

    private static class UpdateReceiverAsyncTask extends AsyncTask<Receiver,Void,Void>{
        private ReceiverDAO receiverDAO;

        public UpdateReceiverAsyncTask(ReceiverDAO receiverDAO){
            this.receiverDAO = receiverDAO;
        }
        @Override
        protected Void doInBackground(Receiver... receivers) {
            receiverDAO.updateReceiver(receivers[0]);
            return null;
        }
    }

    private static  class DeleteReceiverAsyncTask extends  AsyncTask<Receiver,Void,Void>{
        private ReceiverDAO receiverDAO;

        public DeleteReceiverAsyncTask(ReceiverDAO receiverDAO){
            this.receiverDAO =receiverDAO;
        }
        @Override
        protected Void doInBackground(Receiver... receivers) {
             receiverDAO.deleteReceiver(receivers[0]);
            return null;
        }
    }


    private static class FindReceiverByUuid extends AsyncTask<UUID,Void,Receiver>{

        private ReceiverDAO receiverDAO;
        public FindReceiverByUuid (ReceiverDAO receiverDAO){
            this.receiverDAO = receiverDAO;
        }
        @Override
        protected Receiver doInBackground(UUID... uuids) {
            return receiverDAO.getReceiverByUuid(uuids[0]);
        }
    }


    private static class FindReceiverByIP extends AsyncTask<String,Void,Receiver>{
        private ReceiverDAO receiverDAO;
        public FindReceiverByIP(ReceiverDAO receiverDAO){
            this.receiverDAO = receiverDAO;
        }
        @Override
        protected Receiver doInBackground(String... strings) {
            receiverDAO.getReceiverByIP(strings[0]);
            return null;
        }
    }
    private static class GetAllReceivers extends AsyncTask<Void,Void,LiveData<List<Receiver>>>{

        private ReceiverDAO receiverDAO;
        public GetAllReceivers(ReceiverDAO receiverDAO){
            this.receiverDAO = receiverDAO;
        }
        @Override
        protected LiveData<List<Receiver>> doInBackground(Void... voids) {
            return receiverDAO.getAllReceiver();
        }
    }
}
