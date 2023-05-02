package com.example.recipes.controller;

import com.example.recipes.model.dto.IngredientDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class IngredientControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getUrl() {
        return "http://localhost:" + port + "/ingredients";
    }

    @Test
    public void shouldReturnAllIngredients() throws Exception {
        URI url = new URI(getUrl());
        ResponseEntity<IngredientDTO[]> responseEntity = restTemplate.getForEntity(url, IngredientDTO[].class);
        List<IngredientDTO> ingredients = Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertThat(ingredients).isNotEmpty();
    }
    @Test
    public void testGetAllIngredient() {
        ResponseEntity<List> response = restTemplate.getForEntity("/ingredients", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetIngredientById() {
        ResponseEntity<IngredientDTO> response = restTemplate.getForEntity("/ingredients/1", IngredientDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetIngredientByName() {
        ResponseEntity<List> response = restTemplate.getForEntity("/ingredients/name?name=banana", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddIngredient() {
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setName("testIngredient");
        HttpEntity<IngredientDTO> request = new HttpEntity<>(ingredientDTO);
        ResponseEntity<String> response = restTemplate.postForEntity("/ingredients", request, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateIngredient() {
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(1L);
        ingredientDTO.setName("updatedIngredient");
        HttpEntity<IngredientDTO> request = new HttpEntity<>(ingredientDTO);
        ResponseEntity<String> response = restTemplate.exchange("/ingredients/update/1", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteIngredient() {
        restTemplate.delete("/ingredients/delete/1");
        ResponseEntity<IngredientDTO> response = restTemplate.getForEntity("/ingredients/1", IngredientDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
