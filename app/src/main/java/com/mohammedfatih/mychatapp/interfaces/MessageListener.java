package com.mohammedfatih.mychatapp.interfaces;

import com.mohammedfatih.mychatapp.models.Message;

public interface MessageListener {
    void onMessageSendingSuccessfully(Message message);
    void onMessageSendingFailed();
}
