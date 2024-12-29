package com.example.lmstudio;

public class ChatMessage {
    private final String role;
    private  String content;
    private String completionTokens;

    public ChatMessage(String role, String content, String completionTokens) {
        this.role = role;
        this.content = content;
        this.completionTokens = completionTokens;

    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public void addContent(String content) {
        this.content += "\n"+content;

    }
    public String getCompletionTokens() {
        return completionTokens;
    }

}
