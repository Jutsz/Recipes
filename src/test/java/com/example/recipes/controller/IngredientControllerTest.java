package com.example.recipes.controller;

import com.example.recipes.model.dto.IngredientDTO;
import com.example.recipes.service.IngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @Test
    public void testGetAllIngredient() throws Exception {
        List<IngredientDTO> ingredientDTOList = List.of(new IngredientDTO(1L, "salt", new HashSet<>()));
        when(ingredientService.getAllIngredient()).thenReturn(ingredientDTOList);

        mockMvc.perform(get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("salt"));
    }

    @Test
    public void testGetIngredientById() throws Exception {
        IngredientDTO ingredientDTO = new IngredientDTO(1L, "salt", new HashSet<>());
        when(ingredientService.getIngredientById(1L)).thenReturn(Optional.of(ingredientDTO));

        mockMvc.perform(get("/ingredients/1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name", is("salt")));
    }

    @Test
    public void testGetIngredientByIdNotFound() throws Exception {
        when(ingredientService.getIngredientById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/ingredients/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetIngredientByName() throws Exception {
        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        ingredientDTOList.add(new IngredientDTO(1L, "salt", new HashSet<>()));
        when(ingredientService.getIngredientByName("salt")).thenReturn(ingredientDTOList);

        mockMvc.perform(get("/ingredients/name?name=salt"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("salt")));
    }

    @Test
    public void testGetIngredientByNameNotFound() throws Exception {
        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        when(ingredientService.getIngredientByName("pepper")).thenReturn(ingredientDTOList);

        mockMvc.perform(get("/ingredients/name?name=pepper"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddIngredient() throws Exception {
        IngredientDTO ingredientDTO = new IngredientDTO(null, "salt", new HashSet<>());

        mockMvc.perform(post("/ingredients")
                        .content(new ObjectMapper().writeValueAsString(ingredientDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddIngredientBadRequest() throws Exception {
        IngredientDTO ingredientDTO = new IngredientDTO(null, null, new HashSet<>());

        mockMvc.perform(post("/ingredients")
                        .content(new ObjectMapper().writeValueAsString(ingredientDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateIngredient() throws Exception {
        IngredientDTO ingredientDTO = new IngredientDTO(1L, "salt", new HashSet<>());

        mockMvc.perform(put("/ingredients/update/1")
                        .content(new ObjectMapper().writeValueAsString(ingredientDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateIngredientBadRequest() throws Exception {
        IngredientDTO ingredientDTO = new IngredientDTO(1L, null, new HashSet<>());

        mockMvc.perform(put("/ingredients/update/1")
                        .content(new ObjectMapper().writeValueAsString(ingredientDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateIngredientNotFound() throws Exception {
    }
}
