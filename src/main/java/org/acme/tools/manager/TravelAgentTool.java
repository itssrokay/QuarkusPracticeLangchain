package org.acme.tools.manager;

import org.acme.agents.TravelAgent;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TravelAgentTool {

    @Inject
    TravelAgent travelAgent;

    @Tool("Delegate travel-related queries to the specialized Travel Agent. Use this for destination suggestions, travel planning, and local attractions.")
    public String delegateToTravelAgent(@P("The travel-related query to process") String query,
                                      @P("Session ID for conversation continuity") String sessionId) {
        System.out.println("=== DELEGATING TO TRAVEL AGENT ===");
        System.out.println("Query: " + query);
        System.out.println("Session ID: " + sessionId);
        
        // Validate input parameters
        if (query == null || query.trim().isEmpty()) {
            return "Error: No query provided for travel agent. Please specify a travel-related question.";
        }
        if (sessionId == null || sessionId.trim().isEmpty()) {
            sessionId = "default-travel-session";
        }
        
        String response = travelAgent.chat(sessionId, query);
        
        // Ensure we never return null or empty response
        if (response == null || response.trim().isEmpty()) {
            response = "Error: Travel agent returned no response. Please try again with a specific travel query.";
        }
        
        System.out.println("Travel Agent Response: " + response);
        System.out.println("=== END TRAVEL AGENT DELEGATION ===");
        
        return response;
    }
} 