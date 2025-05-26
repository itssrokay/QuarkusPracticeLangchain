package org.acme.tools.flight;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FlightStatusTool {

    @Tool("Get real-time flight status information using flight number and date. This tool provides departure/arrival times, delays, and gate information.")
    public String flightStatus(@P("The flight number (e.g., AB1234, CD5678)") String flightNumber,
                              @P("The flight date in YYYY-MM-DD format") String date) {
        
        System.out.println("=== FLIGHT_STATUS TOOL CALLED ===");
        System.out.println("Tool: flightStatus");
        System.out.println("LLM Extracted Parameters:");
        System.out.println("  - flightNumber: " + flightNumber);
        System.out.println("  - date: " + date);
        
        // Validate input parameters
        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            return "Error: Flight number is required. Please provide a valid flight number (e.g., AB1234).";
        }
        if (date == null || date.trim().isEmpty()) {
            return "Error: Flight date is required. Please provide a date in YYYY-MM-DD format.";
        }
        
        // Mock flight status data
        String statusResponse = generateMockFlightStatus(flightNumber, date);
        
        System.out.println("Flight Status Response:");
        System.out.println(statusResponse);
        System.out.println("=== END FLIGHT_STATUS TOOL ===");
        
        return statusResponse;
    }
    
    private String generateMockFlightStatus(String flightNumber, String date) {
        // Generate mock flight status based on flight number
        return switch (flightNumber.toUpperCase().trim()) {
            case "AB1234" -> String.format("""
                <h3>Flight Status: %s on %s</h3>
                
                <div style="border: 1px solid #green; padding: 10px; margin: 10px 0; background-color: #f0fff0;">
                    <h4 style="color: green;">✈️ ON TIME</h4>
                    
                    <h5>Departure:</h5>
                    <ul>
                        <li><b>Airport:</b> JFK - New York</li>
                        <li><b>Scheduled:</b> 10:00 AM</li>
                        <li><b>Actual:</b> 10:05 AM</li>
                        <li><b>Gate:</b> A12</li>
                        <li><b>Terminal:</b> 4</li>
                    </ul>
                    
                    <h5>Arrival:</h5>
                    <ul>
                        <li><b>Airport:</b> LAX - Los Angeles</li>
                        <li><b>Scheduled:</b> 1:00 PM</li>
                        <li><b>Estimated:</b> 1:05 PM</li>
                        <li><b>Gate:</b> B7</li>
                        <li><b>Terminal:</b> 2</li>
                    </ul>
                    
                    <p><b>Aircraft:</b> Boeing 737-800 | <b>Status:</b> In Flight</p>
                </div>
                """, flightNumber, date);
                
            case "CD5678" -> String.format("""
                <h3>Flight Status: %s on %s</h3>
                
                <div style="border: 1px solid #orange; padding: 10px; margin: 10px 0; background-color: #fff8dc;">
                    <h4 style="color: orange;">⏰ DELAYED</h4>
                    
                    <h5>Departure:</h5>
                    <ul>
                        <li><b>Airport:</b> LAX - Los Angeles</li>
                        <li><b>Scheduled:</b> 2:15 PM</li>
                        <li><b>Revised:</b> 3:00 PM</li>
                        <li><b>Gate:</b> C15</li>
                        <li><b>Terminal:</b> 3</li>
                    </ul>
                    
                    <h5>Arrival:</h5>
                    <ul>
                        <li><b>Airport:</b> JFK - New York</li>
                        <li><b>Scheduled:</b> 5:45 PM</li>
                        <li><b>Revised:</b> 6:30 PM</li>
                        <li><b>Gate:</b> A8</li>
                        <li><b>Terminal:</b> 4</li>
                    </ul>
                    
                    <p><b>Delay Reason:</b> Air Traffic Control | <b>Delay:</b> 45 minutes</p>
                </div>
                """, flightNumber, date);
                
            case "EF9012" -> String.format("""
                <h3>Flight Status: %s on %s</h3>
                
                <div style="border: 1px solid #red; padding: 10px; margin: 10px 0; background-color: #ffe4e1;">
                    <h4 style="color: red;">❌ CANCELLED</h4>
                    
                    <h5>Original Schedule:</h5>
                    <ul>
                        <li><b>Departure:</b> JFK - New York at 7:30 PM</li>
                        <li><b>Arrival:</b> LAX - Los Angeles at 11:00 PM</li>
                        <li><b>Gate:</b> B22</li>
                    </ul>
                    
                    <p><b>Cancellation Reason:</b> Weather conditions at departure airport</p>
                    <p><b>Rebooking Options:</b> Passengers can rebook on next available flight at no additional cost.</p>
                    <p><b>Contact:</b> Please contact customer service for rebooking assistance.</p>
                </div>
                """, flightNumber, date);
                
            default -> String.format("""
                <h3>Flight Status: %s on %s</h3>
                
                <div style="border: 1px solid #blue; padding: 10px; margin: 10px 0; background-color: #f0f8ff;">
                    <h4 style="color: blue;">ℹ️ SCHEDULED</h4>
                    
                    <h5>Departure:</h5>
                    <ul>
                        <li><b>Airport:</b> Origin Airport</li>
                        <li><b>Scheduled:</b> Check airline website</li>
                        <li><b>Gate:</b> TBA</li>
                    </ul>
                    
                    <h5>Arrival:</h5>
                    <ul>
                        <li><b>Airport:</b> Destination Airport</li>
                        <li><b>Scheduled:</b> Check airline website</li>
                        <li><b>Gate:</b> TBA</li>
                    </ul>
                    
                    <p><b>Note:</b> Flight information will be updated closer to departure time.</p>
                </div>
                """, flightNumber, date);
        };
    }
} 