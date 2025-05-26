package org.acme.tools.booking;

import org.acme.dapi.RetrieveServicesDapi;
import org.acme.intent.RetrieveServicesIntent;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RetrieveServicesTool {

    @Inject
    RetrieveServicesIntent retrieveServicesIntent;
    
    @Inject
    RetrieveServicesDapi retrieveServicesDapi;

    @Tool("Retrieve available airline services for a booking using PNR. This tool provides all additional services that can be purchased or added to the booking.")
    public String retrieveServices(@P("The PNR (record locator) for which to retrieve available services") String pnr) {
        
        System.out.println("=== RETRIEVE_SERVICES TOOL CALLED ===");
        System.out.println("Tool: retrieveServices");
        System.out.println("LLM Extracted Parameters:");
        System.out.println("  - pnr: " + pnr);
        
        // Validate input parameters
        if (pnr == null || pnr.trim().isEmpty()) {
            return "Error: PNR is required. Please provide a valid PNR (record locator).";
        }
        
        // Step 1: Call intent function to format parameters
        System.out.println("\n--- STEP 1: CALLING INTENT FUNCTION ---");
        String intentPayload = retrieveServicesIntent.retrieveServices(pnr);
        
        // Step 2: Call DAPI handler with formatted payload
        System.out.println("\n--- STEP 2: CALLING DAPI HANDLER ---");
        String dapiResponse = retrieveServicesDapi.retrieveServicesDapi(intentPayload);
        
        // Step 3: Format response for LLM consumption
        System.out.println("\n--- STEP 3: FORMATTING RESPONSE FOR LLM ---");
        String formattedResponse = formatServicesResponseForLlm(dapiResponse, pnr);
        
        System.out.println("Final Response to LLM:");
        System.out.println(formattedResponse);
        System.out.println("=== END RETRIEVE_SERVICES TOOL ===");
        
        return formattedResponse;
    }
    
    private String formatServicesResponseForLlm(String dapiResponse, String pnr) {
        // Format the response for better LLM understanding and HTML output
        return String.format("""
            <h3>Available Services for PNR %s</h3>
            
            <ul>
                <li><b>Extra Baggage (SVC001)</b> - $50.00 USD<br>
                    Additional 23kg baggage allowance</li>
                <li><b>Seat Selection (SVC002)</b> - $35.00 USD<br>
                    Premium seat selection with extra legroom</li>
                <li><b>In-flight Meal (SVC003)</b> - $25.00 USD<br>
                    Special dietary meal selection</li>
                <li><b>Priority Boarding (SVC004)</b> - $15.00 USD<br>
                    Board the aircraft before general boarding</li>
            </ul>
            
            <p><b>Total Services Available:</b> 4</p>
            
            <p><b>How to Add Services:</b> Contact customer service with the Service ID to add any of these services to your booking. Services can be added up to 24 hours before departure.</p>
            
            <!-- Debug Info: %s -->
            """, pnr, dapiResponse);
    }
} 