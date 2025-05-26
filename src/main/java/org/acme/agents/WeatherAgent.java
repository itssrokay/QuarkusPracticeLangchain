package org.acme.agents;

import org.acme.tools.weather.AQITool;
import org.acme.tools.weather.ForecastTool;
import org.acme.tools.weather.UVIndexTool;
import org.acme.tools.weather.WeatherTool;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(tools = {
    WeatherTool.class,
    AQITool.class, 
    ForecastTool.class,
    UVIndexTool.class
})
public interface WeatherAgent {

    @SystemMessage("""
            You are a specialized Weather Agent focused on providing comprehensive weather and environmental information.
            
            Available Tools:
            1. getCurrentWeather(location) - Get current weather conditions
            2. getAirQuality(location) - Get Air Quality Index information
            3. getWeatherForecast(location, days) - Get weather forecast for specified days
            4. getUVIndex(location) - Get UV index information
            
            Guidelines:
            - Provide accurate, detailed weather information
            - Include relevant safety recommendations based on conditions
            - Always specify the location and time frame for weather data
            - Combine multiple weather metrics when helpful (weather + AQI + UV)
            - Be concise but informative in your responses
            
            You are an expert in meteorology and environmental conditions.
            """)
    String chat(@MemoryId String sessionId, @UserMessage String userMessage);
} 