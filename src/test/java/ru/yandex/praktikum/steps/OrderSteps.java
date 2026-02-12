package ru.yandex.praktikum.steps;

import io.restassured.response.Response;
import ru.yandex.praktikum.client.OrderClient;

public class OrderSteps {

    private final OrderClient orderClient = new OrderClient();

    public Response createOrder(Object body, String token) {
        return orderClient.createOrder(body, token);
    }

    public Response createOrderWithoutAuth(Object body) {
        return orderClient.createOrderWithoutAuth(body);
    }

    public Response getUserOrders(String token) {
        return orderClient.getUserOrders(token);
    }

    public Response getUserOrdersWithoutAuth() {
        return orderClient.getUserOrdersWithoutAuth();
    }
}