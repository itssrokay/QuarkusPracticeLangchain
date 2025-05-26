package org.acme.tools.weather;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ForecastTool {

    @Tool("Get weather forecast for a specific location and number of days. Provides daily weather predictions.")
    public String getWeatherForecast(@P("The location (city, country) to get forecast for") String location, 
                                    @P("Number of days for forecast (1-7)") int days) {
        System.out.println("=== FORECAST TOOL CALLED ===");
        System.out.println("Location: " + location);
        System.out.println("Days: " + days);
        
        // Validate days parameter
        if (days < 1 || days > 7) {
            return "Forecast is available for 1-7 days only. Please specify a valid number of days.";
        }
        
        // Mock forecast data based on location
        String forecastData = generateMockForecast(location, days);
        
        // Ensure we never return null or empty
        if (forecastData == null || forecastData.trim().isEmpty()) {
            forecastData = "Error: Unable to retrieve forecast data for " + (location != null ? location : "unknown location");
        }
        
        System.out.println("Forecast Response: " + forecastData);
        System.out.println("=== END FORECAST TOOL ===");
        
        return forecastData;
    }
    
    private String generateMockForecast(String location, int days) {
        // Ensure location is not null or empty
        if (location == null || location.trim().isEmpty()) {
            return "Error: Location not specified. Please provide a valid city name for weather forecast.";
        }
        
        StringBuilder forecast = new StringBuilder();
        forecast.append(String.format("%d-day weather forecast for %s:\n\n", days, location.trim()));
        
        String[] conditions = {"Sunny", "Partly cloudy", "Cloudy", "Light rain", "Rain", "Clear"};
        int[] temps = {18, 20, 16, 14, 22, 19, 17}; // Base temperatures
        
        for (int i = 0; i < days; i++) {
            String day = switch (i) {
                case 0 -> "Today";
                case 1 -> "Tomorrow";
                default -> String.format("Day %d", i + 1);
            };
            
            String condition = conditions[i % conditions.length];
            int temp = temps[i % temps.length];
            int humidity = 50 + (i * 5) % 40; // Varying humidity
            
            forecast.append(String.format("%s: %d°C, %s, Humidity: %d%%\n", 
                day, temp, condition, humidity));
        }
        
        // Add location-specific recommendations
        forecast.append("\nRecommendations: ");
        String locationLower = location.toLowerCase().trim();
        if (locationLower.contains("london")) {
            forecast.append("Pack an umbrella and layers for changing weather.");
        } else if (locationLower.contains("mumbai")) {
            forecast.append("Stay hydrated and use sun protection. Expect high humidity.");
        } else if (locationLower.contains("berlin")) {
            forecast.append("Dress in layers. Weather can change quickly.");
        } else {
            forecast.append("Check conditions before outdoor activities.");
        }
        
        return forecast.toString();
    }
} 