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

public class CreateOrderTest extends BaseTest {

    private final UserSteps userSteps = new UserSteps();
    private final OrderSteps orderSteps = new OrderSteps();
    private final List<String> ingredientIds = orderSteps.getValidIngredientIds();

    private String accessToken;

    @Before
    public void setUp() {
        User user = UserGenerator.randomUser();
        Response response = userSteps.createUser(user);
        accessToken = response.path("accessToken");
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Успешное оздание заказа")
    @Description("Создание заказа с токеном авторизации")
    public void createOrderWithAuthSuccess() {
        Order order = new Order(List.of(ingredientIds.get(0), ingredientIds.get(1))
        );

        orderSteps.createOrder(order, accessToken)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Безуспешное создание заказа")
    @Description("Создание заказа без токена авторизации")
    public void createOrderWithoutAuthSuccess() {
        Order order = new Order(List.of(ingredientIds.get(0)));
        orderSteps.createOrderWithoutAuth(order)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Безуспешное создание заказа")
    @Description("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredientsReturnsError() {
        Order order = new Order(List.of());
        orderSteps.createOrder(order, accessToken)
                .then()
                .statusCode(400)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Безуспешное создание заказа")
    @Description("Создание заказа с некорректным ингредиентом")
    public void createOrderWithWrongIngredientReturnsError() {
        Order order = new Order(List.of("wrong_hash"));
        orderSteps.createOrder(order, accessToken)
                .then()
                .statusCode(500);
    }
}