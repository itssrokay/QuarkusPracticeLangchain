package org.acme.tools.travel;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LocalAttractionsTool {

    @Tool("Get local attractions and points of interest for a specific location.")
    public String getLocalAttractions(@P("The location to get attractions for") String location) {
        System.out.println("=== LOCAL ATTRACTIONS TOOL CALLED ===");
        System.out.println("Location: " + location);
        
        String attractions = generateAttractions(location);
        
        // Ensure we never return null or empty
        if (attractions == null || attractions.trim().isEmpty()) {
            attractions = "Error: Unable to retrieve local attractions for " + (location != null ? location : "unknown location");
        }
        
        System.out.println("Attractions Response: " + attractions);
        System.out.println("=== END LOCAL ATTRACTIONS TOOL ===");
        
        return attractions;
    }
    
    private String generateAttractions(String location) {
        // Ensure location is not null or empty
        if (location == null || location.trim().isEmpty()) {
            return "Error: Location not specified. Please provide a valid city name to get local attractions.";
        }
        
        return switch (location.toLowerCase().trim()) {
            case "paris", "paris, france" -> 
                "Top attractions in Paris:\n1. Eiffel Tower - Iconic landmark\n2. Louvre Museum - World's largest art museum\n3. Notre-Dame Cathedral - Gothic architecture\n4. Champs-Élysées - Famous avenue\n5. Montmartre - Artistic district";
            case "tokyo", "tokyo, japan" -> 
                "Top attractions in Tokyo:\n1. Senso-ji Temple - Ancient Buddhist temple\n2. Tokyo Skytree - Modern observation tower\n3. Shibuya Crossing - Busiest intersection\n4. Meiji Shrine - Peaceful shrine\n5. Tsukiji Fish Market - Fresh seafood";
            case "new york", "new york, usa" -> 
                "Top attractions in New York:\n1. Statue of Liberty - Symbol of freedom\n2. Central Park - Urban oasis\n3. Times Square - Bright lights district\n4. Brooklyn Bridge - Historic bridge\n5. 9/11 Memorial - Moving tribute";
            default -> 
                String.format("Popular attractions in %s:\n1. Historic city center\n2. Local museums\n3. Cultural landmarks\n4. Natural parks\n5. Traditional markets", location);
        };
    }
} 