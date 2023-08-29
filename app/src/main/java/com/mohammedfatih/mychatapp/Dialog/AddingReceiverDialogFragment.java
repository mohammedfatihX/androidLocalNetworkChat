package com.mohammedfatih.mychatapp.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mohammedfatih.mychatapp.R;
import com.mohammedfatih.mychatapp.interfaces.InputListener;

import java.io.IOException;

public  class AddingReceiverDialogFragment extends androidx.fragment.app.DialogFragment {
    public final String TAG = "DialogFragment ";
    private  InputListener inputListener;
    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog builder = new Dialog(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setContentView(inflater.inflate(R.layout.dialog_add_user,null));

        Button ok = builder.findViewById(R.id.addUsers_btn);
        Button cancel = builder.findViewById(R.id.cancel_button);

        EditText ipField = builder.findViewById(R.id.ipRecipient);

        ok.setOnClickListener(event ->{
            String tempvalue = ipField.getText().toString();
            if ((tempvalue == null) || "".equalsIgnoreCase(tempvalue) || !tempvalue.contains(".") ){
                Toast.makeText(getContext(), "please enter the address IP Like This 192.168.1.8", Toast.LENGTH_SHORT).show();
            }else if (checkIpAddressIsConnected(tempvalue)){
                Log.e(TAG,"Dialog input ip Recipient  and it is : "+ tempvalue);
                    inputListener.onClickListener(tempvalue);
            }else {
                Toast.makeText(getContext(), "the ip is not working", Toast.LENGTH_SHORT).show();

            }
            getDialog().cancel();
                });
             cancel.setOnClickListener(event -> {
                           getDialog().cancel();
                     });


        return builder;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InputListener){
            inputListener =(InputListener) context;

        }else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public  boolean checkIpAddressIsConnected(String ip)  {
        //TODO: to make your program is work for all platform you need change the command of "ping -c 1 " because in windows -c is -n so try to figure it out
        Process p1 = null;
        int returnVal = 2;

        try {
            p1 = Runtime.getRuntime().exec("ping -c 1 "+ip);
            returnVal = p1.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return (returnVal==0);
    }




}
