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
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<ChatMessage> messages = new ArrayList<>();
    private Markwon markdwon;

    // Constructor que recibe el contexto
    public MessageAdapter(Context context) {
        markdwon = Markwon.create(context);
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
        holder.bind(message);
        markdwon.setMarkdown(holder.messageText, message.getContent());
        drawItems(holder, message, position);
    }

    private void drawItems(MessageViewHolder holder, ChatMessage message, int position){
        holder.roleText.setText(message.getRole());
        ConstraintLayout.LayoutParams roleLayoutParams = (ConstraintLayout.LayoutParams) holder.roleText.getLayoutParams();
        ConstraintLayout.LayoutParams messageLayoutParams = (ConstraintLayout.LayoutParams) holder.messageText.getLayoutParams();
        ConstraintLayout.LayoutParams completionTokensLayoutParams = (ConstraintLayout.LayoutParams) holder.completionTokensText.getLayoutParams();
        if (message.getRole().equals("user")) {
            roleLayoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET;
            roleLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            messageLayoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET;
            messageLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            completionTokensLayoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET;
            completionTokensLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            holder.roleText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.messageText.setBackgroundResource(R.drawable.bubble_user);
            holder.messageText.setTextColor(holder.messageText.getResources().getColor(R.color.surfaceDark));
            holder.completionTokensText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        } else {
            roleLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            roleLayoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET;
            messageLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            messageLayoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET;
            completionTokensLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            completionTokensLayoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET;
            holder.roleText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.messageText.setTextColor(holder.messageText.getResources().getColor(R.color.white));
            holder.messageText.setBackgroundResource(R.drawable.bubble_assistant);
            holder.completionTokensText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

        }
        holder.roleText.setLayoutParams(roleLayoutParams);
        holder.messageText.setLayoutParams(messageLayoutParams);
        holder.completionTokensText.setLayoutParams(completionTokensLayoutParams);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageText;
        private final TextView roleText;
        private final TextView completionTokensText;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            completionTokensText = itemView.findViewById(R.id.tvCompTokens);
            messageText = itemView.findViewById(R.id.messageText);
            roleText = itemView.findViewById(R.id.roleText);
        }

        public void bind(ChatMessage message) {
            messageText.setText(message.getContent());
            roleText.setText(message.getRole());
            completionTokensText.setText(message.getCompletionTokens());
        }
    }
}
