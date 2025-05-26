# Multi-Agent Airline System Testing Guide

## Overview
This guide provides comprehensive testing instructions for the multi-agent airline system built with Quarkus and LangChain4j. The system follows the Python Flask structure with specialized agents for different airline operations.

## System Architecture

### Agents
1. **ManagerAgent** - Central coordinator that routes queries to specialized agents
2. **BookingAgent** - Handles PNR retrieval, refunds, and booking services
3. **FlightSearchAgent** - Manages flight search, rebooking, and flight status
4. **CustomerServiceAgent** - Provides policies, contact info, and general FAQs

### Flow Structure (Similar to Python)
```
User Query → ManagerAgent → Specialized Agent → Intent Function → DAPI Handler → Mock Response → Formatted Response
```

## Starting the Application

```bash
# Start Quarkus in dev mode
./mvnw quarkus:dev

# Or using Maven wrapper
mvn quarkus:dev
```

The application will start on `http://localhost:8080`

## API Endpoints

### Main Chat Endpoints (Similar to Python Flask)

#### 1. POST /chat (Main endpoint like Python Flask)
```bash
curl -X POST http://localhost:8080/chat \
  -H "Content-Type: application/json" \
  -d '{
    "chatId": "test-123",
    "customerConversation": "I need to check my booking PNR BS8ND5 for Smith"
  }'
```

#### 2. GET /chat (Simple query endpoint)
```bash
curl "http://localhost:8080/chat?q=I want to search flights from New York to Los Angeles on 2024-12-15"
```

### Direct Agent Testing Endpoints

#### 3. BookingAgent Direct Test
```bash
curl "http://localhost:8080/chat/booking?q=Retrieve PNR BS8ND5 for passenger Smith"
```

#### 4. FlightSearchAgent Direct Test
```bash
curl "http://localhost:8080/chat/flight?q=Search flights from JFK to LAX on 2024-12-20"
```

#### 5. CustomerServiceAgent Direct Test
```bash
curl "http://localhost:8080/chat/service?q=What is the baggage policy?"
```

#### 6. Health Check
```bash
curl http://localhost:8080/chat/health
```

## Test Scenarios

### 1. Booking Operations (BookingAgent)

#### PNR Retrieval
```bash
# Test PNR retrieval
curl "http://localhost:8080/chat?q=I need to check my booking PNR BS8ND5 for Smith"

# Expected: Manager routes to BookingAgent → RetrievePnrTool → Intent → DAPI → Mock booking details
```

#### Refund Processing
```bash
# Test refund eligibility
curl "http://localhost:8080/chat?q=I want to cancel and get a refund for PNR 6X67GH, passenger name Johnson"

# Expected: Manager routes to BookingAgent → RefundTool → Intent → DAPI → Mock refund details
```

#### Services Retrieval
```bash
# Test available services
curl "http://localhost:8080/chat?q=What services are available for my booking HUG67D?"

# Expected: Manager routes to BookingAgent → RetrieveServicesTool → Intent → DAPI → Mock services list
```

### 2. Flight Operations (FlightSearchAgent)

#### Flight Search
```bash
# Test flight search
curl "http://localhost:8080/chat?q=I need flights from New York to Los Angeles on December 15, 2024"

# Expected: Manager routes to FlightSearchAgent → SearchFlightsTool → Intent → DAPI → Mock flight options
```

#### Flight Rebooking
```bash
# Test flight rebooking
curl "http://localhost:8080/chat?q=I need to rebook my flight PNR BS8ND5 for Smith to travel from JFK to LAX on 2024-12-25"

# Expected: Manager routes to FlightSearchAgent → RebookFlightTool → Intent → DAPI → Mock rebooking confirmation
```

#### Flight Status
```bash
# Test flight status
curl "http://localhost:8080/chat?q=What is the status of flight AB1234 on 2024-12-15?"

# Expected: Manager routes to FlightSearchAgent → FlightStatusTool → Mock status (ON TIME/DELAYED/CANCELLED)
```

### 3. Customer Service Operations (CustomerServiceAgent)

#### Policy Information
```bash
# Test baggage policy
curl "http://localhost:8080/chat?q=What is your baggage policy?"

# Test cancellation policy
curl "http://localhost:8080/chat?q=What are the cancellation rules?"

# Expected: Manager routes to CustomerServiceAgent → PolicyInfoTool → Mock policy details
```

#### Contact Information
```bash
# Test contact info
curl "http://localhost:8080/chat?q=I need customer service contact information"

# Test specific department
curl "http://localhost:8080/chat?q=How do I contact the baggage department?"

# Expected: Manager routes to CustomerServiceAgent → ContactInfoTool → Mock contact details
```

#### FAQ
```bash
# Test FAQ
curl "http://localhost:8080/chat?q=Can I travel with my pet?"

# Test travel documents FAQ
curl "http://localhost:8080/chat?q=What documents do I need for international travel?"

# Expected: Manager routes to CustomerServiceAgent → FaqTool → Mock FAQ responses
```

## Expected Debug Output

When testing, you should see comprehensive debug logging:

```
=== MULTI-AGENT CHAT ENDPOINT CALLED ===
Chat ID: test-123
Customer Conversation: I need to check my booking PNR BS8ND5 for Smith

=== MANAGER DELEGATING TO BOOKING AGENT ===
Manager Tool: delegateToBookingAgent
Query to delegate: I need to check my booking PNR BS8ND5 for Smith

=== RETRIEVE_PNR TOOL CALLED ===
Tool: retrievePnr
LLM Extracted Parameters:
  - pnr: BS8ND5
  - lastName: Smith

--- STEP 1: CALLING INTENT FUNCTION ---
=== RETRIEVE_PNR INTENT FUNCTION CALLED ===
Function: retrievePnr
Input Parameters:
  - pnr: BS8ND5
  - lastName: Smith

--- STEP 2: CALLING DAPI HANDLER ---
=== RETRIEVE_PNR DAPI HANDLER CALLED ===
DAPI Handler: retrievePnrDapi
Received Parameters: {"pnr":"BS8ND5","lastName":"Smith","timestamp":"2024-12-15T10:30:00Z"}

--- STEP 3: FORMATTING RESPONSE FOR LLM ---
Final Response to LLM: [HTML formatted booking details]
```

## Mock Data Available

### PNRs for Testing
- **BS8ND5** - Active booking (New York to Los Angeles)
- **6X67GH** - Refundable booking
- **HUG67D** - Booking with available services

### Flight Numbers for Status
- **AB1234** - ON TIME flight
- **CD5678** - DELAYED flight  
- **EF9012** - CANCELLED flight

### Routes for Search
- JFK ↔ LAX (New York ↔ Los Angeles)
- Any origin/destination will return mock flights

## Response Format

All responses follow this structure (similar to Python Flask):

```json
{
  "chatId": "test-123",
  "response": "<h3>Booking Details Retrieved</h3><p>...</p>",
  "responseType": "multi_agent",
  "dapi_response": null
}
```

## Troubleshooting

### Common Issues

1. **Agent Not Found Errors**
   - Ensure all agents are properly injected
   - Check that tools are correctly registered

2. **Tool Call Failures**
   - Verify tool method signatures match @Tool annotations
   - Check parameter validation in tools

3. **Empty/Null Responses**
   - All tools have validation to prevent null returns
   - Check debug logs for specific error messages

### Debug Tips

1. **Enable Verbose Logging**
   ```bash
   # Add to application.properties
   quarkus.log.level=DEBUG
   ```

2. **Check Tool Registration**
   - Verify @Tool annotations are present
   - Ensure tools are in @ApplicationScoped classes

3. **Validate Agent Injection**
   - Check that all agents are properly injected in ManagerAgent tools
   - Verify CDI is working correctly

## Performance Testing

### Load Testing
```bash
# Test multiple concurrent requests
for i in {1..10}; do
  curl "http://localhost:8080/chat?q=Test query $i" &
done
wait
```

### Memory Usage
Monitor memory usage during testing to ensure efficient resource utilization.

## Integration with Frontend

The API is designed to be compatible with frontend applications expecting the Python Flask structure:

```javascript
// POST request (like Python Flask)
fetch('http://localhost:8080/chat', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    chatId: 'frontend-123',
    customerConversation: 'User query here'
  })
})

// GET request (simple)
fetch('http://localhost:8080/chat?q=User query here')
```

## Success Criteria

✅ **Manager Agent Routing**: Queries are correctly routed to appropriate specialized agents  
✅ **Tool Execution**: All tools execute without errors and return formatted responses  
✅ **Debug Logging**: Comprehensive logging shows the complete flow  
✅ **Mock Data**: Realistic airline data is returned for all operations  
✅ **Error Handling**: Invalid inputs are handled gracefully  
✅ **Response Format**: HTML-formatted responses suitable for frontend display  

## Next Steps

1. **Add Authentication**: Implement user authentication for production use
2. **Real API Integration**: Replace mock DAPI handlers with real airline APIs
3. **Database Integration**: Store conversation history and user sessions
4. **Advanced Routing**: Implement more sophisticated query routing logic
5. **Monitoring**: Add metrics and monitoring for production deployment

This multi-agent system provides a robust foundation for airline customer service automation with clear separation of concerns and comprehensive testing capabilities. 