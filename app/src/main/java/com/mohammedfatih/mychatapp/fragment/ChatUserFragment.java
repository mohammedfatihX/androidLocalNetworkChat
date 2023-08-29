package com.mohammedfatih.mychatapp.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.mohammedfatih.mychatapp.R;
import com.mohammedfatih.mychatapp.adapter.ChatBublesAdapter;
import com.mohammedfatih.mychatapp.interfaces.MessageListener;
import com.mohammedfatih.mychatapp.interfaces.UpdateMessageFragmentListener;
import com.mohammedfatih.mychatapp.models.Message;
import com.mohammedfatih.mychatapp.models.MessageType;
import com.mohammedfatih.mychatapp.models.Receiver;
import com.mohammedfatih.mychatapp.models.Sender;
import com.mohammedfatih.mychatapp.models.User;
import com.mohammedfatih.mychatapp.services.ClientConnectionHandler;
import com.mohammedfatih.mychatapp.tools.Config;
import com.mohammedfatih.mychatapp.viewModel.ChatViewModel;

import java.util.List;


public class ChatUserFragment extends Fragment implements UpdateMessageFragmentListener , MessageListener {
    private FragmentManager fragmentManager;
    private ChatViewModel chatViewModel;
    private final String TAG = "ChatUserFragment";
    private RecyclerView chatUserRecyclerView;
    private ChatBublesAdapter chatAdapter;
    private ImageButton back;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_user, container, false);

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
            requireActivity().getSupportFragmentManager().popBackStack();
            //fragmentManager.popBackStack();
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
            updateThePosition(messages);
            Log.e(TAG," chat model view detect a new message update in database");
        });
    }

    private void sendMessage(Receiver recipeint, Message message){
        new Thread(new ClientConnectionHandler(recipeint,message,this)).start();
        this.message.setText(null);
    }
    private void updateThePosition(List<Message> messages){
        int messagesize = messages.size()-1;

        if (messagesize > 0){
            chatUserRecyclerView.scrollToPosition(messagesize);
        }
    }
    @Override
    public void onReceivedNewMessage(Message message) {
        Log.e(TAG, "new Message is recieved: " + message);
        Message tempMessage =new Message(MessageType.MESSAGE,message.getMessageText(),(Sender) message.getSender(),Config.me.getUuidSender());
        tempMessage.setDate(message.getDate());
        chatViewModel.insert(tempMessage);

    }

    @Override
    public void onMessageSendingSuccessfully(Message message) {
        chatViewModel.insert(message);

    }

    @Override
    public void onMessageSendingFailed() {
        requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "message is not sent please check ip address of recipient", Toast.LENGTH_SHORT).show());    }

}