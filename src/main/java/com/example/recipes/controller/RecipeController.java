package com.example.recipes.controller;

import com.example.recipes.model.Recipe;
import com.example.recipes.model.dto.RecipeDTO;
import com.example.recipes.model.types.RecipeType;
import com.example.recipes.service.RecipeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<RecipeDTO> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public RecipeDTO getRecipeById(@PathVariable("id")Long id) {
        return recipeService.getRecipeById(id);
    }

    public List<RecipeDTO> getRecipeByIngredient(String ingredientName) {
        return recipeService.getRecipeByIngredient(ingredientName);
    }

    public List<RecipeDTO> getRecipesWithRecipeTypeByIngredient(RecipeType recipeType, String ingredientName) {
        return recipeService.getRecipesWithRecipeTypeByIngredient(recipeType, ingredientName);
    }

    public void addNewRecipe(Recipe recipe) {
        recipeService.addNewRecipe(recipe);
    }

    public void updateRecipe(Long id, Recipe recipe) {
        recipeService.updateRecipe(id, recipe);
    }

    public void deleteRecipe(Long id) {
        recipeService.deleteRecipe(id);
    }
}
