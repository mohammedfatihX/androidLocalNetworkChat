package com.mohammedfatih.mychatapp.interfaces;

import com.mohammedfatih.mychatapp.models.Receiver;

public interface UpdateReceiverListener {
    void onReceiverUpdated(Receiver receiver);
    void onRecevierRemoved(Receiver receiver);

}
