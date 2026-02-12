package ru.yandex.praktikum.client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String ORDERS_PATH = "/api/orders";

    public Response createOrder(Object body, String accessToken) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .body(body)
                .post(ORDERS_PATH);
    }

    public Response createOrderWithoutAuth(Object body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .post(ORDERS_PATH);
    }

    public Response getUserOrders(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .get(ORDERS_PATH);
    }

    public Response getUserOrdersWithoutAuth() {
        return given()
                .get(ORDERS_PATH);
    }
}