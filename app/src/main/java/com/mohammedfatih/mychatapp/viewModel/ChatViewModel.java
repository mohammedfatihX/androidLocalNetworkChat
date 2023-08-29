package com.mohammedfatih.mychatapp.viewModel;

import android.app.Application;


import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mohammedfatih.mychatapp.models.Message;
import com.mohammedfatih.mychatapp.models.Receiver;
import com.mohammedfatih.mychatapp.repository.MesssageRepository;

import java.util.List;
import java.util.UUID;

public class ChatViewModel extends AndroidViewModel {
    private MesssageRepository messsageRepository;
    private Receiver receiver;
    private LiveData<List<Message>> allMessagesByUser;
    public ChatViewModel(Application application, Receiver receiver) {
        super(application);
        this.receiver = receiver;
        messsageRepository = new MesssageRepository(application, receiver);
        allMessagesByUser = messsageRepository.getAllMessages();
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
    public Message getlastMessage(UUID receiverUUID){
        return messsageRepository.getLastMessages(receiverUUID);
    }
    public void insert(Message message){
        messsageRepository.insertMessage(message);
    }
    public void update(Message message){
        messsageRepository.updateMessage(message);
    }
    public void delete(Message message){
        messsageRepository.deleteMessage(message);
    }

    public  LiveData<List<Message>> getAllMessagesByUser(){
        return allMessagesByUser;
    }
}
