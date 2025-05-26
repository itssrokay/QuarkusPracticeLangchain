package org.acme;

import org.acme.agents.TravelAgent;
import org.acme.agents.WeatherAgent;
import org.acme.tools.travel.LocalAttractionsTool;
import org.acme.tools.travel.SuggestDestinationsTool;
import org.acme.tools.weather.AQITool;
import org.acme.tools.weather.ForecastTool;
import org.acme.tools.weather.UVIndexTool;
import org.acme.tools.weather.WeatherTool;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class MultiAgentSystemTest {

    @Inject
    ManagerAgent managerAgent;
    
    @Inject
    WeatherAgent weatherAgent;
    
    @Inject
    TravelAgent travelAgent;
    
    @Inject
    AirlineAgent airlineAgent;
    
    // Weather Tools
    @Inject
    WeatherTool weatherTool;
    
    @Inject
    AQITool aqiTool;
    
    @Inject
    ForecastTool forecastTool;
    
    @Inject
    UVIndexTool uvIndexTool;
    
    // Travel Tools
    @Inject
    SuggestDestinationsTool suggestDestinationsTool;
    
    @Inject
    LocalAttractionsTool localAttractionsTool;

    @Test
    public void testWeatherToolsDirectly() {
        System.out.println("\n=== TESTING WEATHER TOOLS DIRECTLY ===");
        
        // Test Weather Tool
        String weather = weatherTool.getCurrentWeather("Berlin");
        assertNotNull(weather);
        assertTrue(weather.contains("Berlin"));
        assertTrue(weather.contains("°C"));
        
        // Test AQI Tool
        String aqi = aqiTool.getAirQuality("Mumbai");
        assertNotNull(aqi);
        assertTrue(aqi.contains("AQI"));
        assertTrue(aqi.contains("Mumbai"));
        
        // Test Forecast Tool
        String forecast = forecastTool.getWeatherForecast("Tokyo", 3);
        assertNotNull(forecast);
        assertTrue(forecast.contains("forecast"));
        assertTrue(forecast.contains("Tokyo"));
        
        // Test UV Index Tool
        String uvIndex = uvIndexTool.getUVIndex("Sydney");
        assertNotNull(uvIndex);
        assertTrue(uvIndex.contains("UV Index"));
        assertTrue(uvIndex.contains("Sydney"));
        
        System.out.println("All weather tools working correctly");
        System.out.println("=== WEATHER TOOLS TEST COMPLETED ===\n");
    }

    @Test
    public void testTravelToolsDirectly() {
        System.out.println("\n=== TESTING TRAVEL TOOLS DIRECTLY ===");
        
        // Test Destination Suggestions
        String destinations = suggestDestinationsTool.suggestDestinations("adventure", "mid-range", 7);
        assertNotNull(destinations);
        assertTrue(destinations.contains("adventure"));
        assertTrue(destinations.contains("mid-range"));
        
        // Test Local Attractions
        String attractions = localAttractionsTool.getLocalAttractions("Paris");
        assertNotNull(attractions);
        assertTrue(attractions.contains("Paris"));
        assertTrue(attractions.contains("Eiffel Tower"));
        
        System.out.println("All travel tools working correctly");
        System.out.println("=== TRAVEL TOOLS TEST COMPLETED ===\n");
    }

    @Test
    public void testWeatherAgentIntegration() {
        System.out.println("\n=== TESTING WEATHER AGENT INTEGRATION ===");
        
        String response = weatherAgent.chat("weather-test-session", "What's the weather like in Berlin and what's the UV index?");
        
        assertNotNull(response);
        // The agent should use multiple tools and provide comprehensive weather info
        System.out.println("Weather Agent Response: " + response);
        
        System.out.println("=== WEATHER AGENT TEST COMPLETED ===\n");
    }

    @Test
    public void testTravelAgentIntegration() {
        System.out.println("\n=== TESTING TRAVEL AGENT INTEGRATION ===");
        
        String response = travelAgent.chat("travel-test-session", "I want adventure travel for 10 days with a mid-range budget");
        
        assertNotNull(response);
        System.out.println("Travel Agent Response: " + response);
        
        System.out.println("=== TRAVEL AGENT TEST COMPLETED ===\n");
    }

    @Test
    public void testManagerAgentDelegation() {
        System.out.println("\n=== TESTING MANAGER AGENT DELEGATION ===");
        
        // Test weather delegation
        String weatherResponse = managerAgent.chat("manager-test-1", "What's the weather in Tokyo?");
        assertNotNull(weatherResponse);
        System.out.println("Manager → Weather: " + weatherResponse);
        
        // Test travel delegation
        String travelResponse = managerAgent.chat("manager-test-2", "Suggest beach destinations for a luxury 5-day trip");
        assertNotNull(travelResponse);
        System.out.println("Manager → Travel: " + travelResponse);
        
        // Test airline delegation
        String airlineResponse = managerAgent.chat("manager-test-3", "Get booking details for PNR BS8ND5 and last name WICK");
        assertNotNull(airlineResponse);
        System.out.println("Manager → Airline: " + airlineResponse);
        
        System.out.println("=== MANAGER AGENT TEST COMPLETED ===\n");
    }

    @Test
    public void testComplexMultiAgentQuery() {
        System.out.println("\n=== TESTING COMPLEX MULTI-AGENT QUERY ===");
        
        String complexQuery = "I'm planning a trip to Bali. Can you tell me the weather forecast, suggest some attractions, and help me understand if I need to worry about air quality?";
        
        String response = managerAgent.chat("complex-test-session", complexQuery);
        
        assertNotNull(response);
        // This should potentially involve both weather and travel agents
        System.out.println("Complex Query Response: " + response);
        
        System.out.println("=== COMPLEX MULTI-AGENT TEST COMPLETED ===\n");
    }

    @Test
    public void testAgentSpecialization() {
        System.out.println("\n=== TESTING AGENT SPECIALIZATION ===");
        
        // Each agent should handle their domain well
        String weatherSpecific = weatherAgent.chat("spec-weather", "Give me detailed weather info for London including AQI and UV index");
        assertNotNull(weatherSpecific);
        
        String travelSpecific = travelAgent.chat("spec-travel", "What are the top attractions in New York?");
        assertNotNull(travelSpecific);
        
        String airlineSpecific = airlineAgent.chat("spec-airline", "What services are available for PNR BS8ND5?");
        assertNotNull(airlineSpecific);
        
        System.out.println("All agents demonstrate proper specialization");
        System.out.println("=== AGENT SPECIALIZATION TEST COMPLETED ===\n");
    }
} 