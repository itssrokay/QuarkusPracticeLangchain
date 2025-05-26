package org.acme.dapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RebookFlightDapi {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String rebookFlightDapi(String params) {
        System.out.println("=== REBOOK_FLIGHT DAPI HANDLER CALLED ===");
        System.out.println("DAPI Handler: rebookFlightDapi");
        System.out.println("Received Parameters:");
        System.out.println(params);
        
        try {
            // Parse the JSON parameters
            JsonNode paramsNode = objectMapper.readTree(params);
            String orderId = paramsNode.get("orderId").asText();
            JsonNode itineraries = paramsNode.get("itineraries");
            
            String origin = itineraries.get("originLocationCode").asText();
            String destination = itineraries.get("destinationLocationCode").asText();
            String date = itineraries.get("departureDateTime").asText();
            String lastName = itineraries.get("lastName").asText();
            
            System.out.println("Extracted Parameters:");
            System.out.println("  - orderId (PNR): " + orderId);
            System.out.println("  - origin: " + origin);
            System.out.println("  - destination: " + destination);
            System.out.println("  - date: " + date);
            System.out.println("  - lastName: " + lastName);
            
            // Simulate API call with mock rebooking response
            ObjectNode mockResponse = objectMapper.createObjectNode();
            mockResponse.put("status", "success");
            
            ObjectNode data = objectMapper.createObjectNode();
            data.put("originalPnr", orderId);
            data.put("newPnr", orderId + "-RBK");
            data.put("passengerName", "John " + lastName);
            
            ObjectNode originalFlight = objectMapper.createObjectNode();
            originalFlight.put("flightNumber", "AB1234");
            originalFlight.put("status", "Cancelled");
            data.set("originalFlight", originalFlight);
            
            ObjectNode newFlight = objectMapper.createObjectNode();
            newFlight.put("flightNumber", "CD5678");
            newFlight.put("departure", origin + " - " + date + "T14:30:00");
            newFlight.put("arrival", destination + " - " + date + "T18:00:00");
            newFlight.put("seatNumber", "15C");
            newFlight.put("status", "Confirmed");
            data.set("newFlight", newFlight);
            
            ObjectNode rebookingFee = objectMapper.createObjectNode();
            rebookingFee.put("amount", 75.00);
            rebookingFee.put("currency", "USD");
            data.set("rebookingFee", rebookingFee);
            
            mockResponse.set("data", data);
            
            String response = mockResponse.toString();
            
            System.out.println("Mock DAPI Response:");
            System.out.println(response);
            System.out.println("=== END REBOOK_FLIGHT DAPI HANDLER ===");
            
            return response;
            
        } catch (Exception e) {
            System.out.println("Error in DAPI handler: " + e.getMessage());
            return "{\"status\": \"error\", \"message\": \"Failed to rebook flight\"}";
        }
    }
} 