package org.acme.dapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SearchFlightDapi {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String searchFlightDapi(String params) {
        System.out.println("=== SEARCH_FLIGHT DAPI HANDLER CALLED ===");
        System.out.println("DAPI Handler: searchFlightDapi");
        System.out.println("Received Parameters:");
        System.out.println(params);
        
        try {
            // Parse the JSON parameters
            JsonNode paramsNode = objectMapper.readTree(params);
            String origin = paramsNode.get("origin").asText();
            String destination = paramsNode.get("destination").asText();
            String date = paramsNode.get("date").asText();
            
            System.out.println("Extracted Parameters:");
            System.out.println("  - origin: " + origin);
            System.out.println("  - destination: " + destination);
            System.out.println("  - date: " + date);
            
            // Simulate API call with mock flight search response
            ObjectNode mockResponse = objectMapper.createObjectNode();
            mockResponse.put("status", "success");
            
            ArrayNode flights = objectMapper.createArrayNode();
            
            // Flight 1
            ObjectNode flight1 = objectMapper.createObjectNode();
            flight1.put("flightNumber", "AB1234");
            flight1.put("airline", "AirlineX");
            flight1.put("departure", origin + " - 08:00");
            flight1.put("arrival", destination + " - 11:30");
            flight1.put("duration", "3h 30m");
            flight1.put("price", 299.99);
            flight1.put("currency", "USD");
            flight1.put("class", "Economy");
            flights.add(flight1);
            
            // Flight 2
            ObjectNode flight2 = objectMapper.createObjectNode();
            flight2.put("flightNumber", "CD5678");
            flight2.put("airline", "SkyLine");
            flight2.put("departure", origin + " - 14:15");
            flight2.put("arrival", destination + " - 17:45");
            flight2.put("duration", "3h 30m");
            flight2.put("price", 349.99);
            flight2.put("currency", "USD");
            flight2.put("class", "Economy");
            flights.add(flight2);
            
            // Flight 3
            ObjectNode flight3 = objectMapper.createObjectNode();
            flight3.put("flightNumber", "EF9012");
            flight3.put("airline", "CloudAir");
            flight3.put("departure", origin + " - 19:30");
            flight3.put("arrival", destination + " - 23:00");
            flight3.put("duration", "3h 30m");
            flight3.put("price", 279.99);
            flight3.put("currency", "USD");
            flight3.put("class", "Economy");
            flights.add(flight3);
            
            mockResponse.set("flights", flights);
            mockResponse.put("totalFlights", 3);
            
            String response = mockResponse.toString();
            
            System.out.println("Mock DAPI Response:");
            System.out.println(response);
            System.out.println("=== END SEARCH_FLIGHT DAPI HANDLER ===");
            
            return response;
            
        } catch (Exception e) {
            System.out.println("Error in DAPI handler: " + e.getMessage());
            return "{\"status\": \"error\", \"message\": \"Failed to search flights\"}";
        }
    }
} 