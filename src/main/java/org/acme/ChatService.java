package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Service to handle chat interactions using Langchain4j AI Service.
 */
@ApplicationScoped
public class ChatService {

    @Inject
    WeatherAgent weatherAgent;

    /**
     * Get a response from the chat AI.
     * @param userMessage The user's message.
     * @return Response from the AI.
     */
    public String getChatResponse(String userMessage) {
        // For simplicity, using a fixed session ID. In a real app, this could be a user ID or session token.
        String sessionId = "default-user";
        return weatherAgent.chat("abc",userMessage);
    }
}