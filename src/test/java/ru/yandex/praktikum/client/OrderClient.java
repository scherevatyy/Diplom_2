package ru.yandex.praktikum.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import ru.yandex.praktikum.model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String ORDERS_PATH = "/api/orders";

    public Response createOrder(Order order, String accessToken) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .body(order)
                .post(ORDERS_PATH);
    }

    public Response createOrderWithoutAuth(Order order) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .body(order)
                .post(ORDERS_PATH);
    }

    public Response getUserOrders(String accessToken) {
        return given()
                .filter(new AllureRestAssured())
                .header("Authorization", accessToken)
                .get(ORDERS_PATH);
    }

    public Response getUserOrdersWithoutAuth() {
        return given()
                .filter(new AllureRestAssured())
                .get(ORDERS_PATH);
    }
}