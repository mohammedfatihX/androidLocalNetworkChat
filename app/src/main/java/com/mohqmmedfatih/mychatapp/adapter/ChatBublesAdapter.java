package com.mohqmmedfatih.mychatapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohqmmedfatih.mychatapp.R;
import com.mohqmmedfatih.mychatapp.models.Message;
import com.mohqmmedfatih.mychatapp.models.User;
import com.mohqmmedfatih.mychatapp.tools.Config;

import java.util.ArrayList;

public class ChatBublesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private User user;
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;
    private ArrayList<Message> messages;

    public ChatBublesAdapter(User user){
        messages = Config.WHOLECHAT.get(user);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_SENT) {
            View view = inflater.inflate(R.layout.sender_buble_chat, parent, false);
            return new SenderChatholder(view);
        } else {
            View view = inflater.inflate(R.layout.reciever_buble_chat, parent, false);
            return new RecieverChatholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      if (messages.isEmpty() || messages == null){
          return;
      }
        if (holder instanceof SenderChatholder) {
            ((SenderChatholder) holder).message.setText(messages.get(position).getMessageText());
        } else if (holder instanceof RecieverChatholder) {
            ((RecieverChatholder) holder).message.setText(messages.get(position).getMessageText());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (messages.get(position).getSender() == Config.me){
            return VIEW_TYPE_SENT;
        }else {
            return VIEW_TYPE_RECEIVED;
        }
    }


    public static class SenderChatholder extends RecyclerView.ViewHolder {
        TextView message;
        public SenderChatholder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.messagetext);
        }
    }
    public static class RecieverChatholder extends RecyclerView.ViewHolder {
        TextView message;
        public RecieverChatholder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.messagetext);
        }
    }
}
