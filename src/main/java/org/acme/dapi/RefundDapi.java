package org.acme.dapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RefundDapi {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String refundDapi(String params) {
        System.out.println("=== REFUND DAPI HANDLER CALLED ===");
        System.out.println("DAPI Handler: refundDapi");
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
            
            // Simulate API call with mock refund response
            ObjectNode mockResponse = objectMapper.createObjectNode();
            mockResponse.put("status", "success");
            
            ObjectNode data = objectMapper.createObjectNode();
            data.put("pnr", pnr);
            data.put("passengerName", "John " + lastName);
            data.put("refundEligible", true);
            
            ObjectNode refundAmount = objectMapper.createObjectNode();
            refundAmount.put("amount", 450.00);
            refundAmount.put("currency", "USD");
            data.set("refundAmount", refundAmount);
            
            data.put("refundPolicy", "Full refund available - cancellation within 24 hours");
            data.put("processingTime", "5-7 business days");
            data.put("refundMethod", "Original payment method");
            
            ObjectNode cancellationFee = objectMapper.createObjectNode();
            cancellationFee.put("amount", 0.00);
            cancellationFee.put("currency", "USD");
            data.set("cancellationFee", cancellationFee);
            
            mockResponse.set("data", data);
            
            String response = mockResponse.toString();
            
            System.out.println("Mock DAPI Response:");
            System.out.println(response);
            System.out.println("=== END REFUND DAPI HANDLER ===");
            
            return response;
            
        } catch (Exception e) {
            System.out.println("Error in DAPI handler: " + e.getMessage());
            return "{\"status\": \"error\", \"message\": \"Failed to process refund\"}";
        }
    }
} 