package com.mohqmmedfatih.mychatapp.models;

import com.mohqmmedfatih.mychatapp.R;

public enum MenuOption {
    update(R.id.updateReceiverMenu),
    remove(R.id.removeReceiverMenu),
    none(0);

    private int resources;
    MenuOption(int resources){
        this.resources = resources;
    }

    public int getResources() {
        return resources;
    }
}
