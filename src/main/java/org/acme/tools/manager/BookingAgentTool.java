package org.acme.tools.manager;

import org.acme.agents.BookingAgent;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BookingAgentTool {

    @Inject
    BookingAgent bookingAgent;

    @Tool("Delegate queries to the BookingAgent for PNR retrieval, refunds, and booking services. Use this for any booking-related queries.")
    public String delegateToBookingAgent(@P("The customer query to delegate to the BookingAgent") String query) {
        
        System.out.println("=== MANAGER DELEGATING TO BOOKING AGENT ===");
        System.out.println("Manager Tool: delegateToBookingAgent");
        System.out.println("Query to delegate: " + query);
        
        // Validate input
        if (query == null || query.trim().isEmpty()) {
            return "Error: Query is required for delegation to BookingAgent.";
        }
        
        // Generate a session ID for this delegation
        String sessionId = "booking-" + System.currentTimeMillis();
        
        // Delegate to BookingAgent
        System.out.println("Delegating to BookingAgent with sessionId: " + sessionId);
        String response = bookingAgent.chat(sessionId, query);
        
        System.out.println("BookingAgent Response:");
        System.out.println(response);
        System.out.println("=== END BOOKING AGENT DELEGATION ===");
        
        return response;
    }
} 