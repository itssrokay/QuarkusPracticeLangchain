package org.acme.tools.weather;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AQITool {

    @Tool("Get Air Quality Index (AQI) information for a specific location. Provides AQI value, category, and health recommendations.")
    public String getAirQuality(@P("The location (city, country) to get AQI for") String location) {
        System.out.println("=== AQI TOOL CALLED ===");
        System.out.println("Location: " + location);
        
        // Mock AQI data based on location
        String aqiData = generateMockAQI(location);
        
        // Ensure we never return null or empty
        if (aqiData == null || aqiData.trim().isEmpty()) {
            aqiData = "Error: Unable to retrieve AQI data for " + (location != null ? location : "unknown location");
        }
        
        System.out.println("AQI Response: " + aqiData);
        System.out.println("=== END AQI TOOL ===");
        
        return aqiData;
    }
    
    private String generateMockAQI(String location) {
        // Ensure location is not null or empty
        if (location == null || location.trim().isEmpty()) {
            return "Error: Location not specified. Please provide a valid city name for AQI information.";
        }
        
        // Mock AQI data for different locations
        return switch (location.toLowerCase().trim()) {
            case "berlin", "berlin, germany" -> 
                "AQI in Berlin: 45 (Good). Air quality is satisfactory. Air pollution poses little or no risk. Safe for outdoor activities.";
            case "london", "london, uk" -> 
                "AQI in London: 65 (Moderate). Air quality is acceptable for most people. Unusually sensitive people should consider reducing prolonged outdoor exertion.";
            case "tokyo", "tokyo, japan" -> 
                "AQI in Tokyo: 55 (Moderate). Air quality is acceptable. Sensitive individuals may experience minor breathing discomfort.";
            case "new york", "new york, usa" -> 
                "AQI in New York: 40 (Good). Air quality is excellent. Perfect for all outdoor activities and exercise.";
            case "mumbai", "mumbai, india" -> 
                "AQI in Mumbai: 120 (Unhealthy for Sensitive Groups). People with respiratory conditions should limit outdoor activities. Consider wearing a mask.";
            case "beijing", "beijing, china" -> 
                "AQI in Beijing: 180 (Unhealthy). Everyone may experience health effects. Limit outdoor activities and wear protective masks.";
            case "delhi", "delhi, india" -> 
                "AQI in Delhi: 200 (Very Unhealthy). Health alert - everyone should avoid outdoor activities. Stay indoors with air purifiers.";
            default -> 
                String.format("AQI in %s: 60 (Moderate). Air quality is generally acceptable for most people. Sensitive individuals should be cautious.", location);
        };
    }
} 