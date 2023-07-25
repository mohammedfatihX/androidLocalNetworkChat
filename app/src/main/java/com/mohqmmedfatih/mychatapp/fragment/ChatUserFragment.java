package com.mohqmmedfatih.mychatapp.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.mohqmmedfatih.mychatapp.R;
import com.mohqmmedfatih.mychatapp.adapter.ChatBublesAdapter;
import com.mohqmmedfatih.mychatapp.interfaces.UpdateMessageFragmentListener;
import com.mohqmmedfatih.mychatapp.models.Message;
import com.mohqmmedfatih.mychatapp.models.MessageType;
import com.mohqmmedfatih.mychatapp.models.Receiver;
import com.mohqmmedfatih.mychatapp.models.Sender;
import com.mohqmmedfatih.mychatapp.models.User;
import com.mohqmmedfatih.mychatapp.services.ClientConnectionHandler;
import com.mohqmmedfatih.mychatapp.tools.Config;
import com.mohqmmedfatih.mychatapp.viewModel.ChatViewModel;

import java.util.List;
import java.util.Objects;


public class ChatUserFragment extends Fragment implements UpdateMessageFragmentListener {
    private FragmentManager fragmentManager;
    private ChatViewModel chatViewModel;
    private final String TAG = "ChatUserFragment";
    private RecyclerView chatUserRecyclerView;
    private ChatBublesAdapter chatAdapter;
    private ImageButton back;
    private ButtonVisibilityListener buttonVisibilityListener;

    private ImageView userimage;
    private TextView recipientUsername;
    private Button setting_btn;
    private EditText message;
    private Receiver receiver;
    private ImageButton send;
    public ChatUserFragment() {
        // Required empty public constructor
    }

    public static ChatUserFragment newInstance(User recipient) {
        ChatUserFragment fragment = new ChatUserFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipient", recipient);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ButtonVisibilityListener) {
            buttonVisibilityListener = (ButtonVisibilityListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ButtonVisibilityListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_user, container, false);
        buttonVisibilityListener.onButtonVisibilityChanged(false);

        Bundle args = getArguments();
        if (args != null) {
            receiver = (Receiver) args.getSerializable("recipient");
            Log.e(TAG,"the recipient is  : "+receiver);
        }
        chatUserRecyclerView = rootView.findViewById(R.id.messagesRecycleView);
        back = rootView.findViewById(R.id.beckArrow);
        userimage = rootView.findViewById(R.id.imageRecipient);
        recipientUsername = rootView.findViewById(R.id.recipientUsernamet);
        setting_btn = rootView.findViewById(R.id.setting_btn);
        message = rootView.findViewById(R.id.inputMessage);
        send = rootView.findViewById(R.id.sendmessage);


        chatAdapter = new ChatBublesAdapter(receiver);
        chatUserRecyclerView.setAdapter(chatAdapter);
        chatUserRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fragmentManager = requireActivity().getSupportFragmentManager();
        recipientUsername.setText(receiver.getUsername());
        userimage.setImageDrawable(AvatarGenerator.Companion.avatarImage(getContext(),120, AvatarConstants.Companion.getCIRCLE(), receiver.getUsername()));

        send.setOnClickListener(event ->{
            Log.e(TAG,"a button is clicked");
            Message tempMesssage = new Message(MessageType.MESSAGE,message.getText().toString(),Config.me,receiver.getUuidReceiver());
            Log.e(TAG," the message that will be send is  : "+tempMesssage);
            sendMessage(receiver,tempMesssage);
        });
        back.setOnClickListener(event -> {
            Log.e(TAG,"a button is clicked");

            fragmentManager.popBackStack();
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //chatViewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(ChatViewModel.class);
        chatViewModel = new ChatViewModel(requireActivity().getApplication(),receiver);
        //chatViewModel.setRecipient(recipient);
        chatViewModel.getAllMessagesByUser().observe(getActivity(), messages -> {
            chatAdapter.setListMessages(messages);
            Log.e(TAG," chat model view detect a new message update in database");
        });
    }

    private void sendMessage(User recipeint, Message message){
        chatViewModel.insert(message);
        new Thread(new ClientConnectionHandler(recipeint,message)).start();
//        WHOLECHAT.get(recipeint).add(message);

        // chatAdapter.notifyItemInserted(Config.WHOLECHAT.get(recipient).size());
        this.message.setText(null);
    }

    @Override
    public void onReceivedNewMessage(Message message) {
        Log.e(TAG, "new Message is recieved: " + message);
        Message tempMessage =new Message(MessageType.MESSAGE,message.getMessageText(),(Sender) message.getSender(),Config.me.getUuidSender());
        tempMessage.setDate(message.getDate());
        chatViewModel.insert(tempMessage);
//        chatAdapter.notifyItemInserted(Config.WHOLECHAT.get(recipient).size());
    }


    public interface ButtonVisibilityListener {
        void onButtonVisibilityChanged(boolean isVisible);
    }
}