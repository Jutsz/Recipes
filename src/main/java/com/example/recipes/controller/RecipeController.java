package com.example.recipes.controller;

import com.example.recipes.model.Recipe;
import com.example.recipes.model.dto.RecipeDTO;
import com.example.recipes.model.types.RecipeType;
import com.example.recipes.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

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

    @GetMapping("/ingredient")
    public List<RecipeDTO> getRecipeByIngredient(@RequestParam(name="ingredient") String ingredientName) {
        return recipeService.getRecipeByIngredient(ingredientName);
    }

    @GetMapping("/recipetype/ingredient")
    public List<RecipeDTO> getRecipesWithRecipeTypeByIngredient(@RequestParam(name="recipeType") String recipeType,
                                                                @RequestParam(name="ingredient") String ingredientName) {
        return recipeService.getRecipesWithRecipeTypeByIngredient
                (RecipeType.valueOf(recipeType.toUpperCase()), ingredientName);
    }

    @PostMapping
    public void addNewRecipe(@RequestBody Recipe recipe) {
        recipeService.addNewRecipe(recipe);
    }

    @PutMapping("/update/{id}")
    public void updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        recipeService.updateRecipe(id, recipe);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }
}
