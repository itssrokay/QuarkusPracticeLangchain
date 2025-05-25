package org.acme;

import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response; // For generating a simple session ID if needed

/**
 * REST resource for chat interactions with comprehensive airline tools
 */
@Path("/chat")
public class ChatResource {

    // If you have a ChatService that then calls WeatherAgent:
    // @Inject
    // ChatService chatService;

    // If ChatResource calls WeatherAgent directly:
    @Inject
    AirlineAgent airlineAgent;

    /**
     * Chat endpoint with comprehensive airline support
     * @param query The user's query
     * @param sessionId A unique identifier for the conversation session
     * @return Response from the AI
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response chat(@QueryParam("q") String query, @QueryParam("sessionId") String sessionId) {
        System.out.println("=== CHAT RESOURCE CALLED ===");
        System.out.println("Received Query: " + query);
        System.out.println("Session ID: " + sessionId);
        
        if (query == null || query.isBlank()) {
            System.out.println("ERROR: Query parameter is missing or empty");
            return Response.status(Response.Status.BAD_REQUEST).entity("Query parameter 'q' is missing or empty.").build();
        }

        String currentSessionId = sessionId;
        if (currentSessionId == null || currentSessionId.isBlank()) {
            currentSessionId = UUID.randomUUID().toString();
            System.out.println("Generated new session ID: " + currentSessionId);
        }

        System.out.println("=== CALLING AIRLINE AGENT ===");
        System.out.println("Session ID: " + currentSessionId);
        System.out.println("User Query: " + query);
        
        // Call the comprehensive airline agent
        String response = airlineAgent.chat(currentSessionId, query);

        System.out.println("=== AIRLINE AGENT RESPONSE ===");
        System.out.println("Response Length: " + response.length() + " characters");
        System.out.println("Response: " + response);
        System.out.println("=== END CHAT RESOURCE ===");

        return Response.ok(response).build();
    }
}