package com.example.lmstudio;

import java.util.List;

public class ChatResponse {
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public static class Choice {
        private ChatMessage message;

        public ChatMessage getMessage() {
            return message;
        }
    }
}
