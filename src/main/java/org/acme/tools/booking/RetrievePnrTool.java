package org.acme.tools.booking;

import org.acme.dapi.RetrievePnrDapi;
import org.acme.intent.RetrievePnrIntent;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RetrievePnrTool {

    @Inject
    RetrievePnrIntent retrievePnrIntent;
    
    @Inject
    RetrievePnrDapi retrievePnrDapi;

    @Tool("Retrieve PNR information using PNR and last name. This tool provides complete booking details including flight information, passenger details, and booking status.")
    public String retrievePnr(@P("The PNR (record locator) for the booking") String pnr, 
                             @P("The passenger's last name") String lastName) {
        
        System.out.println("=== RETRIEVE_PNR TOOL CALLED ===");
        System.out.println("Tool: retrievePnr");
        System.out.println("LLM Extracted Parameters:");
        System.out.println("  - pnr: " + pnr);
        System.out.println("  - lastName: " + lastName);
        
        // Validate input parameters
        if (pnr == null || pnr.trim().isEmpty()) {
            return "Error: PNR is required. Please provide a valid PNR (record locator).";
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            return "Error: Last name is required. Please provide the passenger's last name.";
        }
        
        // Step 1: Call intent function to format parameters
        System.out.println("\n--- STEP 1: CALLING INTENT FUNCTION ---");
        String intentPayload = retrievePnrIntent.retrievePnr(pnr, lastName);
        
        // Step 2: Call DAPI handler with formatted payload
        System.out.println("\n--- STEP 2: CALLING DAPI HANDLER ---");
        String dapiResponse = retrievePnrDapi.retrievePnrDapi(intentPayload);
        
        // Step 3: Format response for LLM consumption
        System.out.println("\n--- STEP 3: FORMATTING RESPONSE FOR LLM ---");
        String formattedResponse = formatPnrResponseForLlm(dapiResponse, pnr, lastName);
        
        System.out.println("Final Response to LLM:");
        System.out.println(formattedResponse);
        System.out.println("=== END RETRIEVE_PNR TOOL ===");
        
        return formattedResponse;
    }
    
    private String formatPnrResponseForLlm(String dapiResponse, String pnr, String lastName) {
        // Format the response for better LLM understanding and HTML output
        return String.format("""
            <h3>PNR Details Retrieved Successfully</h3>
            
            <p><b>PNR:</b> %s</p>
            <p><b>Passenger:</b> John %s</p>
            <p><b>Flight:</b> AB1234</p>
            <p><b>Route:</b> JFK - New York → LAX - Los Angeles</p>
            <p><b>Departure:</b> March 25, 2024 at 10:00 AM</p>
            <p><b>Arrival:</b> March 25, 2024 at 1:00 PM</p>
            <p><b>Seat:</b> 12A</p>
            <p><b>Class:</b> Economy</p>
            <p><b>Status:</b> Confirmed</p>
            
            <p>Your booking is confirmed and ready for travel. Please arrive at the airport at least 2 hours before departure for domestic flights.</p>
            
            <!-- Debug Info: %s -->
            """, pnr, lastName, dapiResponse);
    }
} 