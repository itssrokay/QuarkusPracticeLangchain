package org.acme.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.services.WeatherService;

@ApplicationScoped
public class WeatherTool {

    @Inject
    WeatherService weatherService;

    @Tool("Get the current weather for a specific city. This tool will provide the temperature and weather conditions.")
    public String getWeather(@P("The name of the city for which to get weather information") String city) {
        // Note: No parameter extraction logic here - Langchain4j handles passing the parameter from the LLM
        return weatherService.getWeatherForCity(city);
    }
}