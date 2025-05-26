package org.acme;

import java.util.UUID;

import org.acme.agents.TravelAgent;
import org.acme.agents.WeatherAgent;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST resource for testing different agents and multi-agent scenarios
 */
@Path("/agents")
public class MultiAgentChatResource {

    @Inject
    ManagerAgent managerAgent;
    
    @Inject
    WeatherAgent weatherAgent;
    
    @Inject
    TravelAgent travelAgent;
    
    @Inject
    AirlineAgent airlineAgent;

    /**
     * Test the ManagerAgent - Central orchestrator
     */
    @GET
    @Path("/manager")
    @Produces(MediaType.TEXT_PLAIN)
    public Response testManager(@QueryParam("q") String query, @QueryParam("sessionId") String sessionId) {
        System.out.println("=== TESTING MANAGER AGENT ===");
        
        if (query == null || query.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Query parameter 'q' is required").build();
        }

        String currentSessionId = sessionId != null ? sessionId : UUID.randomUUID().toString();
        String response = managerAgent.chat(currentSessionId, query);
        
        return Response.ok("MANAGER AGENT RESPONSE:\n" + response).build();
    }

    /**
     * Test the WeatherAgent directly
     */
    @GET
    @Path("/weather")
    @Produces(MediaType.TEXT_PLAIN)
    public Response testWeather(@QueryParam("q") String query, @QueryParam("sessionId") String sessionId) {
        System.out.println("=== TESTING WEATHER AGENT DIRECTLY ===");
        
        if (query == null || query.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Query parameter 'q' is required").build();
        }

        String currentSessionId = sessionId != null ? sessionId : UUID.randomUUID().toString();
        String response = weatherAgent.chat(currentSessionId, query);
        
        return Response.ok("WEATHER AGENT RESPONSE:\n" + response).build();
    }

    /**
     * Test the TravelAgent directly
     */
    @GET
    @Path("/travel")
    @Produces(MediaType.TEXT_PLAIN)
    public Response testTravel(@QueryParam("q") String query, @QueryParam("sessionId") String sessionId) {
        System.out.println("=== TESTING TRAVEL AGENT DIRECTLY ===");
        
        if (query == null || query.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Query parameter 'q' is required").build();
        }

        String currentSessionId = sessionId != null ? sessionId : UUID.randomUUID().toString();
        String response = travelAgent.chat(currentSessionId, query);
        
        return Response.ok("TRAVEL AGENT RESPONSE:\n" + response).build();
    }

    /**
     * Test the AirlineAgent directly
     */
    @GET
    @Path("/airline")
    @Produces(MediaType.TEXT_PLAIN)
    public Response testAirline(@QueryParam("q") String query, @QueryParam("sessionId") String sessionId) {
        System.out.println("=== TESTING AIRLINE AGENT DIRECTLY ===");
        
        if (query == null || query.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Query parameter 'q' is required").build();
        }

        String currentSessionId = sessionId != null ? sessionId : UUID.randomUUID().toString();
        String response = airlineAgent.chat(currentSessionId, query);
        
        return Response.ok("AIRLINE AGENT RESPONSE:\n" + response).build();
    }

    /**
     * Test complex multi-agent scenarios
     */
    @GET
    @Path("/complex")
    @Produces(MediaType.TEXT_PLAIN)
    public Response testComplex(@QueryParam("scenario") String scenario, @QueryParam("sessionId") String sessionId) {
        System.out.println("=== TESTING COMPLEX MULTI-AGENT SCENARIO ===");
        
        if (scenario == null || scenario.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Scenario parameter is required. Options: trip-planning, weather-travel, airline-weather").build();
        }

        String currentSessionId = sessionId != null ? sessionId : UUID.randomUUID().toString();
        String query = getComplexScenarioQuery(scenario);
        
        if (query == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid scenario. Options: trip-planning, weather-travel, airline-weather").build();
        }
        
        String response = managerAgent.chat(currentSessionId, query);
        
        return Response.ok("COMPLEX SCENARIO (" + scenario + ") RESPONSE:\n" + response).build();
    }

    /**
     * Health check endpoint
     */
    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public Response health() {
        return Response.ok("Multi-Agent System is running!\n\nAvailable endpoints:\n" +
                "/agents/manager - Test ManagerAgent\n" +
                "/agents/weather - Test WeatherAgent\n" +
                "/agents/travel - Test TravelAgent\n" +
                "/agents/airline - Test AirlineAgent\n" +
                "/agents/complex - Test complex scenarios\n" +
                "/agents/health - This health check").build();
    }

    private String getComplexScenarioQuery(String scenario) {
        return switch (scenario.toLowerCase()) {
            case "trip-planning" -> 
                "I'm planning a trip to Tokyo next month. Can you check the weather forecast, suggest attractions to visit, and help me understand my flight booking BS8ND5 for passenger WICK?";
            case "weather-travel" -> 
                "I want to go somewhere with good weather for a beach vacation. Can you suggest destinations with current weather conditions and UV index information?";
            case "airline-weather" -> 
                "I have a flight booking BS8ND5 for WICK. Can you check my booking details and tell me the weather at my destination?";
            default -> null;
        };
    }
} 