package org.acme.tools.booking;

import org.acme.dapi.RefundDapi;
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
    RefundDapi refundDapi;

    @Tool("Check refund eligibility and process refund for a booking using PNR and last name. This tool provides refund amount, policy details, and processing information.")
    public String refund(@P("The PNR (record locator) for the booking to refund") String pnr, 
                        @P("The passenger's last name") String lastName) {
        
        System.out.println("=== REFUND TOOL CALLED ===");
        System.out.println("Tool: refund");
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
        String intentPayload = refundIntent.refund(pnr, lastName);
        
        // Step 2: Call DAPI handler with formatted payload
        System.out.println("\n--- STEP 2: CALLING DAPI HANDLER ---");
        String dapiResponse = refundDapi.refundDapi(intentPayload);
        
        // Step 3: Format response for LLM consumption
        System.out.println("\n--- STEP 3: FORMATTING RESPONSE FOR LLM ---");
        String formattedResponse = formatRefundResponseForLlm(dapiResponse, pnr, lastName);
        
        System.out.println("Final Response to LLM:");
        System.out.println(formattedResponse);
        System.out.println("=== END REFUND TOOL ===");
        
        return formattedResponse;
    }
    
    private String formatRefundResponseForLlm(String dapiResponse, String pnr, String lastName) {
        // Format the response for better LLM understanding and HTML output
        return String.format("""
            <h3>Refund Processing Results</h3>
            
            <p><b>PNR:</b> %s</p>
            <p><b>Passenger:</b> John %s</p>
            <p><b>Refund Status:</b> <span style="color: green;">Eligible for Full Refund</span></p>
            
            <h4>Refund Details:</h4>
            <ul>
                <li><b>Refund Amount:</b> $450.00 USD</li>
                <li><b>Cancellation Fee:</b> $0.00 USD (waived - within 24 hours)</li>
                <li><b>Net Refund:</b> $450.00 USD</li>
            </ul>
            
            <h4>Processing Information:</h4>
            <ul>
                <li><b>Refund Policy:</b> Full refund available - cancellation within 24 hours</li>
                <li><b>Processing Time:</b> 5-7 business days</li>
                <li><b>Refund Method:</b> Original payment method</li>
            </ul>
            
            <p><b>Next Steps:</b> Your refund has been initiated and will be processed within 5-7 business days. You will receive a confirmation email shortly with the refund reference number.</p>
            
            <!-- Debug Info: %s -->
            """, pnr, lastName, dapiResponse);
    }
} 