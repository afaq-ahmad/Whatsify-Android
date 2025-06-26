package com.codespacepro.whatsify.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codespacepro.whatsify.Adapters.MessageAdapter;
import com.codespacepro.whatsify.Models.Message;
import com.codespacepro.whatsify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView messageList;
    private EditText messageBox;
    private Button sendBtn;

    private MessageAdapter adapter;
    private List<Message> messages = new ArrayList<>();

    private String receiverId;
    private String senderId;
    private DatabaseReference messagesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        receiverId = getIntent().getStringExtra("receiverId");
        senderId = FirebaseAuth.getInstance().getUid();

        messageList = findViewById(R.id.message_list);
        messageBox = findViewById(R.id.message_box);
        sendBtn = findViewById(R.id.send_btn);

        adapter = new MessageAdapter(this, messages);
        messageList.setLayoutManager(new LinearLayoutManager(this));
        messageList.setAdapter(adapter);

        messagesRef = FirebaseDatabase.getInstance().getReference("chats");

        loadMessages();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void loadMessages() {
        if (senderId == null || receiverId == null) return;
        String senderRoom = senderId + receiverId;
        messagesRef.child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);
                    if (message != null) {
                        if (!message.getSenderId().equals(senderId) && message.getStatus() < 2) {
                            message.setStatus(2);
                            dataSnapshot.getRef().setValue(message);
                        }
                        messages.add(message);
                    }
                }
                adapter.notifyDataSetChanged();
                messageList.scrollToPosition(messages.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void sendMessage() {
        String text = messageBox.getText().toString().trim();
        if (TextUtils.isEmpty(text)) return;
        long timestamp = System.currentTimeMillis();
        Message message = new Message(senderId, text, timestamp, 0);
        String senderRoom = senderId + receiverId;
        String receiverRoom = receiverId + senderId;
        String key = messagesRef.child(senderRoom).push().getKey();
        if (key == null) return;
        message.setMessageId(key);
        messagesRef.child(senderRoom).child(key).setValue(message);
        messagesRef.child(receiverRoom).child(key).setValue(message);
        messageBox.setText("");
    }
}
