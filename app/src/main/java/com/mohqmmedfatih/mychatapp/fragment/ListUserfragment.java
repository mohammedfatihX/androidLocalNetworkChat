package com.mohqmmedfatih.mychatapp.fragment;

import static com.mohqmmedfatih.mychatapp.tools.Config.WHOLECHAT;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.mohqmmedfatih.mychatapp.R;
import com.mohqmmedfatih.mychatapp.adapter.ChatBublesAdapter;
import com.mohqmmedfatih.mychatapp.adapter.UserAdapter;
import com.mohqmmedfatih.mychatapp.interfaces.UpdateUserFragmetListener;
import com.mohqmmedfatih.mychatapp.models.User;
import com.mohqmmedfatih.mychatapp.tools.Config;

import java.util.Objects;

public class ListUserfragment extends Fragment implements UpdateUserFragmetListener {

    final String TAG = "ListUserfragment";
    RecyclerView recyclerViewListUser;
    ImageView myImage;
    Button setting;
    UserAdapter userAdapter;
    public ChatUserFragment chatUserFragment;



    public ListUserfragment() {
        // Required empty public constructor
    }




    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =inflater.inflate(R.layout.fragment_list_userfragment, container, false);
        // Inflate the layout for this fragment
        myImage = root.findViewById(R.id.myimage);
        myImage.setImageDrawable(AvatarGenerator.Companion.avatarImage(getContext(),120, AvatarConstants.Companion.getCIRCLE(), Config.me.getUsername()));
        setting = root.findViewById(R.id.setting_btn);
        recyclerViewListUser = root.findViewById(R.id.userLists);
        userAdapter = new UserAdapter(getContext(), user -> {
            chatUserFragment = ChatUserFragment.newInstance(user);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
           fragmentManager.beginTransaction().
            replace(R.id.fragmentplace,chatUserFragment).
            commit();
        });
        recyclerViewListUser.setAdapter(userAdapter);
        recyclerViewListUser.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }

    @Override
    public void onReceivedNewUser(User newUser) {


        Log.e(TAG, "new user Position : " + WHOLECHAT.getLastindex());
        userAdapter.notifyItemInserted(WHOLECHAT.getLastindex());
    }
}