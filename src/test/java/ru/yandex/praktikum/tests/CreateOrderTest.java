package ru.yandex.praktikum.tests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.model.Ingredients;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.model.UserGenerator;
import ru.yandex.praktikum.steps.OrderSteps;
import ru.yandex.praktikum.steps.UserSteps;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class CreateOrderTest extends BaseTest {

    private final UserSteps userSteps = new UserSteps();
    private final OrderSteps orderSteps = new OrderSteps();

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
    public void createOrderWithAuthSuccess() {
        Map<String, Object> body = Map.of(
                "ingredients", List.of(Ingredients.BUN, Ingredients.SAUCE)
        );

        orderSteps.createOrder(body, accessToken)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    public void createOrderWithoutAuthSuccess() {
        Map<String, Object> body = Map.of(
                "ingredients", List.of(Ingredients.BUN)
        );

        orderSteps.createOrderWithoutAuth(body)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    public void createOrderWithoutIngredientsReturnsError() {
        Map<String, Object> body = Map.of(
                "ingredients", List.of()
        );

        orderSteps.createOrder(body, accessToken)
                .then()
                .statusCode(400)
                .body("success", equalTo(false));
    }

    @Test
    public void createOrderWithWrongIngredientReturnsError() {
        Map<String, Object> body = Map.of(
                "ingredients", List.of("wrong_hash")
        );
        orderSteps.createOrder(body, accessToken)
                .then()
                .statusCode(500);
    }
}