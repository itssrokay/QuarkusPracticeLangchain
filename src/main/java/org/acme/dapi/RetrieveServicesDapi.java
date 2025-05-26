package org.acme.dapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RetrieveServicesDapi {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String retrieveServicesDapi(String params) {
        System.out.println("=== RETRIEVE_SERVICES DAPI HANDLER CALLED ===");
        System.out.println("DAPI Handler: retrieveServicesDapi");
        System.out.println("Received Parameters:");
        System.out.println(params);
        
        try {
            // Parse the JSON parameters
            JsonNode paramsNode = objectMapper.readTree(params);
            String pnr = paramsNode.get("pnr").asText();
            
            System.out.println("Extracted Parameters:");
            System.out.println("  - pnr: " + pnr);
            
            // Simulate API call with mock services response
            ObjectNode mockResponse = objectMapper.createObjectNode();
            mockResponse.put("status", "success");
            
            ArrayNode services = objectMapper.createArrayNode();
            
            // Service 1
            ObjectNode service1 = objectMapper.createObjectNode();
            service1.put("id", "SVC001");
            service1.put("name", "Extra Baggage");
            service1.put("description", "Additional 23kg baggage allowance");
            ObjectNode price1 = objectMapper.createObjectNode();
            price1.put("amount", 50.00);
            price1.put("currency", "USD");
            service1.set("price", price1);
            service1.put("category", "baggage");
            services.add(service1);
            
            // Service 2
            ObjectNode service2 = objectMapper.createObjectNode();
            service2.put("id", "SVC002");
            service2.put("name", "Seat Selection");
            service2.put("description", "Premium seat selection with extra legroom");
            ObjectNode price2 = objectMapper.createObjectNode();
            price2.put("amount", 35.00);
            price2.put("currency", "USD");
            service2.set("price", price2);
            service2.put("category", "seating");
            services.add(service2);
            
            // Service 3
            ObjectNode service3 = objectMapper.createObjectNode();
            service3.put("id", "SVC003");
            service3.put("name", "In-flight Meal");
            service3.put("description", "Special dietary meal selection");
            ObjectNode price3 = objectMapper.createObjectNode();
            price3.put("amount", 25.00);
            price3.put("currency", "USD");
            service3.set("price", price3);
            service3.put("category", "meal");
            services.add(service3);
            
            // Service 4
            ObjectNode service4 = objectMapper.createObjectNode();
            service4.put("id", "SVC004");
            service4.put("name", "Priority Boarding");
            service4.put("description", "Board the aircraft before general boarding");
            ObjectNode price4 = objectMapper.createObjectNode();
            price4.put("amount", 15.00);
            price4.put("currency", "USD");
            service4.set("price", price4);
            service4.put("category", "boarding");
            services.add(service4);
            
            mockResponse.set("services", services);
            mockResponse.put("pnr", pnr);
            mockResponse.put("totalServices", 4);
            
            String response = mockResponse.toString();
            
            System.out.println("Mock DAPI Response:");
            System.out.println(response);
            System.out.println("=== END RETRIEVE_SERVICES DAPI HANDLER ===");
            
            return response;
            
        } catch (Exception e) {
            System.out.println("Error in DAPI handler: " + e.getMessage());
            return "{\"status\": \"error\", \"message\": \"Failed to retrieve services\"}";
        }
    }
} 