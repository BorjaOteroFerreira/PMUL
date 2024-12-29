package com.example.lmstudio;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText messageInput;
    private ImageButton sendButton;
    private ProgressBar progressBar;
    private MessageAdapter messageAdapter;
    private ChatViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        // Obtiene la URL del backend desde el Intent
        String backendUrl = getIntent().getStringExtra("BACKEND_URL");
        // Inicializa las vistas
        recyclerView = findViewById(R.id.recyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        progressBar = findViewById(R.id.progressBar);
        // Configura el RecyclerView
        messageAdapter = new MessageAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);
        // Inicializa el ViewModel
        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        viewModel.initialize(backendUrl);
        // Observa cambios en los mensajes
        viewModel.getMessages().observe(this, messages -> {
            messageAdapter.setMessages(messages);
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        });

        // Observa cambios en el estado de carga
        viewModel.getIsLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            sendButton.setEnabled(!isLoading);
            messageInput.setEnabled(!isLoading);
        });

        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                JSONArray previousMessages = new JSONArray();
                for (ChatMessage chatMessage : viewModel.getMessages().getValue()) {
                    // Usar el rol del mensaje en lugar
                    previousMessages.put(new JSONArray()
                            .put(chatMessage.getRole())
                            .put(chatMessage.getContent())
                    );
                }
                viewModel.sendMessage(message);
            }
            messageInput.setText("");
        });

    }
}
