package com.example.lmstudio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import io.noties.markwon.Markwon;
import com.example.lmstudio.ChatMessage;
import com.example.lmstudio.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<ChatMessage> messages = new ArrayList<>();
    private Markwon markwon;

    // Constructor que recibe el contexto
    public MessageAdapter(Context context) {
        // Inicializa Markwon con el contexto de la actividad o aplicación
        markwon = Markwon.create(context);
    }

    public void setMessages(List<ChatMessage> newMessages) {
        this.messages = newMessages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatMessage message = messages.get(position);

        // Procesar el mensaje Markdown y mostrarlo
        markwon.setMarkdown(holder.messageText, message.getContent());

        // Asegúrate de que el rol también sea visible
        holder.roleText.setText(message.getRole());

        // Alinear el label del rol según el rol del mensaje
        ConstraintLayout.LayoutParams roleLayoutParams = (ConstraintLayout.LayoutParams) holder.roleText.getLayoutParams();
        if (message.getRole().equals("user")) {

            roleLayoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET;
            roleLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            holder.roleText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        } else {
            roleLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            roleLayoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET;
            holder.roleText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
        holder.roleText.setLayoutParams(roleLayoutParams);

        // Alinear el mensaje según el rol
        ConstraintLayout.LayoutParams messageLayoutParams = (ConstraintLayout.LayoutParams) holder.messageText.getLayoutParams();
        if (message.getRole().equals("user")) {

            messageLayoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET;
            messageLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            holder.messageText.setBackgroundResource(R.drawable.bubble_user);
            holder.messageText.setTextColor(holder.messageText.getResources().getColor(R.color.surfaceDark));
        } else {
            messageLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            messageLayoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET;
            holder.messageText.setTextColor(holder.messageText.getResources().getColor(R.color.white));

            holder.messageText.setBackgroundResource(R.drawable.bubble_assistant);
        }

        holder.messageText.setLayoutParams(messageLayoutParams);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageText;
        private final TextView roleText;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            roleText = itemView.findViewById(R.id.roleText);
        }

        public void bind(ChatMessage message) {
            messageText.setText(message.getContent());
            roleText.setText(message.getRole());
        }
    }
}
