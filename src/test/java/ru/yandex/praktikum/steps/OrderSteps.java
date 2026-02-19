package ru.yandex.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.client.OrderClient;
import ru.yandex.praktikum.model.Order;

import java.util.List;

public class OrderSteps {

    private final OrderClient orderClient = new OrderClient();
    private final IngredientSteps ingredientSteps = new IngredientSteps();

    @Step("Создание заказа")
    public Response createOrder(Order body, String token) {
        return orderClient.createOrder(body, token);
    }

    @Step("Создание заказа без токена")
    public Response createOrderWithoutAuth(Order body) {
        return orderClient.createOrderWithoutAuth(body);
    }

    @Step("Получение заказов пользователя")
    public Response getUserOrders(String token) {
        return orderClient.getUserOrders(token);
    }

    @Step("Получаение заказов пользователя без токена")
    public Response getUserOrdersWithoutAuth() {
        return orderClient.getUserOrdersWithoutAuth();
    }

    @Step("Получение id ингредиентов")
    public List<String> getValidIngredientIds() {
        return ingredientSteps.getIngredientIds();
    }
}