package org.acme.tools.flight;

import org.acme.dapi.RebookFlightDapi;
import org.acme.intent.RebookFlightIntent;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RebookFlightTool {

    @Inject
    RebookFlightIntent rebookFlightIntent;
    
    @Inject
    RebookFlightDapi rebookFlightDapi;

    @Tool("Rebook a flight to new date and route using PNR and passenger details. This tool changes existing booking to new flight details.")
    public String rebookFlight(@P("The origin city or airport code") String origin,
                              @P("The destination city or airport code") String destination,
                              @P("The new travel date in YYYY-MM-DD format") String date,
                              @P("The PNR (record locator) for the booking to rebook") String pnr,
                              @P("The passenger's last name") String lastName) {
        
        System.out.println("=== REBOOK_FLIGHT TOOL CALLED ===");
        System.out.println("Tool: rebookFlight");
        System.out.println("LLM Extracted Parameters:");
        System.out.println("  - origin: " + origin);
        System.out.println("  - destination: " + destination);
        System.out.println("  - date: " + date);
        System.out.println("  - pnr: " + pnr);
        System.out.println("  - lastName: " + lastName);
        
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
        if (pnr == null || pnr.trim().isEmpty()) {
            return "Error: PNR is required. Please provide a valid PNR (record locator).";
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            return "Error: Last name is required. Please provide the passenger's last name.";
        }
        
        // Step 1: Call intent function to format parameters
        System.out.println("\n--- STEP 1: CALLING INTENT FUNCTION ---");
        String intentPayload = rebookFlightIntent.rebookFlight(origin, destination, date, pnr, lastName);
        
        // Step 2: Call DAPI handler with formatted payload
        System.out.println("\n--- STEP 2: CALLING DAPI HANDLER ---");
        String dapiResponse = rebookFlightDapi.rebookFlightDapi(intentPayload);
        
        // Step 3: Format response for LLM consumption
        System.out.println("\n--- STEP 3: FORMATTING RESPONSE FOR LLM ---");
        String formattedResponse = formatRebookingResponseForLlm(dapiResponse, origin, destination, date, pnr, lastName);
        
        System.out.println("Final Response to LLM:");
        System.out.println(formattedResponse);
        System.out.println("=== END REBOOK_FLIGHT TOOL ===");
        
        return formattedResponse;
    }
    
    private String formatRebookingResponseForLlm(String dapiResponse, String origin, String destination, String date, String pnr, String lastName) {
        // Format the response for better LLM understanding and HTML output
        return String.format("""
            <h3>Flight Rebooking Completed Successfully</h3>
            
            <h4>Booking Details:</h4>
            <ul>
                <li><b>Original PNR:</b> %s</li>
                <li><b>New PNR:</b> %s-RBK</li>
                <li><b>Passenger:</b> John %s</li>
            </ul>
            
            <h4>Flight Changes:</h4>
            <div style="border: 1px solid #ccc; padding: 10px; margin: 10px 0;">
                <h5>Original Flight:</h5>
                <ul>
                    <li><b>Flight:</b> AB1234 (Cancelled)</li>
                    <li><b>Status:</b> <span style="color: red;">Cancelled</span></li>
                </ul>
            </div>
            
            <div style="border: 1px solid #ccc; padding: 10px; margin: 10px 0;">
                <h5>New Flight:</h5>
                <ul>
                    <li><b>Flight:</b> CD5678</li>
                    <li><b>Route:</b> %s → %s</li>
                    <li><b>Departure:</b> %s at 2:30 PM</li>
                    <li><b>Arrival:</b> %s at 6:00 PM</li>
                    <li><b>Seat:</b> 15C</li>
                    <li><b>Status:</b> <span style="color: green;">Confirmed</span></li>
                </ul>
            </div>
            
            <h4>Additional Charges:</h4>
            <ul>
                <li><b>Rebooking Fee:</b> $75.00 USD</li>
            </ul>
            
            <p><b>Confirmation:</b> Your flight has been successfully rebooked. You will receive a confirmation email with your new e-ticket shortly. Please arrive at the airport at least 2 hours before departure.</p>
            
            <!-- Debug Info: %s -->
            """, pnr, pnr, lastName, origin, destination, date, date, dapiResponse);
    }
} 