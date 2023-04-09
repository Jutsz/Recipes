package com.example.recipes.repository;

import com.example.recipes.model.Recipe;
import com.example.recipes.model.types.RecipeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeDAO extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByIngredients_nameContainsIgnoreCase(String ingredientName);

    List<Recipe> findAllByRecipeTypeIsAndIngredients_nameContainsIgnoreCase(RecipeType recipeType, String ingredientName);
}
