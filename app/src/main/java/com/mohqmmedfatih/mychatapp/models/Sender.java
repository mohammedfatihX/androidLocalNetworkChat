package com.mohqmmedfatih.mychatapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "senders")
public class Sender extends User implements Serializable {

    @NonNull
    @PrimaryKey
    private UUID uuidSender;

    public Sender(String username, String ip, String tag ,@NonNull UUID uuidSender) {
        super(username, ip, tag);
        this.uuidSender = uuidSender;
    }

    public UUID getUuidSender() {
        return uuidSender;
    }

    public void setUuidSender(UUID uuidSender) {
        this.uuidSender = uuidSender;
    }

    @Override
    public String toString() {
        return super.toString()+"\n"+
                "Sender{" +
                "uuidSender=" + uuidSender +
                '}';
    }
}
