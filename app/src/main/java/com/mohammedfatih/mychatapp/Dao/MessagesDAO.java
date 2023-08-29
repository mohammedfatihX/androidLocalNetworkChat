package com.mohammedfatih.mychatapp.Dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.mohammedfatih.mychatapp.models.Message;

import java.util.List;
import java.util.UUID;

@Dao
public interface MessagesDAO  {
    @Insert
    void insert(Message message);
    @Update
    void update(Message message);
    @Delete
    void delete(Message message);
    @Query("SELECT * FROM messages WHERE (messages.uuidReceiver = :uuidSender OR messages.uuidReceiver = :uuidReceiver) AND (messages.uuidSender = :uuidSender OR messages.uuidSender = :uuidReceiver) ORDER BY id DESC LIMIT 1 ")
    Message getlastMessage(UUID uuidSender ,UUID uuidReceiver);
    @Transaction
    @Query("SELECT * FROM messages WHERE (messages.uuidReceiver = :uuidSender OR messages.uuidReceiver = :uuidReceiver) AND (messages.uuidSender = :uuidSender OR messages.uuidSender = :uuidReceiver)")
    LiveData<List<Message>> getConeversationMessages(UUID uuidSender ,UUID uuidReceiver);

}
