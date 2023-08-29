package com.mohammedfatih.mychatapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohammedfatih.mychatapp.R;
import com.mohammedfatih.mychatapp.models.Message;
import com.mohammedfatih.mychatapp.models.Receiver;
import com.mohammedfatih.mychatapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class ChatBublesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private User user;
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;
    private List<Message> messages = new ArrayList<>();
    private Receiver receiver;

    public ChatBublesAdapter(Receiver receiver){
        this.receiver = receiver;
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
    public void setListMessages(List<Message> newMessages){
        messages = newMessages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("chatbuble","this the receiver  "+ receiver);
        Log.e("chatbuble","this messages  :  "+messages);
        if ((messages.get(position)).getUuidSender().equals(receiver.getUuidReceiver())){
            Log.e("chatbuble","this message is received");

            return VIEW_TYPE_RECEIVED;
        }else {
            Log.e("chatbuble","this message is ME");

            return VIEW_TYPE_SENT ;
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
