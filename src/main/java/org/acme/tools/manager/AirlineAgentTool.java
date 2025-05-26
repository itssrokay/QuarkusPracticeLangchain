package org.acme.tools.manager;

import org.acme.AirlineAgent;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AirlineAgentTool {

    @Inject
    AirlineAgent airlineAgent;

    @Tool("Delegate airline-related queries to the specialized Airline Agent. Use this for PNR retrieval, refunds, rebooking, and airline services.")
    public String delegateToAirlineAgent(@P("The airline-related query to process") String query,
                                       @P("Session ID for conversation continuity") String sessionId) {
        System.out.println("=== DELEGATING TO AIRLINE AGENT ===");
        System.out.println("Query: " + query);
        System.out.println("Session ID: " + sessionId);
        
        // Validate input parameters
        if (query == null || query.trim().isEmpty()) {
            return "Error: No query provided for airline agent. Please specify an airline-related question.";
        }
        if (sessionId == null || sessionId.trim().isEmpty()) {
            sessionId = "default-airline-session";
        }
        
        String response = airlineAgent.chat(sessionId, query);
        
        // Ensure we never return null or empty response
        if (response == null || response.trim().isEmpty()) {
            response = "Error: Airline agent returned no response. Please try again with a specific airline query.";
        }
        
        System.out.println("Airline Agent Response: " + response);
        System.out.println("=== END AIRLINE AGENT DELEGATION ===");
        
        return response;
    }
} 