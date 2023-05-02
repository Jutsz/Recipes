package com.example.recipes.controller;

import com.example.recipes.model.dto.IngredientDTO;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class IngredientControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void getAllIngredientShouldReturnAll() {
        ResponseEntity<List> response = restTemplate.getForEntity("/ingredients", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    public void getIngredientByIdShouldReturnIngredient() {
        IngredientDTO expectedResponse = new IngredientDTO(1L, "mozzarella", Set.of("mozzarellás szendvics"));
        ResponseEntity<IngredientDTO> response = restTemplate.getForEntity("/ingredients/1", IngredientDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void getIngredientByNameShouldReturnIngredient() {
        final String ingredientName = "mozzarella";
        final String expectedResponse = "[{\"id\":1,\"name\":\"mozzarella\",\"recipeNames\":[\"mozzarellás szendvics\"]}]";

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/ingredients/name?name={name}",
                String.class,
                ingredientName
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testAddIngredient() {
        IngredientDTO ingredientDTO = new IngredientDTO();
        String ingredientName = "testIngredient";
        ingredientDTO.setName(ingredientName);
        HttpEntity<IngredientDTO> request = new HttpEntity<>(ingredientDTO);
        ResponseEntity<String> responseForPost = restTemplate.postForEntity("/ingredients", request, String.class);
        assertEquals(HttpStatus.OK, responseForPost.getStatusCode());
        ResponseEntity<String> responseForGet = restTemplate.getForEntity(
                "/ingredients/name?name={name}",
                String.class,
                ingredientName
        );
        assertTrue(Objects.requireNonNull(responseForGet.getBody()).contains(ingredientName));
    }

    @Test
    public void testUpdateIngredient() {
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(1L);
        ingredientDTO.setName("updatedIngredient");
        HttpEntity<IngredientDTO> request = new HttpEntity<>(ingredientDTO);
        ResponseEntity<String> response = restTemplate.exchange("/ingredients/update/1", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ResponseEntity<IngredientDTO> responseToGet = restTemplate.getForEntity("/ingredients/1", IngredientDTO.class);
        assertEquals("updatedIngredient", Objects.requireNonNull(responseToGet.getBody()).getName());
    }
    @Test
    public void deletedIngredientShouldNotGetById() {
        restTemplate.delete("/ingredients/delete/1");

        ResponseEntity<IngredientDTO> response = restTemplate.getForEntity("/ingredients/1", IngredientDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}
