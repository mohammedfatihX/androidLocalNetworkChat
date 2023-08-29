package com.mohammedfatih.mychatapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "receivers")
public class Receiver extends User implements Serializable {
    @NonNull
    @PrimaryKey
    private UUID uuidReceiver;
   // private static final long serialVersionUID = 6529685098267757691L;

    public Receiver(String username, String ip, String tag, @NonNull UUID uuidReceiver) {
        super(username, ip, tag);
        this.uuidReceiver = uuidReceiver;
    }

    public UUID getUuidReceiver() {
        return uuidReceiver;
    }

    public void setUuidReceiver(UUID uuidReciever) {
        this.uuidReceiver = uuidReciever;
    }


    @Override
    public String toString() {
        return "Receiver{" +
                "uuidReciever=" + uuidReceiver +
                '}';
    }
}
