package com.mohqmmedfatih.mychatapp.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.mohqmmedfatih.mychatapp.R;
import com.mohqmmedfatih.mychatapp.interfaces.ItemListener;
import com.mohqmmedfatih.mychatapp.interfaces.UpdateReceiverListener;
import com.mohqmmedfatih.mychatapp.models.MenuOption;
import com.mohqmmedfatih.mychatapp.models.Receiver;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private Context context;
    private final String TAG = "UserAdapter";
     ItemListener itemListener;
     UpdateReceiverListener updateReceiverListener;
     private List<Receiver> receivers = new ArrayList<>();
    public UserAdapter(Context context, ItemListener itemListener,UpdateReceiverListener updateReceiverListener){
        this.updateReceiverListener = updateReceiverListener;
        this.itemListener = itemListener;
        this.context = context;

    }
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_ui,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        final Receiver user = receivers.get(position);

        holder.recipientImage.setImageDrawable(AvatarGenerator.Companion.avatarImage(context,120, AvatarConstants.Companion.getCIRCLE(),user.getUsername() ));
        holder.recipientUsername.setText(user.getUsername());

       if (!receivers.isEmpty()){
           holder.lastMessageDate.setText("");
           holder.lastMessage.setText("write a message");

       }else {
           holder.lastMessageDate.setText("");
           holder.lastMessage.setText("");
       }


    }

    @Override
    public int getItemCount() {
        return receivers.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setReceivers(List<Receiver> receivers){
        this.receivers =receivers;
        notifyDataSetChanged();
    }

    private void showContextMenu (View v,Receiver tempreceiver){
        PopupMenu popupMenu = new PopupMenu(context,v);
        popupMenu.inflate(R.menu.menu_user_option);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            final MenuOption option = checkTheResources(menuItem);
            switch (option){
                case update:
                    Log.e(TAG,"the option menu is created and the option update is the choosen one");
                     updateReceiverListener.onReceiverUpdated(tempreceiver);
                    return true;

                case remove:
                    Log.e(TAG,"the option menu is created and the option removed is the choosen one");
                    updateReceiverListener.onRecevierRemoved(tempreceiver);

                    return true;


                default :

                    Log.e(TAG,"the option menu is created and but selected option is default");
                    return false;
            }

        });
        popupMenu.show();
    }
    public  class  UserHolder extends RecyclerView.ViewHolder {
        public TextView recipientUsername;
        public TextView lastMessage;
        public TextView lastMessageDate;
        public ImageView recipientImage;
        public UserHolder(@NonNull View userUI) {
            super(userUI);

            recipientImage = userUI.findViewById(R.id.imageprofile);
            recipientUsername = userUI.findViewById(R.id.recipientUsername);
            lastMessage = userUI.findViewById(R.id.lastMessageRecipient);
            lastMessageDate = userUI.findViewById(R.id.messageDate);

            itemView.setOnClickListener(view -> {
                itemListener.onclickListener(receivers.get(getAdapterPosition()));
            });
            itemView.setOnLongClickListener(event ->{
                showContextMenu(userUI,receivers.get(getAdapterPosition()));
                return true;
            });
        }


    }

    private MenuOption checkTheResources(MenuItem menuItem){
        if (menuItem.getItemId() == MenuOption.update.getResources()){
            return MenuOption.update;
        }else if (menuItem.getItemId() == MenuOption.remove.getResources()){
            return  MenuOption.remove;
        }else {
            return MenuOption.none;
        }
    }
}
