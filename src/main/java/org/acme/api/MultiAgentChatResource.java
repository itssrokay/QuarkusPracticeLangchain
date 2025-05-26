package org.acme.api;

import org.acme.agents.BookingAgent;
import org.acme.agents.CustomerServiceAgent;
import org.acme.agents.FlightSearchAgent;
import org.acme.agents.ManagerAgent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/chat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MultiAgentChatResource {

    @Inject
    ManagerAgent managerAgent;
    
    @Inject
    BookingAgent bookingAgent;
    
    @Inject
    FlightSearchAgent flightSearchAgent;
    
    @Inject
    CustomerServiceAgent customerServiceAgent;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @POST
    public Response chat(ChatRequest request) {
        System.out.println("=== MULTI-AGENT CHAT ENDPOINT CALLED ===");
        System.out.println("Chat ID: " + request.chatId);
        System.out.println("Customer Conversation: " + request.customerConversation);
        
        try {
            if (request.chatId == null || request.chatId.trim().isEmpty()) {
                return Response.status(400)
                    .entity(createErrorResponse("Invalid input data", "Chat ID is required"))
                    .build();
            }
            
            if (request.customerConversation == null || request.customerConversation.trim().isEmpty()) {
                return Response.status(400)
                    .entity(createErrorResponse("Invalid input data", "Customer conversation is required"))
                    .build();
            }
            
            // Process the conversation through ManagerAgent
            String responseMessage = managerAgent.chat(request.chatId, request.customerConversation);
            
            System.out.println("Manager Agent Response:");
            System.out.println(responseMessage);
            
            // Create response similar to Python Flask structure
            ObjectNode response = objectMapper.createObjectNode();
            response.put("chatId", request.chatId);
            response.put("response", responseMessage);
            response.put("responseType", "multi_agent");
            response.putNull("dapi_response"); // For compatibility with Python structure
            
            System.out.println("=== END MULTI-AGENT CHAT ENDPOINT ===");
            
            return Response.ok(response).build();
            
        } catch (Exception e) {
            System.out.println("Error in chat endpoint: " + e.getMessage());
            e.printStackTrace();
            return Response.status(500)
                .entity(createErrorResponse("Internal server error", e.getMessage()))
                .build();
        }
    }
    
    @GET
    public Response chatGet(@QueryParam("q") String query) {
        System.out.println("=== MULTI-AGENT CHAT GET ENDPOINT CALLED ===");
        System.out.println("Query: " + query);
        
        try {
            if (query == null || query.trim().isEmpty()) {
                return Response.status(400)
                    .entity(createErrorResponse("Invalid input", "Query parameter 'q' is required"))
                    .build();
            }
            
            // Generate a chat ID for GET requests
            String chatId = "get-" + System.currentTimeMillis();
            
            // Process the query through ManagerAgent
            String responseMessage = managerAgent.chat(chatId, query);
            
            System.out.println("Manager Agent Response:");
            System.out.println(responseMessage);
            
            // Create response
            ObjectNode response = objectMapper.createObjectNode();
            response.put("chatId", chatId);
            response.put("response", responseMessage);
            response.put("responseType", "multi_agent");
            response.put("query", query);
            
            System.out.println("=== END MULTI-AGENT CHAT GET ENDPOINT ===");
            
            return Response.ok(response).build();
            
        } catch (Exception e) {
            System.out.println("Error in chat GET endpoint: " + e.getMessage());
            e.printStackTrace();
            return Response.status(500)
                .entity(createErrorResponse("Internal server error", e.getMessage()))
                .build();
        }
    }
    
    // Direct agent endpoints for testing
    @GET
    @Path("/booking")
    public Response bookingAgentTest(@QueryParam("q") String query) {
        if (query == null || query.trim().isEmpty()) {
            return Response.status(400)
                .entity(createErrorResponse("Invalid input", "Query parameter 'q' is required"))
                .build();
        }
        
        String sessionId = "booking-test-" + System.currentTimeMillis();
        String response = bookingAgent.chat(sessionId, query);
        
        ObjectNode result = objectMapper.createObjectNode();
        result.put("agent", "BookingAgent");
        result.put("sessionId", sessionId);
        result.put("query", query);
        result.put("response", response);
        
        return Response.ok(result).build();
    }
    
    @GET
    @Path("/flight")
    public Response flightAgentTest(@QueryParam("q") String query) {
        if (query == null || query.trim().isEmpty()) {
            return Response.status(400)
                .entity(createErrorResponse("Invalid input", "Query parameter 'q' is required"))
                .build();
        }
        
        String sessionId = "flight-test-" + System.currentTimeMillis();
        String response = flightSearchAgent.chat(sessionId, query);
        
        ObjectNode result = objectMapper.createObjectNode();
        result.put("agent", "FlightSearchAgent");
        result.put("sessionId", sessionId);
        result.put("query", query);
        result.put("response", response);
        
        return Response.ok(result).build();
    }
    
    @GET
    @Path("/service")
    public Response serviceAgentTest(@QueryParam("q") String query) {
        if (query == null || query.trim().isEmpty()) {
            return Response.status(400)
                .entity(createErrorResponse("Invalid input", "Query parameter 'q' is required"))
                .build();
        }
        
        String sessionId = "service-test-" + System.currentTimeMillis();
        String response = customerServiceAgent.chat(sessionId, query);
        
        ObjectNode result = objectMapper.createObjectNode();
        result.put("agent", "CustomerServiceAgent");
        result.put("sessionId", sessionId);
        result.put("query", query);
        result.put("response", response);
        
        return Response.ok(result).build();
    }
    
    @GET
    @Path("/health")
    public Response health() {
        ObjectNode health = objectMapper.createObjectNode();
        health.put("status", "healthy");
        health.put("service", "Multi-Agent Airline System");
        health.put("timestamp", System.currentTimeMillis());
        health.put("agents", "ManagerAgent, BookingAgent, FlightSearchAgent, CustomerServiceAgent");
        
        return Response.ok(health).build();
    }
    
    private ObjectNode createErrorResponse(String error, String message) {
        ObjectNode errorResponse = objectMapper.createObjectNode();
        errorResponse.put("error", error);
        errorResponse.put("message", message);
        errorResponse.put("timestamp", System.currentTimeMillis());
        return errorResponse;
    }
    
    // Request class for POST endpoint
    public static class ChatRequest {
        public String chatId;
        public String customerConversation;
        
        // Default constructor for Jackson
        public ChatRequest() {}
        
        public ChatRequest(String chatId, String customerConversation) {
            this.chatId = chatId;
            this.customerConversation = customerConversation;
        }
    }
} 