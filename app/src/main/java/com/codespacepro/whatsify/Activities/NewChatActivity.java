package com.codespacepro.whatsify.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codespacepro.whatsify.Adapters.ChatAdapter;
import com.codespacepro.whatsify.Models.Chat;
import com.codespacepro.whatsify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyView;
    private EditText searchBar;

    private final List<Chat> users = new ArrayList<>();
    private final List<Chat> filteredUsers = new ArrayList<>();
    private DatabaseReference ref;
    private ValueEventListener userListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        recyclerView = findViewById(R.id.new_chat_list);
        progressBar = findViewById(R.id.progress);
        emptyView = findViewById(R.id.empty_view);
        searchBar = findViewById(R.id.search_bar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref = FirebaseDatabase.getInstance().getReference().child("Users");

        userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                String currentUid = FirebaseAuth.getInstance().getUid();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (snap.getKey() == null || snap.getKey().equals(currentUid)) continue;
                    if (snap.hasChild("email") && snap.hasChild("fullname")) {
                        String email = snap.child("email").getValue(String.class);
                        String fullname = snap.child("fullname").getValue(String.class);
                        String uid = snap.getKey();
                        users.add(new Chat(uid, email, fullname));
                    }
                }
                applyFilter(searchBar.getText().toString());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                emptyView.setText("Failed to load users");
            }
        };

        progressBar.setVisibility(View.VISIBLE);
        ref.addValueEventListener(userListener);

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
            if (chat.getFullname() != null && chat.getFullname().toLowerCase().contains(lower)) {
                filteredUsers.add(chat);
            }
        }
        if (filteredUsers.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
        recyclerView.setAdapter(new ChatAdapter(NewChatActivity.this, filteredUsers, chat -> {
            Intent intent = new Intent(NewChatActivity.this, ChatActivity.class);
            intent.putExtra("receiverId", chat.getUid());
            startActivity(intent);
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ref != null && userListener != null) {
            ref.removeEventListener(userListener);
        }
    }
}
