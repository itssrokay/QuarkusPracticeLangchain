package org.acme.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RetrievePnrApi {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String retrievePnrDapi(String payload) {
        System.out.println("=== RETRIEVE_PNR API HANDLER CALLED ===");
        System.out.println("API Handler: retrievePnrDapi");
        System.out.println("Received Payload:");
        System.out.println(payload);
        
        try {
            // Parse the payload to extract parameters
            JsonNode payloadNode = objectMapper.readTree(payload);
            String pnr = payloadNode.get("pnr").asText();
            String lastName = payloadNode.get("lastName").asText();
            
            System.out.println("Extracted Parameters:");
            System.out.println("  - pnr: " + pnr);
            System.out.println("  - lastName: " + lastName);
            
            // Mock API response simulating real PNR data
            String mockResponse = String.format("""
                {
                    "status": "success",
                    "data": {
                        "pnr": "%s",
                        "passengerName": "John %s",
                        "flightNumber": "AB1234",
                        "departure": {
                            "airport": "JFK",
                            "city": "New York",
                            "dateTime": "2024-03-25T10:00:00"
                        },
                        "arrival": {
                            "airport": "LAX", 
                            "city": "Los Angeles",
                            "dateTime": "2024-03-25T13:00:00"
                        },
                        "seatNumber": "12A",
                        "class": "Economy",
                        "status": "Confirmed"
                    }
                }
                """, pnr, lastName);
            
            System.out.println("Mock API Response:");
            System.out.println(mockResponse);
            System.out.println("=== END RETRIEVE_PNR API HANDLER ===");
            
            return mockResponse;
            
        } catch (Exception e) {
            System.out.println("Error parsing payload: " + e.getMessage());
            return "{\"status\": \"error\", \"message\": \"Failed to retrieve PNR data\"}";
        }
    }
} 