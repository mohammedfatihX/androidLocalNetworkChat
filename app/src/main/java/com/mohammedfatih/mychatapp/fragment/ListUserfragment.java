package com.mohammedfatih.mychatapp.fragment;


import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mohammedfatih.mychatapp.R;
import com.mohammedfatih.mychatapp.activities.ChattingActivity;
import com.mohammedfatih.mychatapp.adapter.UserAdapter;
import com.mohammedfatih.mychatapp.interfaces.MessageListener;
import com.mohammedfatih.mychatapp.interfaces.SnakebarMessage;
import com.mohammedfatih.mychatapp.interfaces.UpdateMessageFragmentListener;
import com.mohammedfatih.mychatapp.interfaces.UpdateReceiverListener;
import com.mohammedfatih.mychatapp.interfaces.UpdateUserFragmetListener;
import com.mohammedfatih.mychatapp.models.Message;
import com.mohammedfatih.mychatapp.models.MessageType;
import com.mohammedfatih.mychatapp.models.Receiver;
import com.mohammedfatih.mychatapp.models.Sender;
import com.mohammedfatih.mychatapp.models.User;
import com.mohammedfatih.mychatapp.services.ClientConnectionHandler;
import com.mohammedfatih.mychatapp.services.ServerConnectionsHandler;
import com.mohammedfatih.mychatapp.tools.Config;
import com.mohammedfatih.mychatapp.viewModel.ChatViewModel;
import com.mohammedfatih.mychatapp.viewModel.ReceiverViewModel;

import java.io.IOException;
import java.net.ServerSocket;

public class ListUserfragment extends Fragment implements UpdateUserFragmetListener  ,UpdateReceiverListener , MessageListener , SnakebarMessage {

    final static String TAG = "ListUserFragment";
    public static ReceiverViewModel receiverViewModel;
    private ChatViewModel chatViewModel;
    private ChattingActivity chattingActivity;

    RecyclerView recyclerViewListUser;
    Button myinfo;

    Button addingUser;
    UserAdapter userAdapter;
    public static ChatUserFragment chatUserFragment;
   // public  static  EditingReceiver tt ;

    public ListUserfragment() {
        // Required empty public constructor
    }





    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        startServerSocket2listing();
        View root =inflater.inflate(R.layout.fragment_list_userfragment, container, false);
        this.chattingActivity  = (ChattingActivity) getActivity();
        myinfo = root.findViewById(R.id.info);
        addingUser = root.findViewById(R.id.addUsers_btn);
        recyclerViewListUser = root.findViewById(R.id.userLists);
        userAdapter = new UserAdapter(getContext(), user -> {
            chatUserFragment = ChatUserFragment.newInstance(user);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().addToBackStack(null).
            replace(R.id.fragmentplace,chatUserFragment).
            commit();
        },this);
        myinfo.setOnClickListener(event ->{
            Snackbar.make(root,"your ip is : "+Config.me.getIp(),Snackbar.LENGTH_LONG).show();
        });

        addingUser.setOnClickListener(event ->{
            addReceiver();
        });

        recyclerViewListUser.setAdapter(userAdapter);
        recyclerViewListUser.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        receiverViewModel = new ReceiverViewModel(requireActivity().getApplication());
        updateUI();
//        receiverViewModel.getAllUsers().observe((LifecycleOwner) getActivity(), users -> {
//            Log.e(TAG," User model view detect a new User update in database");
//
//            userAdapter.setReceivers(users);
//        });
    }

    private void startServerSocket2listing(){
        try{
            ServerConnectionsHandler connectionsHandler = new ServerConnectionsHandler() {
                @Override
                public void newMessageIsReceived(Message message) {

                    messageFiltering(message);
                }
            };
            connectionsHandler.startListening(new ServerSocket(Config.MAINPORT));
            Log.e(TAG,"we have create serverSocket without Error ");

        }catch (Exception e){
            Log.e(TAG,"we have some error in the creation of the serverSocket and the error is :  "+e.getMessage());
            e.printStackTrace();
        }
    }



    public  void welcomingUsers(User user){
        Message message = new Message(MessageType.NEWCONNECTION,null,Config.me,null);
        new Thread(new ClientConnectionHandler(user,message,this)).start();

    }
    public  void welcomingUsers(String ip){
        Message message = new Message(MessageType.NEWCONNECTION,null,Config.me,null);
        new Thread(new ClientConnectionHandler(ip,message,this)).start();
        Snackbar.make(getActivity().findViewById(R.id.rooView),"a user is add ",Snackbar.LENGTH_LONG).show();

    }
    private void updateUI(){
        receiverViewModel.getAllUsers().observe((LifecycleOwner) getActivity(), users -> {
            Log.e(TAG," User model view detect a new User update in database");

            userAdapter.setReceivers(users);
        });
    }
    private  void messageFiltering( Message message){
        switch(message.getMessageType()){
            case MESSAGE:
                // Toast.makeText(ChattingActivity.this,"we have recived a message",Toast.LENGTH_SHORT);
                if (receiverViewModel.getById(message.getUuidSender()) != null){

                      chatUserFragment.onReceivedNewMessage(message);
                      chattingActivity.snakeBarMessage("you have a new message from "+message.getSender().getUsername());

                }else {
                    welcomingUsers(message.getSender());
                }

                break;

            case NEWCONNECTION:
                // Toast.makeText(ChattingActivity.this,"we have recived a user",Toast.LENGTH_SHORT);

                Log.e(TAG,"the message recieved is  : "+message);
                if(receiverViewModel.getById(message.getUuidSender()) != null){
                    Log.e(TAG,"the user is exist in the databese");


                }else {
                    Log.e(TAG,"then new user is not exist in the databese");

                    welcomingUsers(message.getSender());
                    onReceivedNewUser(message);
                }



                break;


            case CLOSECONNCTION:


                break;
        }
    }


    @Override
    public void onReceiverUpdated(Receiver receiver) {
//            updateDialogBuilder(receiver);
        editIpaddressforReceiver(receiver);
    }

    @Override
    public void onRecevierRemoved(Receiver receiver) {
        receiverViewModel.delete(receiver);
        Snackbar.make(getActivity().findViewById(R.id.rooView),"the user "+receiver.getUsername()+" is Removed ",Snackbar.LENGTH_LONG).show();

    }

    @Override
    public  void onReceivedNewUser(Message message) {
        Message tempMessage = message;
        if (tempMessage.getType() == MessageType.NEWCONNECTION){
            Log.e(TAG, "new user Position");
            receiverViewModel.insert(new Receiver(tempMessage.getSender().getUsername(),tempMessage.getSender().getIp(),null,((Sender)tempMessage.getSender()).getUuidSender()));
            Snackbar.make(getActivity().findViewById(R.id.rooView),"the user "+message.getSender().getUsername()+" is Added ",Snackbar.LENGTH_LONG).show();

        }
    }




    public  boolean checkIpAddressIsConnected(String ip)  {
            Process p1 = null;
            int returnVal = 2;

            try {
                p1 = Runtime.getRuntime().exec("ping -c 1 "+ip);
                returnVal = p1.waitFor();
            } catch (IOException | InterruptedException e) {
                Log.e(TAG,"the ip you have check is invalid or incorrect more error  info " + e.getMessage());

            }

            return (returnVal==0);
        }


    private void editIpaddressforReceiver(Receiver receiver){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder
            .setView(R.layout.dialog_edit_user)
                .setPositiveButton("Update", (dialogInterface, i) -> {
                    EditText editText = ((AlertDialog) dialogInterface).findViewById(R.id.ipRecipient);
                    String userInput = editText.getText().toString().trim();
                    editText.setText(receiver.getIp());
                        if (checkIpAddressIsConnected(userInput)){
                                receiver.setIp(userInput);
                                receiverViewModel.update(receiver);
                            Snackbar.make(getActivity().findViewById(R.id.rooView),"the user "+receiver.getUsername()+" is updated ",Snackbar.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(requireActivity(), "the ip you have entered is not valid or not connected", Toast.LENGTH_SHORT).show();
                        }

                })
                .setNegativeButton("cancel", (dialogInterface, i) -> dialogInterface.dismiss());



            AlertDialog alertDialog = builder.create();

            alertDialog.show();


    }

    private void addReceiver(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder
                .setView(R.layout.dialog_edit_user)
                .setPositiveButton("Add", (dialogInterface, i) -> {
                    EditText editText = ((AlertDialog) dialogInterface).findViewById(R.id.ipRecipient);
                    String userInput = editText.getText().toString().trim();
                    if (checkIpAddressIsConnected(userInput)){
                        welcomingUsers(userInput);

                    }else {
                        Toast.makeText(requireActivity(), "the ip you have entered is not valid or not connected", Toast.LENGTH_SHORT).show();
                    }

                })
                .setNegativeButton("cancel", (dialogInterface, i) -> dialogInterface.dismiss());



        AlertDialog alertDialog = builder.create();

        alertDialog.show();


    }

    @Override
    public void onMessageSendingSuccessfully(Message message) {

    }

    @Override
    public void onMessageSendingFailed() {
        getActivity().runOnUiThread(()->{
            Toast.makeText(getContext(), "this user is not connected", Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public void snakeBarMessage(String message) {

    }
}