package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.steps.UserSteps;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.model.UserGenerator;

import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest extends BaseTest {

    private UserSteps userSteps;
    private User user;
    private String accessToken;


    @Before
    public void setUpUser() {
        userSteps = new UserSteps();
        user = UserGenerator.randomUser();
        userSteps.createUser(user);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Успешная авторизация существующего пользователя")
    @Description("Авторизация существующего пользователя")
    public void loginWithExistingUserSuccess() {
        Response response = userSteps.login(user);
        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
        accessToken = response.jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Безуспешная авторизация существующего пользователя")
    @Description("Авторизация с некорректным паролем пользователя, проверка сообщения ошибки")
    public void loginWithWrongPasswordReturnsError() {
        User wrongUser = new User(
                user.getEmail(),
                "wrongPassword",
                user.getName()
        );
        userSteps.login(wrongUser)
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}