package com.mohammedfatih.mychatapp.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.mohammedfatih.mychatapp.Dao.MessagesDAO;
import com.mohammedfatih.mychatapp.models.Message;
import com.mohammedfatih.mychatapp.models.Receiver;
import com.mohammedfatih.mychatapp.services.Appdatabase;
import com.mohammedfatih.mychatapp.tools.Config;

import java.util.List;
import java.util.UUID;

public class MesssageRepository {
    private final  String TAG = "MessageReposotory";

    private MessagesDAO messagesDAO;
    private Receiver receiver ;
    private LiveData<List<Message>> getAllMessage;

    public MesssageRepository(Application app, Receiver receiver){
        this.receiver =receiver;
        messagesDAO  = Appdatabase.getInstance(app).messageDAO();
        getAllMessage = messagesDAO.getConeversationMessages(Config.me.getUuidSender(),receiver.getUuidReceiver());
    }

    public void insertMessage(Message message){
    new InsertMessageAsyncTask(messagesDAO).execute(message);
    }
    public void updateMessage(Message message){
    new UpdateMessageAsyncTask(messagesDAO).execute(message);
    }
    public void deleteMessage(Message message){
    new DeleteMessageAsyncTask( messagesDAO).execute(message);
    }
    public Message getLastMessages(UUID receiverUUid){
        Message message = null;
        try{
           message  = new LastMessage(messagesDAO).execute(receiverUUid).get();
        }catch (Exception e ){
            Log.e(TAG,"error getting last message : "+e.getMessage());
        }
        return message;
    }
    public LiveData<List<Message>> getAllMessages(){
        return getAllMessage;
    }


    private  static class InsertMessageAsyncTask extends AsyncTask<Message,Void,Void> {
        private MessagesDAO messagesDAO;
        public InsertMessageAsyncTask(MessagesDAO messagesDAO){
            this.messagesDAO = messagesDAO;
        }

        @Override
        protected Void doInBackground(Message... messages) {
            messagesDAO.insert(messages[0]);
            return null;
        }
    }
    private  static class UpdateMessageAsyncTask extends AsyncTask<Message,Void,Void> {
        private MessagesDAO messagesDAO;
        public UpdateMessageAsyncTask(MessagesDAO messagesDAO){
            this.messagesDAO = messagesDAO;
        }

        @Override
        protected Void doInBackground(Message... messages) {
            messagesDAO.update(messages[0]);
            return null;
        }
    }
    private static  class DeleteMessageAsyncTask extends AsyncTask<Message,Void,Void>{
        private MessagesDAO messagesDAO;
        public DeleteMessageAsyncTask(MessagesDAO messagesDAO){
            this.messagesDAO = messagesDAO;
        }

        @Override
        protected Void doInBackground(Message... messages) {
            messagesDAO.delete(messages[0]);
            return null;
        }
    }

    private static  class LastMessage extends AsyncTask<UUID,Void,Message>{
        private MessagesDAO messagesDAO;

        public LastMessage(MessagesDAO messagesDAO){
            this.messagesDAO = messagesDAO;
        }
        @Override
        protected Message doInBackground(UUID... uuids) {
            return messagesDAO.getlastMessage(Config.me.getUuidSender(), uuids[0]);
        }
    }
}
