package org.acme.tools.weather;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WeatherTool {

    @Tool("Get current weather conditions for a specific location. Provides temperature, humidity, wind speed, and general conditions.")
    public String getCurrentWeather(@P("The location (city, country) to get weather for") String location) {
        System.out.println("=== WEATHER TOOL CALLED ===");
        System.out.println("Location: " + location);
        
        // Mock weather data based on location
        String weatherData = generateMockWeather(location);
        
        // Ensure we never return null or empty
        if (weatherData == null || weatherData.trim().isEmpty()) {
            weatherData = "Error: Unable to retrieve weather data for " + (location != null ? location : "unknown location");
        }
        
        System.out.println("Weather Response: " + weatherData);
        System.out.println("=== END WEATHER TOOL ===");
        
        return weatherData;
    }
    
    private String generateMockWeather(String location) {
        // Ensure location is not null or empty
        if (location == null || location.trim().isEmpty()) {
            return "Error: Location not specified. Please provide a valid city name.";
        }
        
        // Mock weather data for different locations
        return switch (location.toLowerCase().trim()) {
            case "berlin", "berlin, germany" -> 
                "Current weather in Berlin: 15°C, Partly cloudy, Humidity: 65%, Wind: 12 km/h NW, Visibility: 10km. Good conditions for outdoor activities.";
            case "london", "london, uk" -> 
                "Current weather in London: 12°C, Light rain, Humidity: 80%, Wind: 8 km/h SW, Visibility: 8km. Carry an umbrella if going out.";
            case "tokyo", "tokyo, japan" -> 
                "Current weather in Tokyo: 22°C, Sunny, Humidity: 55%, Wind: 6 km/h E, Visibility: 15km. Perfect weather for sightseeing.";
            case "new york", "new york, usa" -> 
                "Current weather in New York: 18°C, Clear skies, Humidity: 45%, Wind: 10 km/h S, Visibility: 12km. Excellent weather conditions.";
            case "mumbai", "mumbai, india" -> 
                "Current weather in Mumbai: 28°C, Humid and partly cloudy, Humidity: 85%, Wind: 15 km/h SW, Visibility: 6km. High humidity, stay hydrated.";
            default -> 
                String.format("Current weather in %s: 20°C, Partly cloudy, Humidity: 60%%, Wind: 10 km/h, Visibility: 10km. Pleasant conditions.", location);
        };
    }
} 