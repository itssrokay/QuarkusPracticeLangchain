package org.acme.tools;

import org.acme.services.ServiceService;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ServiceTool {

    @Inject
    ServiceService serviceService;

    @Tool("Get available services for a booking using PNR. This tool will provide all additional services that can be purchased or added to the booking.")
    public String getBookingServices(@P("The PNR (record locator) for which to retrieve available services") String pnr) {
        System.out.println("=== SERVICE TOOL CALLED ===");
        System.out.println("PNR parameter received: " + pnr);
        
        // The LLM handles parameter extraction, we just call the service
        String result = serviceService.getServicesForPnr(pnr);
        
        System.out.println("=== SERVICE TOOL RESPONSE ===");
        System.out.println("Response length: " + result.length() + " characters");
        System.out.println("=== END SERVICE TOOL ===");
        
        return result;
    }
}