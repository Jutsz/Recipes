package com.example.recipes.controller;

import com.example.recipes.model.dto.RecipeDTO;
import com.example.recipes.model.types.RecipeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Objects;

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
        String ingredientToSearch = "margarin";
        RecipeDTO recipeDTO1 = new RecipeDTO(1L, "mozzarellás szendvics", RecipeType.REGGELI,
                "készítsünk mozzarellás szendvicset", List.of("margarin", "teljes kiőrlésű kenyér",
                "mozzarella"));
        RecipeDTO recipeDTO2 = new RecipeDTO(2L, "sonkás szendvics", RecipeType.REGGELI,
                "készítsünk sonkás szendvicset", List.of("margarin", "teljes kiőrlésű kenyér",
                "sonka"));
        RecipeDTO recipeDTO3 = new RecipeDTO(3L, "camambertes szendvics", RecipeType.REGGELI,
                "készítsünk camambertes szendvicset", List.of("margarin", "teljes kiőrlésű kenyér",
                "camambert"));
        List<RecipeDTO> expectedResponse = List.of(recipeDTO1, recipeDTO2, recipeDTO3);
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/recipes/ingredient?ingredient={ingredient}",
                String.class,
                ingredientToSearch
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains(expectedResponse.get(0).getName()));
        assertTrue(Objects.requireNonNull(response.getBody()).contains(expectedResponse.get(1).getName()));
        assertTrue(Objects.requireNonNull(response.getBody()).contains(expectedResponse.get(2).getName()));
    }

    @Test
    void getRecipeByNonExistingIngredientShouldReturnNotFound() {
        String ingredientToSearch = "krumpli";
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/recipes/ingredient?ingredient={ingredient}",
                String.class,
                ingredientToSearch
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getRecipesWithRecipeTypeByIngredientShouldReturnRecipes() {
        String ingredientToSearch = "rizs";
        String recipeTypeToSearch = "ebéd";
        RecipeDTO expectedRecipeDTO = new RecipeDTO(4L, "rizseshús", RecipeType.EBÉD,
                "készítsünk rizseshúst", List.of("hagyma", "olaj", "csirkemell", "rizs"));
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/recipes/recipetype/ingredient?recipeType={recipeType}&ingredient={ingredient}",
                String.class,
                recipeTypeToSearch,
                ingredientToSearch
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains(expectedRecipeDTO.getName()));
    }

    @Test
    void getRecipesWithRecipeTypeByNonExistingIngredientShouldReturnBadRequest() {
        String ingredientToSearch = "krumpli";
        String recipeTypeToSearch = "ebéd";
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/recipes/recipetype/ingredient?recipeType={recipeType}&ingredient={ingredient}",
                String.class,
                recipeTypeToSearch,
                ingredientToSearch
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddNewRecipe() {
        RecipeDTO recipeDTO = new RecipeDTO();
        String recipeName = "testRecipe";
        String ingredientName = "testIngredient";
        String text = "test text";
        RecipeType recipeType = RecipeType.EBÉD;
        recipeDTO.setIngredientNames(List.of(ingredientName));
        recipeDTO.setName(recipeName);
        recipeDTO.setText(text);
        recipeDTO.setRecipeType(recipeType);
        HttpEntity<RecipeDTO> request = new HttpEntity<>(recipeDTO);
        ResponseEntity<String> responseForPost = restTemplate.postForEntity("/recipes", request, String.class);
        assertEquals(HttpStatus.OK, responseForPost.getStatusCode());
        ResponseEntity<String> responseForGet = restTemplate.getForEntity(
                "/recipes/ingredient?ingredient={ingredient}",
                String.class,
                ingredientName
        );
        assertTrue(Objects.requireNonNull(responseForGet.getBody()).contains(ingredientName));
    }

    @Test
    void testAddNewRecipeWithMissingValuesShouldReturnBadRequest() {
        RecipeDTO recipeDTO = new RecipeDTO();
        String recipeName = "testRecipe";
        recipeDTO.setName(recipeName);
        HttpEntity<RecipeDTO> request = new HttpEntity<>(recipeDTO);
        ResponseEntity<String> responseForPost = restTemplate.postForEntity("/recipes", request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseForPost.getStatusCode());
    }

    @Test
    void testUpdateRecipe() {
        RecipeDTO recipeDTO = new RecipeDTO();
        String ingredientName = "testIngredient";
        String text = "test text";
        RecipeType recipeType = RecipeType.EBÉD;
        recipeDTO.setIngredientNames(List.of(ingredientName));
        recipeDTO.setText(text);
        recipeDTO.setRecipeType(recipeType);
        recipeDTO.setId(1L);
        recipeDTO.setName("updatedRecipe");
        HttpEntity<RecipeDTO> request = new HttpEntity<>(recipeDTO);
        ResponseEntity<String> response = restTemplate.exchange("/recipes/update/1", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ResponseEntity<RecipeDTO> responseToGet = restTemplate.getForEntity("/recipes/1", RecipeDTO.class);
        assertEquals("updatedRecipe", Objects.requireNonNull(responseToGet.getBody()).getName());
    }

    @Test
    void testUpdateRecipeWithMissingValuesShouldReturnBadRequest() {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("updatedRecipe");
        HttpEntity<RecipeDTO> request = new HttpEntity<>(recipeDTO);
        ResponseEntity<String> response = restTemplate.exchange("/recipes/update/1", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deletedRecipeShouldNotGetById() {
        restTemplate.delete("/recipes/delete/1");
        ResponseEntity<RecipeDTO> response = restTemplate.getForEntity("/recipes/1", RecipeDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}