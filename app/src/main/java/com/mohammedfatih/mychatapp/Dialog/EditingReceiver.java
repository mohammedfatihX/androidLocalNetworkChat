package com.mohammedfatih.mychatapp.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mohammedfatih.mychatapp.R;

import java.io.IOException;

public  abstract class EditingReceiver extends androidx.fragment.app.DialogFragment {

    public abstract void ipIsCorrect(String Ip);

    private  final String TAG = "EditingReceiver";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog builder = new Dialog(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setContentView(inflater.inflate(R.layout.dialog_edit_user, null));
      //  Button update = builder.findViewById(R.id.update);
        Button cancel = builder.findViewById(R.id.cancel_button);
        EditText updatedIP = builder.findViewById(R.id.ipRecipient);

        /*update.setOnClickListener(event -> {
            String tempvalue = updatedIP.getText().toString().trim();
            if ((tempvalue == null) || "".equalsIgnoreCase(tempvalue) || !tempvalue.contains(".") ){
                Toast.makeText(getContext(), "please enter the address IP Like This 192.168.1.8", Toast.LENGTH_SHORT).show();
            }else if (checkIpAddressIsConnected(tempvalue)){
                Log.e(TAG,"Dialog input ip Recipient  and it is : "+ tempvalue);
                    ipIsCorrect(tempvalue);
            }else {
                Toast.makeText(getContext(), "the ip is not working", Toast.LENGTH_SHORT).show();

            }
            getDialog().cancel();
        });
*/

        cancel.setOnClickListener(event -> {
            getDialog().cancel();
        });

        return builder;
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
