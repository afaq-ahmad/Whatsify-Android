package com.codespacepro.whatsify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codespacepro.whatsify.Models.Message;
import com.codespacepro.whatsify.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;

    private Context context;
    private List<Message> messages;

    public MessageAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSenderId().equals(FirebaseAuth.getInstance().getUid())) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_message_sent, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_message_received, parent, false);
            return new ReceivedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if (holder instanceof SentViewHolder) {
            ((SentViewHolder) holder).message.setText(message.getText());
            ((SentViewHolder) holder).info.setText(formatInfo(message));
        } else {
            ((ReceivedViewHolder) holder).message.setText(message.getText());
            ((ReceivedViewHolder) holder).info.setText(formatInfo(message));
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private String formatInfo(Message message) {
        String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date(message.getTimestamp()));
        String status;
        switch (message.getStatus()) {
            case 2:
                status = "read";
                break;
            case 1:
                status = "delivered";
                break;
            default:
                status = "sent";
        }
        return time + " • " + status;
    }

    static class SentViewHolder extends RecyclerView.ViewHolder {
        TextView message, info;
        SentViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.text_message_sent);
            info = itemView.findViewById(R.id.text_message_sent_info);
        }
    }

    static class ReceivedViewHolder extends RecyclerView.ViewHolder {
        TextView message, info;
        ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.text_message_received);
            info = itemView.findViewById(R.id.text_message_received_info);
        }
    }
}
