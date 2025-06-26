package com.codespacepro.whatsify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codespacepro.whatsify.Models.Chat;
import com.codespacepro.whatsify.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context mContext;
    private List<Chat> mDataList;
    public interface OnChatClickListener {
        void onChatClick(Chat chat);
    }

    private OnChatClickListener listener;
    public ChatAdapter(Context mContext, List<Chat> mDataList, OnChatClickListener listener) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chat = mDataList.get(position);
        holder.Fullname.setText(chat.getFullname());
        holder.Email.setText(chat.getEmail());
        holder.Status.setText(chat.isOnline() ? "Online" : "Offline");
        // Glide.with(mContext).load(chat.getProfile()).placeholder(R.drawable.avatar_profile).into(holder.Profile);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onChatClick(chat);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Fullname, Email, Status;
        CircleImageView Profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Fullname = itemView.findViewById(R.id.nav_fullname);
            Email = itemView.findViewById(R.id.nav_gmail);
            Status = itemView.findViewById(R.id.nav_status);
            Profile = itemView.findViewById(R.id.nav_chat_profile);
        }
    }
}

