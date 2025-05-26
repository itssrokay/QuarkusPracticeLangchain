package org.acme.tools.weather;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UVIndexTool {

    @Tool("Get UV Index information for a specific location. Provides UV level, risk category, and sun protection recommendations.")
    public String getUVIndex(@P("The location (city, country) to get UV index for") String location) {
        System.out.println("=== UV INDEX TOOL CALLED ===");
        System.out.println("Location: " + location);
        
        // Mock UV index data based on location
        String uvData = generateMockUVIndex(location);
        
        // Ensure we never return null or empty
        if (uvData == null || uvData.trim().isEmpty()) {
            uvData = "Error: Unable to retrieve UV index data for " + (location != null ? location : "unknown location");
        }
        
        System.out.println("UV Index Response: " + uvData);
        System.out.println("=== END UV INDEX TOOL ===");
        
        return uvData;
    }
    
    private String generateMockUVIndex(String location) {
        // Ensure location is not null or empty
        if (location == null || location.trim().isEmpty()) {
            return "Error: Location not specified. Please provide a valid city name for UV index information.";
        }
        
        // Mock UV index data for different locations
        return switch (location.toLowerCase().trim()) {
            case "berlin", "berlin, germany" -> 
                "UV Index in Berlin: 4 (Moderate). Wear sunscreen SPF 30+, sunglasses, and hat during midday hours (10 AM - 4 PM).";
            case "london", "london, uk" -> 
                "UV Index in London: 3 (Moderate). Limited sun protection needed. Sunglasses recommended on bright days.";
            case "tokyo", "tokyo, japan" -> 
                "UV Index in Tokyo: 6 (High). Seek shade during midday. Wear SPF 30+ sunscreen, hat, and protective clothing.";
            case "new york", "new york, usa" -> 
                "UV Index in New York: 5 (Moderate). Use sunscreen SPF 30+. Wear sunglasses and consider a hat during peak hours.";
            case "mumbai", "mumbai, india" -> 
                "UV Index in Mumbai: 9 (Very High). Avoid sun exposure 10 AM - 4 PM. Use SPF 50+ sunscreen, wear protective clothing, hat, and sunglasses.";
            case "sydney", "sydney, australia" -> 
                "UV Index in Sydney: 10 (Very High). Extreme caution required. Stay in shade, use SPF 50+ sunscreen, wear long sleeves and wide-brimmed hat.";
            case "cairo", "cairo, egypt" -> 
                "UV Index in Cairo: 11 (Extreme). Avoid outdoor activities during midday. Use maximum sun protection - SPF 50+, full coverage clothing.";
            case "reykjavik", "reykjavik, iceland" -> 
                "UV Index in Reykjavik: 2 (Low). Minimal sun protection needed. Sunglasses recommended for snow glare.";
            default -> 
                String.format("UV Index in %s: 5 (Moderate). Use sunscreen SPF 30+, wear sunglasses, and limit midday sun exposure.", location);
        };
    }
} 