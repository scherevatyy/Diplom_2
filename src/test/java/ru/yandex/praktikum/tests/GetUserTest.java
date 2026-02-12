package ru.yandex.praktikum.tests;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.model.UserGenerator;
import ru.yandex.praktikum.steps.UserSteps;

import static org.hamcrest.Matchers.equalTo;

public class GetUserTest extends BaseTest {

    private final UserSteps userSteps = new UserSteps();
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        user = UserGenerator.randomUser();

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
    public void getUserWithAuthSuccess() {
        userSteps.getUser(accessToken)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(user.getEmail()))
                .body("user.name", equalTo(user.getName()));
    }

    @Test
    public void getUserWithoutAuthReturnsError() {
        userSteps.getUser("")
                .then()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }
}