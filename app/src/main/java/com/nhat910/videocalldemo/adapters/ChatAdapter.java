package com.nhat910.videocalldemo.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhat910.videocalldemo.R;
import com.nhat910.videocalldemo.holder.ChatReceiveHolder;
import com.nhat910.videocalldemo.holder.ChatSenderHolder;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.model.QBChatMessage;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {
    private ArrayList<QBChatMessage> qbChatMessages;

    public ChatAdapter(ArrayList<QBChatMessage> qbChatMessages) {
        this.qbChatMessages = qbChatMessages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        if (qbChatMessages.get(i).getSenderId().equals(QBChatService.getInstance().getUser().getId())) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_send_chat, viewGroup, false);
            viewHolder = new ChatSenderHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_receive_chat, viewGroup, false);
            viewHolder = new ChatReceiveHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof ChatSenderHolder){
            ((ChatSenderHolder) viewHolder).tvContent.setText(qbChatMessages.get(i).getBody());
        }
        if(viewHolder instanceof ChatReceiveHolder){
            ((ChatReceiveHolder) viewHolder).tvContent.setText(qbChatMessages.get(i).getBody());
        }
    }

    @Override
    public int getItemCount() {
        return qbChatMessages != null ? qbChatMessages.size() : 0;
    }
}
