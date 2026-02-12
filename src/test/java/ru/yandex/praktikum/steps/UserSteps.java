package ru.yandex.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.client.UserClient;
import ru.yandex.praktikum.model.User;

public class UserSteps {

    private final UserClient userClient = new UserClient();

    @Step("Создание пользователя")
    public Response createUser(User user) {
        return userClient.createUser(user);
    }

    @Step("Логин пользователя")
    public Response login(User user) {
        return userClient.login(user);
    }

    @Step("Обновление данных пользователя с авторизацией")
    public Response updateUserWithAuth(String token, User user) {
        return userClient.updateUser(token, user);
    }

    @Step("Обновление данных пользователя без авторизации")
    public Response updateUserWithoutAuth(User user) {
        return userClient.updateUserWithoutAuth(user);
    }

    @Step("Удаление пользователя")
    public void deleteUser(String token) {
        userClient.deleteUser(token);
    }

    @Step("Получение данных пользователя")
    public Response getUser(String accessToken) {
        return userClient.getUser(accessToken);
    }
}