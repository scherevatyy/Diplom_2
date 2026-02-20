package ru.yandex.praktikum.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientClient {
    private static final String ORDERS_PATH = "/api/ingredients";

    public Response getIngredients() {
        return given().filter(new AllureRestAssured()).get(ORDERS_PATH);
    }
}
