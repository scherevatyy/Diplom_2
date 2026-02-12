package ru.yandex.praktikum.tests;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class IngredientsTest extends BaseTest {

    @Test
    public void getIngredientsSuccess() {
        given()
                .get("/api/ingredients")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data", not(empty()));
    }
}