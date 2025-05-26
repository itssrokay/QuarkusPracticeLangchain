package org.acme.tools.manager;

import org.acme.agents.WeatherAgent;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WeatherAgentTool {

    @Inject
    WeatherAgent weatherAgent;

    @Tool("Delegate weather-related queries to the specialized Weather Agent. Use this for weather conditions, forecasts, air quality, and UV index questions.")
    public String delegateToWeatherAgent(@P("The weather-related query to process") String query,
                                       @P("Session ID for conversation continuity") String sessionId) {
        System.out.println("=== DELEGATING TO WEATHER AGENT ===");
        System.out.println("Query: " + query);
        System.out.println("Session ID: " + sessionId);
        
        // Validate input parameters
        if (query == null || query.trim().isEmpty()) {
            return "Error: No query provided for weather agent. Please specify a weather-related question.";
        }
        if (sessionId == null || sessionId.trim().isEmpty()) {
            sessionId = "default-weather-session";
        }
        
        String response = weatherAgent.chat(sessionId, query);
        
        // Ensure we never return null or empty response
        if (response == null || response.trim().isEmpty()) {
            response = "Error: Weather agent returned no response. Please try again with a specific weather query.";
        }
        
        System.out.println("Weather Agent Response: " + response);
        System.out.println("=== END WEATHER AGENT DELEGATION ===");
        
        return response;
    }
} 