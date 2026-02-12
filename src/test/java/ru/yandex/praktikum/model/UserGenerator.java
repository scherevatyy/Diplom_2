package ru.yandex.praktikum.model;

import java.util.UUID;

public class UserGenerator {

    public static User randomUser() {
        return new User(
                UUID.randomUUID() + "@mail.ru",
                UUID.randomUUID().toString(),
                "TestUser"
        );
    }
}