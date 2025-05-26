package org.acme.tools.manager;

import org.acme.agents.FlightSearchAgent;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class FlightSearchAgentTool {

    @Inject
    FlightSearchAgent flightSearchAgent;

    @Tool("Delegate queries to the FlightSearchAgent for flight search, rebooking, and flight status. Use this for any flight-related queries.")
    public String delegateToFlightSearchAgent(@P("The customer query to delegate to the FlightSearchAgent") String query) {
        
        System.out.println("=== MANAGER DELEGATING TO FLIGHT SEARCH AGENT ===");
        System.out.println("Manager Tool: delegateToFlightSearchAgent");
        System.out.println("Query to delegate: " + query);
        
        // Validate input
        if (query == null || query.trim().isEmpty()) {
            return "Error: Query is required for delegation to FlightSearchAgent.";
        }
        
        // Generate a session ID for this delegation
        String sessionId = "flight-" + System.currentTimeMillis();
        
        // Delegate to FlightSearchAgent
        System.out.println("Delegating to FlightSearchAgent with sessionId: " + sessionId);
        String response = flightSearchAgent.chat(sessionId, query);
        
        System.out.println("FlightSearchAgent Response:");
        System.out.println(response);
        System.out.println("=== END FLIGHT SEARCH AGENT DELEGATION ===");
        
        return response;
    }
} 