package org.acme.tools.support;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContactInfoTool {

    @Tool("Provide contact information for different airline departments and services.")
    public String contactInfo(@P("The department or service type (customer service, baggage, reservations, etc.)") String department) {
        
        System.out.println("=== CONTACT_INFO TOOL CALLED ===");
        System.out.println("Tool: contactInfo");
        System.out.println("LLM Extracted Parameters:");
        System.out.println("  - department: " + department);
        
        // Validate input parameters
        if (department == null || department.trim().isEmpty()) {
            return "Error: Department is required. Please specify which department you need contact information for.";
        }
        
        // Generate contact information based on department
        String contactResponse = generateContactInfo(department);
        
        System.out.println("Contact Info Response:");
        System.out.println(contactResponse);
        System.out.println("=== END CONTACT_INFO TOOL ===");
        
        return contactResponse;
    }
    
    private String generateContactInfo(String department) {
        return switch (department.toLowerCase().trim()) {
            case "customer service", "general", "support" -> """
                <h3>Customer Service Contact Information</h3>
                
                <h4>📞 Phone Support:</h4>
                <ul>
                    <li><b>General Inquiries:</b> 1-800-AIRLINE (1-800-247-5463)</li>
                    <li><b>Hours:</b> 24/7, 365 days a year</li>
                    <li><b>International:</b> +1-555-123-4567</li>
                    <li><b>TTY/TDD:</b> 1-800-555-1212</li>
                </ul>
                
                <h4>💬 Digital Support:</h4>
                <ul>
                    <li><b>Live Chat:</b> Available on website 6 AM - 11 PM EST</li>
                    <li><b>Email:</b> support@airline.com</li>
                    <li><b>Social Media:</b> @AirlineSupport on Twitter</li>
                    <li><b>Mobile App:</b> In-app messaging available</li>
                </ul>
                
                <h4>⏱️ Best Times to Call:</h4>
                <ul>
                    <li><b>Shortest Wait:</b> Tuesday-Thursday, 6 AM - 8 AM EST</li>
                    <li><b>Avoid:</b> Monday mornings and Friday afternoons</li>
                    <li><b>Peak Times:</b> Holidays and severe weather days</li>
                </ul>
                """;
                
            case "baggage", "lost baggage", "luggage" -> """
                <h3>Baggage Services Contact Information</h3>
                
                <h4>📞 Baggage Support:</h4>
                <ul>
                    <li><b>Baggage Hotline:</b> 1-800-BAG-HELP (1-800-224-4357)</li>
                    <li><b>Hours:</b> 24/7</li>
                    <li><b>Lost Baggage:</b> Report immediately at airport or call hotline</li>
                    <li><b>Delayed Baggage:</b> Track online with reference number</li>
                </ul>
                
                <h4>🌐 Online Services:</h4>
                <ul>
                    <li><b>Baggage Tracker:</b> airline.com/baggage-tracker</li>
                    <li><b>File Claim:</b> airline.com/baggage-claim</li>
                    <li><b>Reimbursement:</b> Submit receipts for essential items</li>
                </ul>
                
                <h4>🏢 Airport Baggage Offices:</h4>
                <ul>
                    <li><b>Location:</b> Near baggage claim areas</li>
                    <li><b>Hours:</b> Vary by airport (typically 5 AM - 11 PM)</li>
                    <li><b>Services:</b> Report issues, file claims, track bags</li>
                </ul>
                """;
                
            case "reservations", "booking", "tickets" -> """
                <h3>Reservations & Booking Contact Information</h3>
                
                <h4>📞 Reservations:</h4>
                <ul>
                    <li><b>New Bookings:</b> 1-800-FLY-HERE (1-800-359-4373)</li>
                    <li><b>Changes/Cancellations:</b> 1-800-AIRLINE (1-800-247-5463)</li>
                    <li><b>Group Bookings (10+):</b> 1-800-GROUP-FLY</li>
                    <li><b>Hours:</b> 5 AM - 12 AM EST daily</li>
                </ul>
                
                <h4>💻 Online Booking:</h4>
                <ul>
                    <li><b>Website:</b> airline.com</li>
                    <li><b>Mobile App:</b> Available on iOS and Android</li>
                    <li><b>Manage Booking:</b> airline.com/manage-booking</li>
                    <li><b>Check-in:</b> Online 24 hours before departure</li>
                </ul>
                
                <h4>🎫 Special Services:</h4>
                <ul>
                    <li><b>Accessibility:</b> 1-800-ASSIST-ME</li>
                    <li><b>Unaccompanied Minors:</b> 1-800-KIDS-FLY</li>
                    <li><b>Pet Travel:</b> 1-800-PET-TRAVEL</li>
                </ul>
                """;
                
            case "frequent flyer", "loyalty", "miles" -> """
                <h3>Frequent Flyer Program Contact Information</h3>
                
                <h4>📞 SkyMiles Support:</h4>
                <ul>
                    <li><b>Member Services:</b> 1-800-SKYMILES (1-800-759-6453)</li>
                    <li><b>Elite Support:</b> 1-800-ELITE-FLY (priority line)</li>
                    <li><b>Hours:</b> 6 AM - 11 PM EST daily</li>
                    <li><b>International:</b> +1-555-MILES-01</li>
                </ul>
                
                <h4>💳 Credit Card Support:</h4>
                <ul>
                    <li><b>Airline Credit Card:</b> 1-800-CARD-HELP</li>
                    <li><b>Hours:</b> 24/7</li>
                    <li><b>Benefits Inquiry:</b> 1-800-BENEFITS</li>
                </ul>
                
                <h4>🌐 Online Account:</h4>
                <ul>
                    <li><b>Account Management:</b> airline.com/skymiles</li>
                    <li><b>Redeem Miles:</b> Book award flights online</li>
                    <li><b>Transfer Miles:</b> To family and friends</li>
                    <li><b>Status Tracker:</b> Monitor elite qualification progress</li>
                </ul>
                """;
                
            case "corporate", "business", "travel agents" -> """
                <h3>Corporate & Business Travel Contact Information</h3>
                
                <h4>📞 Corporate Sales:</h4>
                <ul>
                    <li><b>Business Travel:</b> 1-800-BIZ-TRAVEL</li>
                    <li><b>Corporate Contracts:</b> 1-800-CORPORATE</li>
                    <li><b>Hours:</b> Monday-Friday, 8 AM - 6 PM EST</li>
                    <li><b>Account Management:</b> Dedicated representatives</li>
                </ul>
                
                <h4>🏢 Travel Agencies:</h4>
                <ul>
                    <li><b>Agent Support:</b> 1-800-AGENT-HELP</li>
                    <li><b>GDS Support:</b> Technical assistance for booking systems</li>
                    <li><b>Training:</b> Agent education programs</li>
                </ul>
                
                <h4>📊 Corporate Tools:</h4>
                <ul>
                    <li><b>Online Booking Tool:</b> corporate.airline.com</li>
                    <li><b>Reporting:</b> Travel analytics and expense tracking</li>
                    <li><b>Policy Management:</b> Enforce corporate travel policies</li>
                </ul>
                """;
                
            default -> String.format("""
                <h3>General Contact Directory</h3>
                
                <p>I can provide contact information for the following departments:</p>
                <ul>
                    <li><b>Customer Service</b> - General inquiries and support</li>
                    <li><b>Baggage Services</b> - Lost, delayed, or damaged baggage</li>
                    <li><b>Reservations</b> - Booking, changes, and cancellations</li>
                    <li><b>Frequent Flyer</b> - Loyalty program and miles</li>
                    <li><b>Corporate Travel</b> - Business and group bookings</li>
                </ul>
                
                <p>You asked about: <b>%s</b></p>
                
                <h4>📞 Main Customer Service:</h4>
                <ul>
                    <li><b>Phone:</b> 1-800-AIRLINE (1-800-247-5463)</li>
                    <li><b>Hours:</b> 24/7</li>
                    <li><b>Website:</b> airline.com</li>
                    <li><b>Email:</b> support@airline.com</li>
                </ul>
                
                <p>Please specify which department you need, or use the main customer service number for general assistance.</p>
                """, department);
        };
    }
} 