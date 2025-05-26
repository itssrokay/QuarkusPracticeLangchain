package org.acme.intent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RefundIntent {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String refund(String pnr, String lastName) {
        System.out.println("=== REFUND INTENT FUNCTION CALLED ===");
        System.out.println("Function: refund");
        System.out.println("Input Parameters:");
        System.out.println("  - pnr: " + pnr);
        System.out.println("  - lastName: " + lastName);
        
        // Create JSON payload similar to Python structure
        ObjectNode refundInfo = objectMapper.createObjectNode();
        refundInfo.put("pnr", pnr);
        refundInfo.put("lastName", lastName);
        refundInfo.put("timestamp", System.currentTimeMillis());
        
        String jsonPayload = refundInfo.toString();
        
        System.out.println("Formatted JSON Payload:");
        System.out.println(jsonPayload);
        System.out.println("=== END REFUND INTENT ===");
        
        return jsonPayload;
    }
} 