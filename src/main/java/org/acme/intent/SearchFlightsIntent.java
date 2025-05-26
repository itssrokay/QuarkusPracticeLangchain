package org.acme.intent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SearchFlightsIntent {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String searchFlights(String origin, String destination, String date) {
        System.out.println("=== SEARCH_FLIGHTS INTENT FUNCTION CALLED ===");
        System.out.println("Function: searchFlights");
        System.out.println("Input Parameters:");
        System.out.println("  - origin: " + origin);
        System.out.println("  - destination: " + destination);
        System.out.println("  - date: " + date);
        
        // Create JSON payload similar to Python structure
        ObjectNode flightInfo = objectMapper.createObjectNode();
        flightInfo.put("origin", origin);
        flightInfo.put("destination", destination);
        flightInfo.put("date", date);
        flightInfo.put("timestamp", System.currentTimeMillis());
        
        String jsonPayload = flightInfo.toString();
        
        System.out.println("Formatted JSON Payload:");
        System.out.println(jsonPayload);
        System.out.println("=== END SEARCH_FLIGHTS INTENT ===");
        
        return jsonPayload;
    }
} 