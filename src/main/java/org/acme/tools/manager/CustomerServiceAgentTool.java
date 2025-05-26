package org.acme.tools.manager;

import org.acme.agents.CustomerServiceAgent;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CustomerServiceAgentTool {

    @Inject
    CustomerServiceAgent customerServiceAgent;

    @Tool("Delegate queries to the CustomerServiceAgent for policies, contact information, and general FAQs. Use this for general information queries.")
    public String delegateToCustomerServiceAgent(@P("The customer query to delegate to the CustomerServiceAgent") String query) {
        
        System.out.println("=== MANAGER DELEGATING TO CUSTOMER SERVICE AGENT ===");
        System.out.println("Manager Tool: delegateToCustomerServiceAgent");
        System.out.println("Query to delegate: " + query);
        
        // Validate input
        if (query == null || query.trim().isEmpty()) {
            return "Error: Query is required for delegation to CustomerServiceAgent.";
        }
        
        // Generate a session ID for this delegation
        String sessionId = "service-" + System.currentTimeMillis();
        
        // Delegate to CustomerServiceAgent
        System.out.println("Delegating to CustomerServiceAgent with sessionId: " + sessionId);
        String response = customerServiceAgent.chat(sessionId, query);
        
        System.out.println("CustomerServiceAgent Response:");
        System.out.println(response);
        System.out.println("=== END CUSTOMER SERVICE AGENT DELEGATION ===");
        
        return response;
    }
} 