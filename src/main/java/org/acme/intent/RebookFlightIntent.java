package org.acme.intent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RebookFlightIntent {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String rebookFlight(String origin, String destination, String date, String pnr, String lastName) {
        System.out.println("=== REBOOK_FLIGHT INTENT FUNCTION CALLED ===");
        System.out.println("Function: rebookFlight");
        System.out.println("Input Parameters:");
        System.out.println("  - origin: " + origin);
        System.out.println("  - destination: " + destination);
        System.out.println("  - date: " + date);
        System.out.println("  - pnr: " + pnr);
        System.out.println("  - lastName: " + lastName);
        
        // Create JSON payload similar to Python structure
        ObjectNode rebookInfo = objectMapper.createObjectNode();
        rebookInfo.put("commercialFareFamilies", "ECONOMY");
        
        ObjectNode itineraries = objectMapper.createObjectNode();
        itineraries.put("originLocationCode", origin);
        itineraries.put("destinationLocationCode", destination);
        itineraries.put("departureDateTime", date);
        itineraries.put("lastName", lastName);
        itineraries.put("action", "change");
        itineraries.put("boundId", "1");
        itineraries.put("directFlights", false);
        itineraries.put("departureTimeWindow", 12);
        
        rebookInfo.set("itineraries", itineraries);
        rebookInfo.put("orderId", pnr);
        rebookInfo.put("timestamp", System.currentTimeMillis());
        
        String jsonPayload = rebookInfo.toString();
        
        System.out.println("Formatted JSON Payload:");
        System.out.println(jsonPayload);
        System.out.println("=== END REBOOK_FLIGHT INTENT ===");
        
        return jsonPayload;
    }
} 