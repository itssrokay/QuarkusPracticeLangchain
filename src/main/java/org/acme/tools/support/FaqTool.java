package org.acme.tools.support;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FaqTool {

    @Tool("Answer frequently asked questions on various airline topics and provide helpful information.")
    public String faq(@P("The topic or question category (travel documents, seat selection, food, wifi, etc.)") String topic) {
        
        System.out.println("=== FAQ TOOL CALLED ===");
        System.out.println("Tool: faq");
        System.out.println("LLM Extracted Parameters:");
        System.out.println("  - topic: " + topic);
        
        // Validate input parameters
        if (topic == null || topic.trim().isEmpty()) {
            return "Error: Topic is required. Please specify what you'd like to know about.";
        }
        
        // Generate FAQ response based on topic
        String faqResponse = generateFaqResponse(topic);
        
        System.out.println("FAQ Response:");
        System.out.println(faqResponse);
        System.out.println("=== END FAQ TOOL ===");
        
        return faqResponse;
    }
    
    private String generateFaqResponse(String topic) {
        return switch (topic.toLowerCase().trim()) {
            case "travel documents", "passport", "id", "documents" -> """
                <h3>Travel Documents FAQ</h3>
                
                <h4>Q: What documents do I need for domestic flights?</h4>
                <p><b>A:</b> A government-issued photo ID such as driver's license, state ID, or passport. The ID must be current and not expired.</p>
                
                <h4>Q: What documents do I need for international flights?</h4>
                <p><b>A:</b> A valid passport is required. Some destinations also require a visa. Check visa requirements at least 6 weeks before travel.</p>
                
                <h4>Q: Can I travel with an expired ID?</h4>
                <p><b>A:</b> No, expired IDs are not accepted. However, if your ID expired after March 1, 2020, TSA may accept it through December 31, 2024 (check current TSA guidelines).</p>
                
                <h4>Q: What if I forgot my ID?</h4>
                <p><b>A:</b> Arrive early at the airport. TSA can verify your identity through additional screening, but this process takes extra time.</p>
                
                <h4>Q: Do children need ID?</h4>
                <p><b>A:</b> Children under 18 don't need ID for domestic flights when traveling with an adult. For international travel, all passengers need a passport regardless of age.</p>
                """;
                
            case "seat selection", "seats", "seating" -> """
                <h3>Seat Selection FAQ</h3>
                
                <h4>Q: When can I select my seat?</h4>
                <p><b>A:</b> Seat selection is available at booking, during online check-in (24 hours before), or at the airport. Some seats may have additional fees.</p>
                
                <h4>Q: Are there fees for seat selection?</h4>
                <p><b>A:</b> Basic seats are free. Premium seats (extra legroom, preferred location) range from $15-$75 depending on route and seat type.</p>
                
                <h4>Q: What if I don't select a seat?</h4>
                <p><b>A:</b> A seat will be automatically assigned at check-in. You may not sit together with your travel companions if you don't pre-select seats.</p>
                
                <h4>Q: Can I change my seat after booking?</h4>
                <p><b>A:</b> Yes, you can change seats online, through the app, or at the airport (subject to availability and fees).</p>
                
                <h4>Q: What are the different seat types?</h4>
                <ul>
                    <li><b>Basic:</b> Standard seats (free)</li>
                    <li><b>Preferred:</b> Front of cabin, near exit ($15-$35)</li>
                    <li><b>Extra Legroom:</b> Emergency exit rows ($25-$75)</li>
                    <li><b>Premium:</b> Business/First class seats</li>
                </ul>
                """;
                
            case "food", "meals", "dining", "snacks" -> """
                <h3>Food & Dining FAQ</h3>
                
                <h4>Q: Is food included on flights?</h4>
                <p><b>A:</b> Complimentary snacks and beverages on flights over 2 hours. Full meals on flights over 4 hours or international flights.</p>
                
                <h4>Q: Can I pre-order special meals?</h4>
                <p><b>A:</b> Yes, special dietary meals must be ordered at least 24 hours before departure. Options include vegetarian, vegan, gluten-free, kosher, and more.</p>
                
                <h4>Q: Can I bring my own food?</h4>
                <p><b>A:</b> Yes, you can bring solid food items through security. Liquids must follow the 3-1-1 rule (3.4 oz containers, 1 quart bag, 1 bag per passenger).</p>
                
                <h4>Q: What food is available for purchase?</h4>
                <p><b>A:</b> Snack boxes ($8-$12), sandwiches ($10-$15), and alcoholic beverages ($7-$12) are available on most flights.</p>
                
                <h4>Q: Are there food options for children?</h4>
                <p><b>A:</b> Yes, children's meals are available on request. We also offer kid-friendly snacks and beverages.</p>
                """;
                
            case "wifi", "internet", "connectivity" -> """
                <h3>WiFi & Connectivity FAQ</h3>
                
                <h4>Q: Is WiFi available on flights?</h4>
                <p><b>A:</b> WiFi is available on most domestic flights and select international routes. Coverage varies by aircraft type.</p>
                
                <h4>Q: How much does WiFi cost?</h4>
                <ul>
                    <li><b>Messaging:</b> Free (iMessage, WhatsApp, Facebook Messenger)</li>
                    <li><b>Basic Internet:</b> $8 for flights under 3 hours, $12 for longer flights</li>
                    <li><b>Streaming:</b> $15-$20 for high-speed internet</li>
                </ul>
                
                <h4>Q: Can I use my phone during flight?</h4>
                <p><b>A:</b> Phones must be in airplane mode during flight. You can use WiFi for internet access and messaging, but voice calls are not permitted.</p>
                
                <h4>Q: What devices can connect to WiFi?</h4>
                <p><b>A:</b> Smartphones, tablets, laptops, and other WiFi-enabled devices. Each passenger can connect multiple devices with one WiFi purchase.</p>
                
                <h4>Q: Is WiFi fast enough for video calls?</h4>
                <p><b>A:</b> Basic WiFi supports messaging and light browsing. Streaming WiFi supports video calls and entertainment, but quality may vary.</p>
                """;
                
            case "pets", "animals", "service animals" -> """
                <h3>Pet Travel FAQ</h3>
                
                <h4>Q: Can I travel with my pet?</h4>
                <p><b>A:</b> Yes, pets can travel in the cabin (small pets) or cargo hold (larger pets). Service animals are always welcome in the cabin.</p>
                
                <h4>Q: What are the cabin pet requirements?</h4>
                <ul>
                    <li><b>Size:</b> Pet + carrier must fit under the seat (18" x 11" x 11")</li>
                    <li><b>Weight:</b> Combined weight under 20 lbs</li>
                    <li><b>Fee:</b> $125 each way for domestic flights</li>
                    <li><b>Limit:</b> One pet per passenger, maximum 4 pets per flight</li>
                </ul>
                
                <h4>Q: What documents do pets need?</h4>
                <p><b>A:</b> Health certificate from veterinarian (within 10 days of travel), vaccination records, and any required permits for destination.</p>
                
                <h4>Q: Are service animals different from pets?</h4>
                <p><b>A:</b> Yes, trained service animals fly free and don't need to be in carriers. Emotional support animals are treated as pets and require fees.</p>
                
                <h4>Q: Can pets travel internationally?</h4>
                <p><b>A:</b> Yes, but requirements vary by country. Some destinations require quarantine, additional vaccinations, or import permits. Plan well in advance.</p>
                """;
                
            default -> String.format("""
                <h3>Frequently Asked Questions</h3>
                
                <p>I can help answer questions about these popular topics:</p>
                <ul>
                    <li><b>Travel Documents</b> - ID requirements, passports, visas</li>
                    <li><b>Seat Selection</b> - How to choose seats, fees, seat types</li>
                    <li><b>Food & Dining</b> - Meals, special diets, bringing food</li>
                    <li><b>WiFi & Connectivity</b> - Internet access, pricing, device usage</li>
                    <li><b>Pet Travel</b> - Bringing pets, service animals, requirements</li>
                </ul>
                
                <p>You asked about: <b>%s</b></p>
                
                <h4>Common Questions:</h4>
                <ul>
                    <li>What can I bring in carry-on luggage?</li>
                    <li>How early should I arrive at the airport?</li>
                    <li>What if my flight is delayed or cancelled?</li>
                    <li>How do I add special assistance to my booking?</li>
                    <li>Can I upgrade my seat or class of service?</li>
                </ul>
                
                <p>For specific questions not covered here, please contact customer service at 1-800-AIRLINE.</p>
                """, topic);
        };
    }
} 