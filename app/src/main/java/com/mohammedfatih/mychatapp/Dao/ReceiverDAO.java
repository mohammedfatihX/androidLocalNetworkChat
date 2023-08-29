package com.mohammedfatih.mychatapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mohammedfatih.mychatapp.models.Receiver;

import java.util.List;
import java.util.UUID;
@Dao
public interface ReceiverDAO {

    @Insert
    void inserReceiver(Receiver receiver);

    @Update
    void updateReceiver(Receiver  receiver);

    @Delete
    void deleteReceiver(Receiver receiver);

    @Query("SELECT * FROM receivers WHERE uuidReceiver  = :uuidReceiver")
     Receiver getReceiverByUuid(UUID uuidReceiver);

    @Query("SELECT * FROM receivers WHERE ip = :ipReceiver")
    Receiver getReceiverByIP(String ipReceiver);

   /* @Query("SELECT * FROM messages WHERE (messages.uuidReceiver = :uuidSender OR messages.uuidReceiver = :uuidReceiver) AND (messages.uuidSender = :uuidSender OR messages.uuidSender = :uuidReceiver) ORDER BY id DESC LIMIT 1 ")
    Message getlastMessage(UUID uuidSender ,UUID uuidReceiver);*/
    @Query("SELECT * FROM receivers")
    LiveData<List<Receiver>> getAllReceiver();


}
