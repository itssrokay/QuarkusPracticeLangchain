package org.acme.tools;

import org.acme.api.RetrieveServicesApi;
import org.acme.intent.RetrieveServicesIntent;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AirlineServiceTool {

    @Inject
    RetrieveServicesIntent retrieveServicesIntent;
    
    @Inject
    RetrieveServicesApi retrieveServicesApi;

    @Tool("Retrieve available airline services for a booking using PNR. This tool will provide all additional services that can be purchased or added to the booking.")
    public String retrieveServices(@P("The PNR (record locator) for which to retrieve available services") String pnr) {
        
        System.out.println("=== AIRLINE SERVICE TOOL CALLED ===");
        System.out.println("Tool: retrieveServices");
        System.out.println("LLM Extracted Parameters:");
        System.out.println("  - pnr: " + pnr);
        
        // Step 1: Call intent function to format parameters
        System.out.println("\n--- STEP 1: CALLING INTENT FUNCTION ---");
        String intentPayload = retrieveServicesIntent.retrieveServices(pnr);
        
        // Step 2: Call API handler with formatted payload
        System.out.println("\n--- STEP 2: CALLING API HANDLER ---");
        String apiResponse = retrieveServicesApi.retrieveServicesDapi(intentPayload);
        
        // Step 3: Format response for LLM consumption
        System.out.println("\n--- STEP 3: FORMATTING RESPONSE FOR LLM ---");
        String formattedResponse = formatServicesResponseForLlm(apiResponse, pnr);
        
        System.out.println("Final Response to LLM:");
        System.out.println(formattedResponse);
        System.out.println("=== END AIRLINE SERVICE TOOL ===");
        
        return formattedResponse;
    }
    
    private String formatServicesResponseForLlm(String apiResponse, String pnr) {
        // In a real implementation, you would parse the JSON response
        // For now, return a formatted string that the LLM can understand
        return String.format("""
            Available Services for PNR %s:
            
            1. Extra Baggage (SVC001) - $50.00 USD
               Additional 23kg baggage allowance
               Flight: AB1234
            
            2. Seat Selection (SVC002) - $35.00 USD
               Premium seat selection with extra legroom
               Flight: AB1234
            
            3. In-flight Meal (SVC003) - $25.00 USD
               Special dietary meal selection
               Flight: AB1234
            
            4. Priority Boarding (SVC004) - $15.00 USD
               Board the aircraft before general boarding
               Flight: AB1234
            
            Total Services Available: 4
            
            Usage Instructions: These services can be added to your booking. Contact customer service with the Service ID to add any of these services to your booking.
            
            Raw API Response: %s
            """, pnr, apiResponse);
    }
} 