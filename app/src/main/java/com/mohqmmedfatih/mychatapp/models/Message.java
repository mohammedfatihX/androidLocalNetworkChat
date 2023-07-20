package com.mohqmmedfatih.mychatapp.models;

import androidx.annotation.NonNull;

import com.mohqmmedfatih.mychatapp.tools.Tools;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;


    private String messageText;
    private MessageType type;
    private String date;
    private User sender;


    public Message(MessageType type,String messageText,User sender){
        this.type = type;
        this.messageText = messageText;
        this.date = Tools.getDate();
        this.sender = sender;

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

    @Override
    public String toString() {
        return "Message{" +
                "messageText='" + messageText + '\'' +
                ", type=" + type +
                '}';
    }
}
