package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.model.Order;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.model.UserGenerator;
import ru.yandex.praktikum.steps.OrderSteps;
import ru.yandex.praktikum.steps.UserSteps;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class GetUserOrdersTest extends BaseTest {

    private final UserSteps userSteps = new UserSteps();
    private final OrderSteps orderSteps = new OrderSteps();
    private final List<String> ingredientIds = orderSteps.getValidIngredientIds();

    private String accessToken;

    @Before
    public void setUp() {
        User user = UserGenerator.randomUser();
        Response response = userSteps.createUser(user);
        accessToken = response.path("accessToken");
        Order order = new Order(List.of(ingredientIds.get(0)));
        orderSteps.createOrder(order, accessToken);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Успешное получение заказов пользователя")
    @Description("Получение заказов пользователя с авторизацией")
    public void getUserOrdersWithAuthSuccess() {
        orderSteps.getUserOrders(accessToken)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("orders", not(empty()))
                .body("orders[0]._id", notNullValue())
                .body("orders[0].ingredients", not(empty()));
    }

    @Test
    @DisplayName("Безуспешное получение заказов пользователя")
    @Description("Получение заказов пользователя без авторизации, получение 401 HTTP кода")
    public void getUserOrdersWithoutAuthReturnsError() {
        orderSteps.getUserOrdersWithoutAuth()
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}