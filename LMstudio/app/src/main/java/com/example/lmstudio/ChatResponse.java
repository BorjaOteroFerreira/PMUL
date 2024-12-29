package com.example.lmstudio;

import java.util.List;

public class ChatResponse {
    private List<Choice> choices;
    private Usage usage;

    public List<Choice> getChoices() {
        return choices;
    }

    public Usage getUsage() {
        return usage;
    }

    public static class Choice {
        private ChatMessage message;

        public ChatMessage getMessage() {
            return message;
        }
    }

    public static class Usage {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;

        public int getPromptTokens() {
            return prompt_tokens;
        }

        public int getCompletionTokens() {
            return completion_tokens;
        }

        public int getTotalTokens() {
            return total_tokens;
        }
    }
}