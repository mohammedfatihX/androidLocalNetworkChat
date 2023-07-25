package com.mohqmmedfatih.mychatapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mohqmmedfatih.mychatapp.models.Receiver;
import com.mohqmmedfatih.mychatapp.models.User;
import com.mohqmmedfatih.mychatapp.repository.ReceiverRepository;

import java.util.List;
import java.util.UUID;

public class ReceiverViewModel extends AndroidViewModel {
    private ReceiverRepository receiverRepository;
    private LiveData<List<Receiver>> getallReceivers;
    public ReceiverViewModel(@NonNull Application application) {
        super(application);
        receiverRepository = new ReceiverRepository(application);
        getallReceivers = receiverRepository.getallReceivers();
    }

    public void insert(Receiver receiver){
        receiverRepository.insertReceiver(receiver);
    }
    public void update(Receiver receiver){
        receiverRepository.updateReceiver(receiver);
    }
    public void delete(Receiver receiver) {
        receiverRepository.deleteReceiver(receiver);
    }
    public User getById(UUID uuidReceiver) {
       return receiverRepository.getReceiverByUuid(uuidReceiver);
    }
    public LiveData<List<Receiver>> getAllUsers(){
        return getallReceivers ;
    }



}
