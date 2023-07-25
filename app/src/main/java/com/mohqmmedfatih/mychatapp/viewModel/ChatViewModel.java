package com.mohqmmedfatih.mychatapp.viewModel;

import android.app.Application;


import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mohqmmedfatih.mychatapp.models.Message;
import com.mohqmmedfatih.mychatapp.models.Receiver;
import com.mohqmmedfatih.mychatapp.models.User;
import com.mohqmmedfatih.mychatapp.repository.MesssageRepository;

import java.util.List;

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
