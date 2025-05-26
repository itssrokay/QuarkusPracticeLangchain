package org.acme.tools.support;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PolicyInfoTool {

    @Tool("Get detailed information about airline policies including baggage, cancellation, refund, and other policies.")
    public String policyInfo(@P("The type of policy information needed (baggage, cancellation, refund, check-in, etc.)") String policyType) {
        
        System.out.println("=== POLICY_INFO TOOL CALLED ===");
        System.out.println("Tool: policyInfo");
        System.out.println("LLM Extracted Parameters:");
        System.out.println("  - policyType: " + policyType);
        
        // Validate input parameters
        if (policyType == null || policyType.trim().isEmpty()) {
            return "Error: Policy type is required. Please specify what policy information you need (baggage, cancellation, refund, etc.).";
        }
        
        // Generate policy information based on type
        String policyResponse = generatePolicyInfo(policyType);
        
        System.out.println("Policy Info Response:");
        System.out.println(policyResponse);
        System.out.println("=== END POLICY_INFO TOOL ===");
        
        return policyResponse;
    }
    
    private String generatePolicyInfo(String policyType) {
        return switch (policyType.toLowerCase().trim()) {
            case "baggage", "luggage" -> """
                <h3>Baggage Policy</h3>
                
                <h4>Carry-on Baggage:</h4>
                <ul>
                    <li><b>Size:</b> Maximum 22" x 14" x 9" (56cm x 36cm x 23cm)</li>
                    <li><b>Weight:</b> Maximum 15 lbs (7 kg)</li>
                    <li><b>Quantity:</b> 1 carry-on bag + 1 personal item per passenger</li>
                    <li><b>Personal Item:</b> Purse, laptop bag, or small backpack that fits under the seat</li>
                </ul>
                
                <h4>Checked Baggage:</h4>
                <ul>
                    <li><b>First Bag:</b> $30 USD (up to 50 lbs/23 kg)</li>
                    <li><b>Second Bag:</b> $40 USD (up to 50 lbs/23 kg)</li>
                    <li><b>Overweight (51-70 lbs):</b> Additional $100 USD</li>
                    <li><b>Oversized (63-80 inches):</b> Additional $150 USD</li>
                </ul>
                
                <h4>Prohibited Items:</h4>
                <ul>
                    <li>Liquids over 3.4 oz in carry-on</li>
                    <li>Sharp objects, weapons</li>
                    <li>Flammable materials</li>
                    <li>Lithium batteries in checked baggage</li>
                </ul>
                
                <p><b>Note:</b> Premium passengers may have different allowances. Check your ticket for specific details.</p>
                """;
                
            case "cancellation", "cancel" -> """
                <h3>Cancellation Policy</h3>
                
                <h4>24-Hour Cancellation:</h4>
                <ul>
                    <li><b>Free Cancellation:</b> Within 24 hours of booking (if booked 7+ days before departure)</li>
                    <li><b>Full Refund:</b> Refunded to original payment method</li>
                    <li><b>Processing Time:</b> 5-7 business days</li>
                </ul>
                
                <h4>Standard Cancellation:</h4>
                <ul>
                    <li><b>Basic Economy:</b> Non-refundable, no changes allowed</li>
                    <li><b>Main Cabin:</b> $200 USD cancellation fee + fare difference</li>
                    <li><b>Premium:</b> $100 USD cancellation fee + fare difference</li>
                    <li><b>Business/First:</b> Free changes, minimal cancellation fees</li>
                </ul>
                
                <h4>Weather/Airline Cancellations:</h4>
                <ul>
                    <li><b>Full Refund:</b> No fees for airline-initiated cancellations</li>
                    <li><b>Rebooking:</b> Free rebooking on next available flight</li>
                    <li><b>Accommodation:</b> Hotel provided for overnight delays (when applicable)</li>
                </ul>
                
                <p><b>Important:</b> Cancellation fees vary by fare type and destination. International flights may have different policies.</p>
                """;
                
            case "refund", "refunds" -> """
                <h3>Refund Policy</h3>
                
                <h4>Refundable Tickets:</h4>
                <ul>
                    <li><b>Full Refund:</b> Available for refundable fare types</li>
                    <li><b>Processing Time:</b> 7-20 business days</li>
                    <li><b>Method:</b> Refunded to original payment method</li>
                </ul>
                
                <h4>Non-Refundable Tickets:</h4>
                <ul>
                    <li><b>Taxes & Fees:</b> Government taxes may be refundable</li>
                    <li><b>Credit:</b> Flight credit for future travel (minus cancellation fee)</li>
                    <li><b>Expiration:</b> Flight credits valid for 12 months</li>
                </ul>
                
                <h4>Special Circumstances:</h4>
                <ul>
                    <li><b>Medical Emergency:</b> May qualify for refund with documentation</li>
                    <li><b>Military Duty:</b> Special provisions for active military</li>
                    <li><b>Death in Family:</b> Compassionate refund policy applies</li>
                </ul>
                
                <h4>Refund Process:</h4>
                <ol>
                    <li>Cancel your booking online or call customer service</li>
                    <li>Submit refund request with required documentation</li>
                    <li>Wait for processing (7-20 business days)</li>
                    <li>Receive refund confirmation email</li>
                </ol>
                """;
                
            case "check-in", "checkin" -> """
                <h3>Check-in Policy</h3>
                
                <h4>Online Check-in:</h4>
                <ul>
                    <li><b>Availability:</b> 24 hours to 1 hour before departure</li>
                    <li><b>Mobile App:</b> Download boarding pass to phone</li>
                    <li><b>Website:</b> Print boarding pass at home</li>
                    <li><b>Seat Selection:</b> Choose available seats during check-in</li>
                </ul>
                
                <h4>Airport Check-in:</h4>
                <ul>
                    <li><b>Kiosks:</b> Available 24 hours to 45 minutes before departure</li>
                    <li><b>Counter:</b> Opens 3 hours before departure</li>
                    <li><b>Bag Drop:</b> For passengers with checked luggage</li>
                    <li><b>Deadline:</b> 45 minutes before domestic, 60 minutes before international</li>
                </ul>
                
                <h4>Required Documents:</h4>
                <ul>
                    <li><b>Domestic:</b> Government-issued photo ID</li>
                    <li><b>International:</b> Valid passport and visa (if required)</li>
                    <li><b>Minors:</b> Birth certificate or passport</li>
                </ul>
                
                <p><b>Tip:</b> Check in online and arrive at airport with extra time for security screening.</p>
                """;
                
            default -> String.format("""
                <h3>General Airline Policies</h3>
                
                <p>I can provide information about the following policies:</p>
                <ul>
                    <li><b>Baggage Policy</b> - Carry-on and checked baggage rules</li>
                    <li><b>Cancellation Policy</b> - How to cancel flights and associated fees</li>
                    <li><b>Refund Policy</b> - Refund eligibility and processing</li>
                    <li><b>Check-in Policy</b> - Online and airport check-in procedures</li>
                </ul>
                
                <p>You asked about: <b>%s</b></p>
                <p>Please specify which policy you'd like to know more about, or contact customer service for specific policy questions.</p>
                
                <p><b>Customer Service:</b> 1-800-AIRLINE (24/7)</p>
                """, policyType);
        };
    }
} 