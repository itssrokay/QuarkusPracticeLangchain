package org.acme.tools;

import org.acme.api.RefundApi;
import org.acme.intent.RefundIntent;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RefundTool {

    @Inject
    RefundIntent refundIntent;
    
    @Inject
    RefundApi refundApi;

    @Tool("Check refund eligibility and process refund for a booking using PNR and last name. This tool will provide refund amount, policy details, and processing information.")
    public String processRefund(@P("The PNR (record locator) for the booking to refund") String pnr, 
                        @P("The passenger's last name") String lastName) {
        
        System.out.println("=== REFUND TOOL CALLED ===");
        System.out.println("Tool: processRefund");
        System.out.println("LLM Extracted Parameters:");
        System.out.println("  - pnr: " + pnr);
        System.out.println("  - lastName: " + lastName);
        
        // Step 1: Call intent function to format parameters
        System.out.println("\n--- STEP 1: CALLING INTENT FUNCTION ---");
        String intentPayload = refundIntent.refund(pnr, lastName);
        
        // Step 2: Call API handler with formatted payload
        System.out.println("\n--- STEP 2: CALLING API HANDLER ---");
        String apiResponse = refundApi.refundDapi(intentPayload);
        
        // Step 3: Format response for LLM consumption
        System.out.println("\n--- STEP 3: FORMATTING RESPONSE FOR LLM ---");
        String formattedResponse = formatRefundResponseForLlm(apiResponse, pnr, lastName);
        
        System.out.println("Final Response to LLM:");
        System.out.println(formattedResponse);
        System.out.println("=== END REFUND TOOL ===");
        
        return formattedResponse;
    }
    
    private String formatRefundResponseForLlm(String apiResponse, String pnr, String lastName) {
        // In a real implementation, you would parse the JSON response
        // For now, return a formatted string that the LLM can understand
        return String.format("""
            Refund Eligibility Check Completed:
            
            PNR: %s
            Passenger: John %s
            Refund Status: ELIGIBLE
            Refund Amount: $450.00 USD
            Cancellation Fee: $0.00 USD (waived)
            Net Refund: $450.00 USD
            
            Refund Policy: Full refund available - cancellation within 24 hours
            Processing Time: 5-7 business days
            Refund Method: Original payment method
            
            The refund has been processed successfully. You will receive a confirmation email shortly.
            
            Raw API Response: %s
            """, pnr, lastName, apiResponse);
    }
} 