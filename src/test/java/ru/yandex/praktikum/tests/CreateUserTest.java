package ru.yandex.praktikum.tests;

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
    public void createDuplicateUserReturnsError() {
        User user = UserGenerator.randomUser();
        userSteps.createUser(user);
        Response response = userSteps.createUser(user);
        response.then()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Test
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