package com.mohqmmedfatih.mychatapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mohqmmedfatih.mychatapp.models.Sender;

import java.util.List;

@Dao
public interface SenderDAO {

    @Insert
    void insertSender(Sender sender);

    @Update
    void updateSender(Sender sender);

    @Delete
    void deleteSender(Sender sender);

    @Query("SELECT * FROM senders WHERE tag = :tag ;")
    Sender getSenderByTag(String tag);

    @Query("SELECT * FROM senders")
    LiveData<List<Sender>> getAllSenders();
}
