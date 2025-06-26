package com.codespacepro.whatsify.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.codespacepro.whatsify.Adapters.ChatAdapter;
import com.codespacepro.whatsify.Models.Chat;
import com.codespacepro.whatsify.R;
import com.codespacepro.whatsify.Activities.ChatActivity;
import com.codespacepro.whatsify.Activities.ContactsActivity;
import com.codespacepro.whatsify.Activities.NewChatActivity;
import com.codespacepro.whatsify.Repository.LocalUserRepository;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {


    public ChatFragment() {
        // Required empty public constructor
    }


    RecyclerView recyclerView;
    List<Chat> usersArrayList = new ArrayList<>();
    FloatingActionButton addContactBtn, startChatBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.recyclerviewchat);
        addContactBtn = view.findViewById(R.id.btn_add_contact);
        startChatBtn = view.findViewById(R.id.btn_start_chat);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        addContactBtn.setOnClickListener(v ->
                startActivity(new Intent(getContext(), ContactsActivity.class)));

        startChatBtn.setOnClickListener(v ->
                startActivity(new Intent(getContext(), NewChatActivity.class)));


        usersArrayList.clear();
        usersArrayList.addAll(LocalUserRepository.getUsers());
        recyclerView.setAdapter(new ChatAdapter(getContext(), usersArrayList, chat -> {
            Intent intent = new Intent(getContext(), ChatActivity.class);
            intent.putExtra("receiverId", chat.getUid());
            startActivity(intent);
        }));

        return view;
    }
}