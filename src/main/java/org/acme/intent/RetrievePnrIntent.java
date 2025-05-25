package org.acme.intent;

import jakarta.enterprise.context.ApplicationScoped;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@ApplicationScoped
public class RetrievePnrIntent {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String retrievePnr(String pnr, String lastName) {
        System.out.println("=== RETRIEVE_PNR INTENT FUNCTION CALLED ===");
        System.out.println("Function: retrievePnr");
        System.out.println("Input Parameters:");
        System.out.println("  - pnr: " + pnr);
        System.out.println("  - lastName: " + lastName);
        
        // Create JSON payload
        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("pnr", pnr);
        payload.put("lastName", lastName);
        payload.put("action", "retrieve_pnr");
        payload.put("timestamp", System.currentTimeMillis());
        
        String jsonPayload = payload.toString();
        
        System.out.println("Formatted Payload:");
        System.out.println(jsonPayload);
        System.out.println("=== END RETRIEVE_PNR INTENT ===");
        
        return jsonPayload;
    }
} 