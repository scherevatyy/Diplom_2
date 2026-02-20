package ru.yandex.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.client.IngredientClient;

import java.util.List;

public class IngredientSteps {

    private final IngredientClient IngredientClient = new IngredientClient();

    @Step("Получение списка ингредиентов")
    public Response getIngredients() {
        return IngredientClient.getIngredients();
    }

    @Step("Получение валидных id ингредиентов")
    public List<String> getIngredientIds() {
        Response response = getIngredients();
        return response.jsonPath().getList("data._id");
    }
}
