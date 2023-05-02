package com.example.recipes.controller;

import com.example.recipes.model.dto.RecipeDTO;
import com.example.recipes.model.types.RecipeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RecipeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllRecipesShouldReturnAll() {
        ResponseEntity<List> response = restTemplate.getForEntity("/recipes", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    void getRecipeByIdShouldReturnRecipe() {
        RecipeDTO expectedResponse = new RecipeDTO(1L, "mozzarellás szendvics", RecipeType.REGGELI,
                "készítsünk mozzarellás szendvicset", List.of("margarin", "teljes kiőrlésű kenyér", "mozzarella"));
        ResponseEntity<RecipeDTO> response = restTemplate.getForEntity("/recipes/1", RecipeDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getRecipeByIngredientShouldReturnRecipes() {
    }

    @Test
    void getRecipesWithRecipeTypeByIngredient() {
    }

    @Test
    void addNewRecipe() {
    }

    @Test
    void updateRecipe() {
    }

    @Test
    void deleteRecipe() {
    }
}