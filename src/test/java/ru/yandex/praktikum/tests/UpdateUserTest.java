package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.model.UserGenerator;
import ru.yandex.praktikum.steps.UserSteps;

import static org.hamcrest.Matchers.equalTo;

public class UpdateUserTest extends BaseTest {

    private UserSteps userSteps;
    private User user;
    private String accessToken;

    @Before
    public void setUpUser() {
        userSteps = new UserSteps();
        user = UserGenerator.randomUser();
        userSteps.createUser(user);
        Response response = userSteps.login(user);
        response.then()
                .statusCode(200)
                .body("success", equalTo(true));

        accessToken = response.jsonPath().getString("accessToken");
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Успешное изменение email пользователя с авторизацией")
    @Description("Проверка изменения email пользователя")
    public void updateUserEmailWithAuthSuccess() {

        User updatedUser = new User(
                "updated_" + user.getEmail(),
                user.getPassword(),
                user.getName()
        );

        userSteps.updateUserWithAuth(accessToken, updatedUser)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Изменение имени пользователя с авторизацией")
    @Description("Проверка изменения имени пользователя")
    public void updateUserNameWithAuthSuccess() {

        User updatedUser = new User(
                user.getEmail(),
                user.getPassword(),
                "UpdatedName"
        );

        userSteps.updateUserWithAuth(accessToken, updatedUser)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка изменения данных пользователя без авторизации")
    @Description("Изменение данных пользователя без авторизации возвращает ошибку")
    public void updateUserWithoutAuthUnauthorized() {

        User updatedUser = new User(
                user.getEmail(),
                user.getPassword(),
                "Hacker"
        );

        userSteps.updateUserWithoutAuth(updatedUser)
                .then()
                .statusCode(401)
                .body("success", equalTo(false));
    }
}