package com.example.recipes.service;

import com.example.recipes.mapper.RecipeMapper;
import com.example.recipes.model.dto.RecipeDTO;
import com.example.recipes.repository.IngredientDAO;
import com.example.recipes.repository.RecipeDAO;
import com.example.recipes.testmodels.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.example.recipes.data.TestRecipes.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    RecipeDAO recipeDAO;

    @Mock
    RecipeMapper recipeMapper;

    @Mock
    IngredientDAO ingredientDAO;

    @InjectMocks
    RecipeService recipeService;

    private static List<Recipe> recipeList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        recipeList = List.of(rizseshús, brokkolisTészta, sajtosSzendvics, gyümölcsösJoghurt, korpovitCottage, csirkemellesSaláta);
    }

    @Test
    void shouldReturnAllRecipesWhenGetAllRecipes() {
        when(recipeDAO.findAll()).thenReturn(new ArrayList<>());/*TODO create the mocking*/
    }

    @Test
    void getRecipeById() {
    }

    @Test
    void getRecipeByIngredient() {
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