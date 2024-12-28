package com.example.lmstudio;

import java.util.List;

public class ChatRequest {
    private final List<ChatMessage> messages;
    private final double temperature;
    private final int max_tokens;
    private final boolean stream;

    public ChatRequest(List<ChatMessage> messages) {
        this.messages = messages;
        this.temperature = 0.3;
        this.max_tokens = -1;
        this.stream = false;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getMaxTokens() {
        return max_tokens;
    }

    public boolean isStream() {
        return stream;
    }
}
