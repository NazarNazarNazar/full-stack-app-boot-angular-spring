package com.example.learning.rest;

import com.example.learning.FullStackAppBootAngularSpringApplication;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FullStackAppBootAngularSpringApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationResourceTest {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = port;
        RestAssured.basePath = ResourceConstants.ROOM_RESERVATION_V1;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void test() {
        given().when().get("/" + 1).then()
                .statusCode(200);
    }

    @Test
    public void getAvailableRooms() {
    }

    @Test
    public void getRoomById() {
    }

    @Test
    public void createReservation() {
    }

    @Test
    public void updateReservation() {
    }

    @Test
    public void deleteReservation() {
    }
}