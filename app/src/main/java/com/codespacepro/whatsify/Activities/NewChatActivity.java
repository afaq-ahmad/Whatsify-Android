package com.codespacepro.whatsify.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codespacepro.whatsify.Adapters.ChatAdapter;
import com.codespacepro.whatsify.Models.Chat;
import com.codespacepro.whatsify.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Chat> users = new ArrayList<>();
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        recyclerView = findViewById(R.id.new_chat_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref = FirebaseDatabase.getInstance().getReference().child("Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (snap.hasChild("email") && snap.hasChild("fullname")) {
                        String email = snap.child("email").getValue(String.class);
                        String fullname = snap.child("fullname").getValue(String.class);
                        String uid = snap.getKey();
                        users.add(new Chat(uid, email, fullname));
                    }
                }
                recyclerView.setAdapter(new ChatAdapter(NewChatActivity.this, users, chat -> {
                    Intent intent = new Intent(NewChatActivity.this, ChatActivity.class);
                    intent.putExtra("receiverId", chat.getUid());
                    startActivity(intent);
                    finish();
                }));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
