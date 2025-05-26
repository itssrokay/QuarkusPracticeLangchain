package org.acme.intent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RetrieveServicesIntent {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String retrieveServices(String pnr) {
        System.out.println("=== RETRIEVE_SERVICES INTENT FUNCTION CALLED ===");
        System.out.println("Function: retrieveServices");
        System.out.println("Input Parameters:");
        System.out.println("  - pnr: " + pnr);
        
        // Create JSON payload similar to Python structure
        ObjectNode servicesInfo = objectMapper.createObjectNode();
        servicesInfo.put("pnr", pnr);
        servicesInfo.put("timestamp", System.currentTimeMillis());
        
        String jsonPayload = servicesInfo.toString();
        
        System.out.println("Formatted JSON Payload:");
        System.out.println(jsonPayload);
        System.out.println("=== END RETRIEVE_SERVICES INTENT ===");
        
        return jsonPayload;
    }
} 