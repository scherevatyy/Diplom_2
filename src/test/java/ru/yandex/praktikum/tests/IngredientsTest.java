package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.yandex.praktikum.steps.IngredientSteps;

import static org.hamcrest.Matchers.*;

public class IngredientsTest extends BaseTest {

    private final IngredientSteps ingredientSteps = new IngredientSteps();

    @Test
    @DisplayName("Успешное получение ингредиентов")
    @Description("Получение ингредиентов")
    public void getIngredientsSuccess() {
        ingredientSteps.getIngredients()
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data", not(empty()));
    }
}