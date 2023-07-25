package com.mohqmmedfatih.mychatapp.models;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.mohqmmedfatih.mychatapp.tools.Config;
import com.mohqmmedfatih.mychatapp.tools.Tools;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "messages"/*,foreignKeys = {
        @ForeignKey(entity = Sender.class,
        parentColumns = "uuidSender",
        childColumns = "uuidSender",
        onDelete = ForeignKey.CASCADE),

        @ForeignKey(entity = Receiver.class,
        parentColumns = "uuidReceiver",
        childColumns = "uuidReceiver",
        onDelete = ForeignKey.CASCADE)}*/)
public class Message implements Serializable {
    @Ignore
    private static final long serialVersionUID = 6529685098267757690L;
    private String messageText;
    @PrimaryKey(autoGenerate = true)
    private int id;
    private MessageType type;
    private String date;
    @Ignore
    private User sender;

    private UUID uuidSender;

    private UUID uuidReceiver;
    /*    @Relation(parentColumn = "userUuid", entityColumn = "uuid")
        @Embedded
        private User sender;
        @Embedded
        private User receiver;*/

    public Message(){
    }




    public Message(MessageType type,String messageText,Sender sender,UUID uuidReceiver){
        this.type = type;
        this.messageText = messageText;
        this.date = Tools.getDate();
        this.sender =sender;
        uuidSender = sender.getUuidSender();
        this.uuidReceiver = uuidReceiver;
    }



    public UUID getUuidSender() {
        return uuidSender;
    }

    public void setUuidSender(UUID uuidSender) {
        this.uuidSender = uuidSender;
    }

    public UUID getUuidReceiver() {
        return uuidReceiver;
    }

    public void setUuidReceiver(UUID uuidReceiver) {
        this.uuidReceiver = uuidReceiver;
    }


    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public void setUser(User user){
        this.sender = user;
    }
    public User getSender(){
        return sender;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getMessageText(){
        return this.messageText;
    }
    public MessageType getMessageType(){
        return this.type;
    }
    public String getDate(){
        return this.date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageText='" + messageText + '\'' +
                ", id=" + id +
                ", type=" + type +
                ", date='" + date + '\'' +
                ", sender=" + sender +
                ", uuidSender=" + uuidSender +
                ", uuidReceiver=" + uuidReceiver +
                '}';
    }
}
