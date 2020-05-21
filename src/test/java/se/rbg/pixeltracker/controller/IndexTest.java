package se.rbg.pixeltracker.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class IndexTest {

  @Test
  @DisplayName("When a valid get call to image endpoint then success")
  void testValidGetImage() {
    given()
      .header("referer", "http://localhost:8080/test.html")
      .contentType("application/json")
      .when()
      .get("/api/v1/image")
      .then()
      .statusCode(200)
      .cookie("userId");
  }

  @Test
  @DisplayName("When a invalid get call to image endpoint then bad request")
  void testInvalidGetImage() {
    given()
      .contentType("application/json")
      .when()
      .get("/api/v1/image")
      .then()
      .statusCode(400);
  }

  @Test
  @DisplayName("When a invalid get call to image endpoint then bad request")
  void testGetUsers() {
    given()
      .contentType("application/json")
      .when()
      .get("/api/v1/tracks")
      .then()
      .statusCode(200);
  }
}