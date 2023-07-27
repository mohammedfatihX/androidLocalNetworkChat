package com.mohqmmedfatih.mychatapp.interfaces;

import com.mohqmmedfatih.mychatapp.models.Receiver;

public interface UpdateReceiverListener {
    void onReceiverUpdated(Receiver receiver);
    void onRecevierRemoved(Receiver receiver);

}
