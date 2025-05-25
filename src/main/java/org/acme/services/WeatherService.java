package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.models.WeatherResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Service to handle weather data API calls
 */
@ApplicationScoped
public class WeatherService {
    // Mock database of weather information (in a real app, this would call an external API)
    private final Map<String, WeatherResponse> weatherData = new HashMap<>();

    public WeatherService() {
        // Initialize with sample data
        weatherData.put("berlin", new WeatherResponse("Berlin", "12°C", "very hot,sunny"));
        weatherData.put("london", new WeatherResponse("London", "10°C", "Rainy"));
        weatherData.put("paris", new WeatherResponse("Paris", "15°C", "Sunny"));
        weatherData.put("new york", new WeatherResponse("New York", "18°C", "Clear"));
        weatherData.put("tokyo", new WeatherResponse("Tokyo", "20°C", "Humid"));
    }

    /**
     * Get weather information for a specific city
     * @param city The name of the city
     * @return Formatted weather information with usage instructions
     */
    public String getWeatherForCity(String city) {
        WeatherResponse weather = getWeatherData(city);

        // Return formatted response with usage instructions
        return String.format(
                "The current weather in %s is %s with a temperature of %s.\n\n" +
                        "Usage instructions: This weather data can be used to suggest appropriate clothing " +
                        "or activities for the user in %s.",
                weather.getCity(),
                weather.getDescription(),
                weather.getTemperature(),
                weather.getCity()
        );
    }

    // Internal method to get weather data
    private WeatherResponse getWeatherData(String city) {
        String cityLower = city.toLowerCase();
        if (weatherData.containsKey(cityLower)) {
            return weatherData.get(cityLower);
        } else {
            // Generate random weather for unknown cities
            Random random = new Random();
            int temp = random.nextInt(35) - 5; // -5 to 30 degrees
            String[] conditions = {"Sunny", "Cloudy", "Rainy", "Windy", "Snowy", "Clear"};
            String condition = conditions[random.nextInt(conditions.length)];
            return new WeatherResponse(city, temp + "°C", condition);
        }
    }
}