package com.example.recipes.service;

import com.example.recipes.mapper.RecipeMapper;
import com.example.recipes.model.dto.RecipeDTO;
import com.example.recipes.repository.IngredientDAO;
import com.example.recipes.repository.RecipeDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldReturnAllRecipesWhenGetAllRecipes() {
        List<RecipeDTO> expectedRecipes;
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