package com.mohqmmedfatih.mychatapp.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.mohqmmedfatih.mychatapp.R;
import com.mohqmmedfatih.mychatapp.interfaces.ItemListener;
import com.mohqmmedfatih.mychatapp.models.Receiver;
import com.mohqmmedfatih.mychatapp.models.User;
import com.mohqmmedfatih.mychatapp.tools.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private Context context;
     ItemListener itemListener;
     private List<Receiver> receivers = new ArrayList<>();
    public UserAdapter(Context context, ItemListener itemListener){
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

    public  class  UserHolder extends RecyclerView.ViewHolder{
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

        }
    }
}
