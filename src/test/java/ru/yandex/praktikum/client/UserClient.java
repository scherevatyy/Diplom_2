package ru.yandex.praktikum.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import ru.yandex.praktikum.model.User;

import static io.restassured.RestAssured.given;

public class UserClient {

    private static final String REGISTER_PATH = "/api/auth/register";
    private static final String LOGIN_PATH = "/api/auth/login";
    private static final String USER_PATH = "/api/auth/user";

    public Response createUser(User user) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(user)
                .post(REGISTER_PATH);
    }

    public Response login(User user) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(user)
                .post(LOGIN_PATH);
    }

    public Response updateUser(String accessToken, User user) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", accessToken)
                .body(user)
                .patch(USER_PATH);
    }

    public Response updateUserWithoutAuth(User user) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(user)
                .patch(USER_PATH);
    }

    public void deleteUser(String accessToken) {
        given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", accessToken)
                .delete(USER_PATH);
    }

    public Response getUser(String accessToken) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", accessToken)
                .get(USER_PATH);
    }
}