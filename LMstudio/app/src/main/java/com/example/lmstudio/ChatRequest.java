// ChatRequest.java
package com.example.lmstudio;
import java.util.List;


public class ChatRequest {
    private List<ChatMessage> messages;
    private double temperature;
    private int max_tokens;
    private boolean stream;

    public ChatRequest(List<ChatMessage> messages) {
        this.messages = messages;
        this.temperature = 0.3;
        this.max_tokens = -1;
        this.stream = false;
    }
}
