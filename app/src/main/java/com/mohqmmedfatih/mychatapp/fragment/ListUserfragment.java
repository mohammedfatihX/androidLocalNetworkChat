package com.mohqmmedfatih.mychatapp.fragment;


import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.mohqmmedfatih.mychatapp.Dialog.AddingReceiverDialogFragment;
import com.mohqmmedfatih.mychatapp.Dialog.EditingReceiver;
import com.mohqmmedfatih.mychatapp.R;
import com.mohqmmedfatih.mychatapp.adapter.UserAdapter;
import com.mohqmmedfatih.mychatapp.interfaces.UpdateReceiverListener;
import com.mohqmmedfatih.mychatapp.interfaces.UpdateUserFragmetListener;
import com.mohqmmedfatih.mychatapp.models.Message;
import com.mohqmmedfatih.mychatapp.models.MessageType;
import com.mohqmmedfatih.mychatapp.models.Receiver;
import com.mohqmmedfatih.mychatapp.models.Sender;
import com.mohqmmedfatih.mychatapp.models.User;
import com.mohqmmedfatih.mychatapp.services.ClientConnectionHandler;
import com.mohqmmedfatih.mychatapp.services.ServerConnectionsHandler;
import com.mohqmmedfatih.mychatapp.tools.Config;
import com.mohqmmedfatih.mychatapp.viewModel.ReceiverViewModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

public class ListUserfragment extends Fragment implements UpdateUserFragmetListener ,UpdateReceiverListener{

    final static String TAG = "ListUserfragment";
    public static ReceiverViewModel receiverViewModel;
    RecyclerView recyclerViewListUser;
    ImageView myImage;
    private ServerSocket serverSocket;

    Button setting;
    UserAdapter userAdapter;
    public static ChatUserFragment chatUserFragment;
   // public  static  EditingReceiver tt ;


    public ListUserfragment() {
        // Required empty public constructor
    }




    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //startServerSocketListening();
        startServerSocket2listing();
        View root =inflater.inflate(R.layout.fragment_list_userfragment, container, false);
        // Inflate the layout for this fragment
        myImage = root.findViewById(R.id.myimage);
        myImage.setImageDrawable(AvatarGenerator.Companion.avatarImage(getContext(),120, AvatarConstants.Companion.getCIRCLE(), "Config.me.getUsername()"));
        setting = root.findViewById(R.id.setting_btn);
        recyclerViewListUser = root.findViewById(R.id.userLists);
        userAdapter = new UserAdapter(getContext(), user -> {


            chatUserFragment = ChatUserFragment.newInstance(user);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
           fragmentManager.beginTransaction().
            replace(R.id.fragmentplace,chatUserFragment).
            commit();
        },this);


        recyclerViewListUser.setAdapter(userAdapter);
        recyclerViewListUser.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }

    @Override
    public  void onReceivedNewUser(Message message) {
        Message tempMessage = message;
        if (message.getType() == MessageType.NEWCONNECTION){
            Log.e(TAG, "new user Position");
            receiverViewModel.insert(new Receiver(tempMessage.getSender().getUsername(),tempMessage.getSender().getIp(),null,((Sender)tempMessage.getSender()).getUuidSender()));

        }

//        userAdapter.notifyItemInserted(WHOLECHAT.getLastindex());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        receiverViewModel = new ReceiverViewModel(requireActivity().getApplication());

       // userViewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(UserViewModel.class);
        receiverViewModel.getAllUsers().observe((LifecycleOwner) getActivity(), users -> {
            Log.e(TAG," User model view detect a new User update in database");
          /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                users.forEach(suer -> Log.e(TAG,"list sened to adapter => "+suer));
            }*/

            userAdapter.setReceivers(users);
        });
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



    public static void welcomingUsers(User user){
        Message message = new Message(MessageType.NEWCONNECTION,null,Config.me,null);
        new Thread(new ClientConnectionHandler(user,message)).start();

    }
    public static void welcomingUsers(String ip){
        Message message = new Message(MessageType.NEWCONNECTION,null,Config.me,null);
        new Thread(new ClientConnectionHandler(ip,message)).start();

    }

    private  void messageFiltering( Message message){
        switch(message.getMessageType()){
            case MESSAGE:
                // Toast.makeText(ChattingActivity.this,"we have recived a message",Toast.LENGTH_SHORT);
                if (receiverViewModel.getById(message.getUuidSender()) != null){
                        chatUserFragment.onReceivedNewMessage(message);
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
                    welcomingUsers(message.getSender());
                    onReceivedNewUser(message);
                }



                break;


            case CLOSECONNCTION:


                break;
        }
    }

//    private void updateDialogBuilder(Receiver receiver){
//         tt = new EditingReceiver() {
//            @Override
//            public void ipIsCorrect(String Ip) {
//                receiver.setIp(Ip);
//                 receiverViewModel.update(receiver);
//            }
//        };
//        tt.show(requireActivity().getSupportFragmentManager(),"dialog input");
//    }

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




        public  boolean checkIpAddressIsConnected(String ip)  {
            //TODO: to make your program is work for all platform you need change the command of "ping -c 1 " because in windows -c is -n so try to figure it out
            Process p1 = null;
            int returnVal = 2;

            try {
                p1 = Runtime.getRuntime().exec("ping -c 1 "+ip);
                returnVal = p1.waitFor();
            } catch (IOException | InterruptedException e) {
                Log.e(TAG,"the ip you have check is invalid or incorrect more error  info " + e.getMessage());
                return (returnVal==0);
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
                        if (checkIpAddressIsConnected(userInput)){
                                receiver.setIp(userInput);
                                receiverViewModel.update(receiver);
                            Snackbar.make(getActivity().findViewById(R.id.rooView),"the user "+receiver.getUsername()+" is updated ",Snackbar.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(requireActivity(), "the ip you have entered is not valid or not connected", Toast.LENGTH_SHORT).show();
                        }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });



            AlertDialog alertDialog = builder.create();

            alertDialog.show();


    }


}