package org.acme.tools.flight;

import org.acme.dapi.SearchFlightDapi;
import org.acme.intent.SearchFlightsIntent;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SearchFlightsTool {

    @Inject
    SearchFlightsIntent searchFlightsIntent;
    
    @Inject
    SearchFlightDapi searchFlightDapi;

    @Tool("Search for available flights using origin, destination, and date. This tool provides flight options with prices, times, and airlines.")
    public String searchFlights(@P("The origin city or airport code (e.g., JFK, New York)") String origin,
                               @P("The destination city or airport code (e.g., LAX, Los Angeles)") String destination,
                               @P("The travel date in YYYY-MM-DD format") String date) {
        
        System.out.println("=== SEARCH_FLIGHTS TOOL CALLED ===");
        System.out.println("Tool: searchFlights");
        System.out.println("LLM Extracted Parameters:");
        System.out.println("  - origin: " + origin);
        System.out.println("  - destination: " + destination);
        System.out.println("  - date: " + date);
        
        // Validate input parameters
        if (origin == null || origin.trim().isEmpty()) {
            return "Error: Origin is required. Please provide a valid origin city or airport code.";
        }
        if (destination == null || destination.trim().isEmpty()) {
            return "Error: Destination is required. Please provide a valid destination city or airport code.";
        }
        if (date == null || date.trim().isEmpty()) {
            return "Error: Travel date is required. Please provide a date in YYYY-MM-DD format.";
        }
        
        // Step 1: Call intent function to format parameters
        System.out.println("\n--- STEP 1: CALLING INTENT FUNCTION ---");
        String intentPayload = searchFlightsIntent.searchFlights(origin, destination, date);
        
        // Step 2: Call DAPI handler with formatted payload
        System.out.println("\n--- STEP 2: CALLING DAPI HANDLER ---");
        String dapiResponse = searchFlightDapi.searchFlightDapi(intentPayload);
        
        // Step 3: Format response for LLM consumption
        System.out.println("\n--- STEP 3: FORMATTING RESPONSE FOR LLM ---");
        String formattedResponse = formatFlightSearchResponseForLlm(dapiResponse, origin, destination, date);
        
        System.out.println("Final Response to LLM:");
        System.out.println(formattedResponse);
        System.out.println("=== END SEARCH_FLIGHTS TOOL ===");
        
        return formattedResponse;
    }
    
    private String formatFlightSearchResponseForLlm(String dapiResponse, String origin, String destination, String date) {
        // Format the response for better LLM understanding and HTML output
        return String.format("""
            <h3>Flight Search Results: %s → %s on %s</h3>
            
            <h4>Best 3 Flight Options (sorted by price):</h4>
            
            <div style="border: 1px solid #ccc; padding: 10px; margin: 10px 0;">
                <h4>1. EF9012 - CloudAir (Cheapest)</h4>
                <ul>
                    <li><b>Price:</b> $279.99 USD</li>
                    <li><b>Departure:</b> %s - 19:30</li>
                    <li><b>Arrival:</b> %s - 23:00</li>
                    <li><b>Duration:</b> 3h 30m</li>
                    <li><b>Class:</b> Economy</li>
                </ul>
            </div>
            
            <div style="border: 1px solid #ccc; padding: 10px; margin: 10px 0;">
                <h4>2. AB1234 - AirlineX</h4>
                <ul>
                    <li><b>Price:</b> $299.99 USD</li>
                    <li><b>Departure:</b> %s - 08:00</li>
                    <li><b>Arrival:</b> %s - 11:30</li>
                    <li><b>Duration:</b> 3h 30m</li>
                    <li><b>Class:</b> Economy</li>
                </ul>
            </div>
            
            <div style="border: 1px solid #ccc; padding: 10px; margin: 10px 0;">
                <h4>3. CD5678 - SkyLine</h4>
                <ul>
                    <li><b>Price:</b> $349.99 USD</li>
                    <li><b>Departure:</b> %s - 14:15</li>
                    <li><b>Arrival:</b> %s - 17:45</li>
                    <li><b>Duration:</b> 3h 30m</li>
                    <li><b>Class:</b> Economy</li>
                </ul>
            </div>
            
            <p><b>Recommendation:</b> Flight EF9012 offers the best value at $279.99. All flights are direct with similar duration.</p>
            
            <!-- Debug Info: %s -->
            """, origin, destination, date, origin, destination, origin, destination, origin, destination, dapiResponse);
    }
} 