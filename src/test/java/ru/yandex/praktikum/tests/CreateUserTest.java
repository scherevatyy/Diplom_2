package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.model.UserGenerator;
import ru.yandex.praktikum.steps.UserSteps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUserTest extends BaseTest {

    private final UserSteps userSteps = new UserSteps();
    private String accessToken;

    @After
    public void tearDown() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Успешное создание уникального пользователя")
    @Description("Создание пользователя с уникальными данными")
    public void createUniqueUserSuccess() {
        User user = UserGenerator.randomUser();
        Response response = userSteps.createUser(user);
        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue());
        accessToken = response.path("accessToken");
    }

    @Test
    @DisplayName("Безуспешное создание пользователя")
    @Description("Создание дубликата пользователя, получение 403 HTTP кода")
    public void createDuplicateUserReturnsError() {
        User user = UserGenerator.randomUser();
        Response firstUser = userSteps.createUser(user);
        accessToken = firstUser.path("accessToken");
        Response response = userSteps.createUser(user);
        response.then()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Безуспешное создание пользователя")
    @Description("Создание пользователя без обязательных параметров, получение 400 HTTP кода")
    public void createUserWithoutRequiredFieldReturnsError() {
        User user = new User(
                null,
                "password",
                "name"
        );
        Response response = userSteps.createUser(user);
        response.then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }
}