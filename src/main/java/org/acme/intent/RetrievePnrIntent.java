package org.acme.intent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RetrievePnrIntent {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String retrievePnr(String pnr, String lastName) {
        System.out.println("=== RETRIEVE_PNR INTENT FUNCTION CALLED ===");
        System.out.println("Function: retrievePnr");
        System.out.println("Input Parameters:");
        System.out.println("  - pnr: " + pnr);
        System.out.println("  - lastName: " + lastName);
        
        // Create JSON payload similar to Python structure
        ObjectNode pnrInfo = objectMapper.createObjectNode();
        pnrInfo.put("pnr", pnr);
        pnrInfo.put("lastName", lastName);
        pnrInfo.put("timestamp", System.currentTimeMillis());
        
        String jsonPayload = pnrInfo.toString();
        
        System.out.println("Formatted JSON Payload:");
        System.out.println(jsonPayload);
        System.out.println("=== END RETRIEVE_PNR INTENT ===");
        
        return jsonPayload;
    }
} 