package org.acme;

import org.acme.tools.ServiceTool;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class ServiceToolTest {

    @Inject
    ServiceTool serviceTool;

    @Test
    public void testGetServicesForValidPnr() {
        // Test with a PNR that has services
        String result = serviceTool.getServices("BS8ND5");
        
        assertNotNull(result);
        assertTrue(result.contains("Available services for PNR BS8ND5"));
        assertTrue(result.contains("Extra Baggage"));
        assertTrue(result.contains("Seat Selection"));
        assertTrue(result.contains("In-flight Meal"));
        assertTrue(result.contains("Priority Boarding"));
        assertTrue(result.contains("Usage instructions"));
        
        System.out.println("=== TEST RESULT FOR BS8ND5 ===");
        System.out.println(result);
    }

    @Test
    public void testGetServicesForValidPnrWithWheelchair() {
        // Test with a PNR that has wheelchair service
        String result = serviceTool.getServices("6X67GH");
        
        assertNotNull(result);
        assertTrue(result.contains("Available services for PNR 6X67GH"));
        assertTrue(result.contains("Wheelchair Assistance"));
        assertTrue(result.contains("Complimentary"));
        assertTrue(result.contains("Pet Transport"));
        assertTrue(result.contains("Lounge Access"));
        
        System.out.println("=== TEST RESULT FOR 6X67GH ===");
        System.out.println(result);
    }

    @Test
    public void testGetServicesForInvalidPnr() {
        // Test with a PNR that doesn't exist
        String result = serviceTool.getServices("INVALID");
        
        assertNotNull(result);
        assertTrue(result.contains("No additional services are available for PNR: INVALID"));
        
        System.out.println("=== TEST RESULT FOR INVALID PNR ===");
        System.out.println(result);
    }

    @Test
    public void testGetServicesForLowercasePnr() {
        // Test that lowercase PNR works (should be converted to uppercase)
        String result = serviceTool.getServices("bs8nd5");
        
        assertNotNull(result);
        assertTrue(result.contains("Available services for PNR bs8nd5"));
        assertTrue(result.contains("Extra Baggage"));
        
        System.out.println("=== TEST RESULT FOR LOWERCASE PNR ===");
        System.out.println(result);
    }
} 