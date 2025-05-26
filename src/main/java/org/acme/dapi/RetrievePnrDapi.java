package org.acme.dapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RetrievePnrDapi {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String retrievePnrDapi(String params) {
        System.out.println("=== RETRIEVE_PNR DAPI HANDLER CALLED ===");
        System.out.println("DAPI Handler: retrievePnrDapi");
        System.out.println("Received Parameters:");
        System.out.println(params);
        
        try {
            // Parse the JSON parameters
            JsonNode paramsNode = objectMapper.readTree(params);
            String pnr = paramsNode.get("pnr").asText();
            String lastName = paramsNode.get("lastName").asText();
            
            System.out.println("Extracted Parameters:");
            System.out.println("  - pnr: " + pnr);
            System.out.println("  - lastName: " + lastName);
            
            // Simulate API call with mock response
            ObjectNode mockResponse = objectMapper.createObjectNode();
            mockResponse.put("status", "success");
            
            ObjectNode data = objectMapper.createObjectNode();
            data.put("pnr", pnr);
            data.put("passengerName", "John " + lastName);
            data.put("flightNumber", "AB1234");
            data.put("departure", "JFK - New York");
            data.put("arrival", "LAX - Los Angeles");
            data.put("departureTime", "2024-03-25T10:00:00");
            data.put("arrivalTime", "2024-03-25T13:00:00");
            data.put("seatNumber", "12A");
            data.put("class", "Economy");
            data.put("status", "Confirmed");
            
            mockResponse.set("data", data);
            
            String response = mockResponse.toString();
            
            System.out.println("Mock DAPI Response:");
            System.out.println(response);
            System.out.println("=== END RETRIEVE_PNR DAPI HANDLER ===");
            
            return response;
            
        } catch (Exception e) {
            System.out.println("Error in DAPI handler: " + e.getMessage());
            return "{\"status\": \"error\", \"message\": \"Failed to retrieve PNR data\"}";
        }
    }
} 