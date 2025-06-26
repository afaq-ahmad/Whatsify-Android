package com.codespacepro.whatsify.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codespacepro.whatsify.Adapters.ChatAdapter;
import com.codespacepro.whatsify.Models.Chat;
import com.codespacepro.whatsify.R;
import com.codespacepro.whatsify.Repository.LocalUserRepository;

import java.util.ArrayList;
import java.util.List;

public class NewChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyView;
    private EditText searchBar;

    private final List<Chat> users = new ArrayList<>();
    private final List<Chat> filteredUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        recyclerView = findViewById(R.id.new_chat_list);
        emptyView = findViewById(R.id.empty_view);
        searchBar = findViewById(R.id.search_bar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        users.addAll(LocalUserRepository.getUsers());
        applyFilter("");

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void applyFilter(String query) {
        filteredUsers.clear();
        if (query == null) query = "";
        String lower = query.toLowerCase();
        for (Chat chat : users) {
            if ((chat.getFullname() != null && chat.getFullname().toLowerCase().contains(lower)) ||
                    (chat.getEmail() != null && chat.getEmail().toLowerCase().contains(lower))) {
                filteredUsers.add(chat);
            }
        }
        if (filteredUsers.isEmpty() && !query.isEmpty()) {
            Chat manual = new Chat(query, query, query, false);
            filteredUsers.add(manual);
        }
        emptyView.setVisibility(filteredUsers.isEmpty() ? View.VISIBLE : View.GONE);

        recyclerView.setAdapter(new ChatAdapter(NewChatActivity.this, filteredUsers, chat -> {
            Intent intent = new Intent(NewChatActivity.this, ChatActivity.class);
            intent.putExtra("receiverId", chat.getUid());
            startActivity(intent);
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
