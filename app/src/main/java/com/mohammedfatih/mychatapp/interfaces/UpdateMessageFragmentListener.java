package com.mohammedfatih.mychatapp.interfaces;

import com.mohammedfatih.mychatapp.models.Message;

public interface UpdateMessageFragmentListener {
    public void onReceivedNewMessage(Message message);
}
