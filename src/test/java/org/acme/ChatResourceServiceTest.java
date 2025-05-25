package org.acme;

import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;

@QuarkusTest
public class ChatResourceServiceTest {

    @Test
    public void testChatWithServiceQuery() {
        // Test asking for services for a specific PNR
        given()
            .when()
            .get("/chat?q=What services are available for PNR BS8ND5?&sessionId=test123")
            .then()
            .statusCode(200)
            .body(containsString("Available services"))
            .body(containsString("Extra Baggage"))
            .body(containsString("Seat Selection"));
    }

    @Test
    public void testChatWithWheelchairQuery() {
        // Test asking specifically about wheelchair service
        given()
            .when()
            .get("/chat?q=Is wheelchair assistance available for PNR 6X67GH?&sessionId=test456")
            .then()
            .statusCode(200)
            .body(containsString("Wheelchair Assistance"));
    }

    @Test
    public void testChatWithInvalidPnr() {
        // Test asking for services for an invalid PNR
        given()
            .when()
            .get("/chat?q=What services are available for PNR INVALID123?&sessionId=test789")
            .then()
            .statusCode(200)
            .body(containsString("No additional services"));
    }

    @Test
    public void testChatWithMissingPnr() {
        // Test asking for services without providing a PNR
        given()
            .when()
            .get("/chat?q=What services are available for my booking?&sessionId=test999")
            .then()
            .statusCode(200);
        // The AI should ask for the PNR in this case
    }

    @Test
    public void testWeatherStillWorks() {
        // Test that weather functionality still works
        given()
            .when()
            .get("/chat?q=What's the weather in Berlin?&sessionId=weather123")
            .then()
            .statusCode(200)
            .body(containsString("Berlin"))
            .body(containsString("temperature"));
    }
}