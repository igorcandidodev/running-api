package com.runningapi.runningapi.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatGptService {

    private final ChatClient chatClient;

    public ChatGptService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String sendPrompt(String systemPrompt, String userPrompt) {
        return chatClient
                .prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .content();
    }
}
