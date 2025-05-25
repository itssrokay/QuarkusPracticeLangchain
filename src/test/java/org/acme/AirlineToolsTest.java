package org.acme;

import org.acme.tools.AirlineServiceTool;
import org.acme.tools.PnrTool;
import org.acme.tools.RebookingTool;
import org.acme.tools.RefundTool;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class AirlineToolsTest {

    @Inject
    PnrTool pnrTool;
    
    @Inject
    RefundTool refundTool;
    
    @Inject
    RebookingTool rebookingTool;
    
    @Inject
    AirlineServiceTool airlineServiceTool;

    @Test
    public void testPnrToolFlow() {
        System.out.println("\n=== TESTING PNR TOOL FLOW ===");
        
        String result = pnrTool.retrievePnr("BS8ND5", "WICK");
        
        assertNotNull(result);
        assertTrue(result.contains("PNR Details Retrieved Successfully"));
        assertTrue(result.contains("BS8ND5"));
        assertTrue(result.contains("WICK"));
        assertTrue(result.contains("Flight: AB1234"));
        assertTrue(result.contains("Raw API Response"));
        
        System.out.println("PNR Tool Test Result:");
        System.out.println(result);
        System.out.println("=== PNR TOOL TEST COMPLETED ===\n");
    }

    @Test
    public void testRefundToolFlow() {
        System.out.println("\n=== TESTING REFUND TOOL FLOW ===");
        
        String result = refundTool.refund("BS8ND5", "WICK");
        
        assertNotNull(result);
        assertTrue(result.contains("Refund Eligibility Check Completed"));
        assertTrue(result.contains("BS8ND5"));
        assertTrue(result.contains("WICK"));
        assertTrue(result.contains("ELIGIBLE"));
        assertTrue(result.contains("$450.00 USD"));
        assertTrue(result.contains("Raw API Response"));
        
        System.out.println("Refund Tool Test Result:");
        System.out.println(result);
        System.out.println("=== REFUND TOOL TEST COMPLETED ===\n");
    }

    @Test
    public void testRebookingToolFlow() {
        System.out.println("\n=== TESTING REBOOKING TOOL FLOW ===");
        
        String result = rebookingTool.rebooking("BS8ND5", "WICK", "2024-04-15", "CD5678");
        
        assertNotNull(result);
        assertTrue(result.contains("Rebooking Completed Successfully"));
        assertTrue(result.contains("BS8ND5"));
        assertTrue(result.contains("WICK"));
        assertTrue(result.contains("2024-04-15"));
        assertTrue(result.contains("CD5678"));
        assertTrue(result.contains("BS8ND5-NEW"));
        assertTrue(result.contains("Raw API Response"));
        
        System.out.println("Rebooking Tool Test Result:");
        System.out.println(result);
        System.out.println("=== REBOOKING TOOL TEST COMPLETED ===\n");
    }

    @Test
    public void testAirlineServiceToolFlow() {
        System.out.println("\n=== TESTING AIRLINE SERVICE TOOL FLOW ===");
        
        String result = airlineServiceTool.retrieveServices("BS8ND5");
        
        assertNotNull(result);
        assertTrue(result.contains("Available Services for PNR BS8ND5"));
        assertTrue(result.contains("Extra Baggage"));
        assertTrue(result.contains("Seat Selection"));
        assertTrue(result.contains("In-flight Meal"));
        assertTrue(result.contains("Priority Boarding"));
        assertTrue(result.contains("SVC001"));
        assertTrue(result.contains("Raw API Response"));
        
        System.out.println("Airline Service Tool Test Result:");
        System.out.println(result);
        System.out.println("=== AIRLINE SERVICE TOOL TEST COMPLETED ===\n");
    }

    @Test
    public void testAllToolsWithDifferentParameters() {
        System.out.println("\n=== TESTING ALL TOOLS WITH DIFFERENT PARAMETERS ===");
        
        // Test PNR tool with different parameters
        String pnrResult = pnrTool.retrievePnr("6X67GH", "SMITH");
        assertTrue(pnrResult.contains("6X67GH"));
        assertTrue(pnrResult.contains("SMITH"));
        
        // Test refund tool with different parameters
        String refundResult = refundTool.refund("HUG67D", "JONES");
        assertTrue(refundResult.contains("HUG67D"));
        assertTrue(refundResult.contains("JONES"));
        
        // Test rebooking tool with different parameters
        String rebookingResult = rebookingTool.rebooking("6X67GH", "SMITH", "2024-05-20", "EF9012");
        assertTrue(rebookingResult.contains("6X67GH"));
        assertTrue(rebookingResult.contains("SMITH"));
        assertTrue(rebookingResult.contains("2024-05-20"));
        assertTrue(rebookingResult.contains("EF9012"));
        
        // Test service tool with different PNR
        String serviceResult = airlineServiceTool.retrieveServices("HUG67D");
        assertTrue(serviceResult.contains("HUG67D"));
        
        System.out.println("All tools tested with different parameters successfully");
        System.out.println("=== ALL TOOLS TEST COMPLETED ===\n");
    }
} 