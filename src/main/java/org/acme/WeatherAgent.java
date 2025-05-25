package org.acme;

import org.acme.tools.ServiceTool;
import org.acme.tools.WeatherTool;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(tools = {WeatherTool.class, ServiceTool.class})
public interface WeatherAgent {

    @SystemMessage("""
            You are an AI agent supposed to help people with their queries.
            
            For weather-related questions, use the getWeather tool to get accurate information.
            For airline service-related questions, use the getServices tool to retrieve available services for a PNR.
            
            When using tools, wait until you have all required parameters before making the call.
            If a parameter is missing, ask the user for it directly.
            
            For service queries:
            - If the user asks about services for a booking, use the getServices tool with the PNR
            - If the user asks about specific services (like wheelchair, baggage, meals), first get all services for the PNR, then provide information about the specific service they asked about
            - Always provide clear information about pricing and how to add services to their booking
            """)
    String chat(@MemoryId String sessionId, @UserMessage String userMessage);
}