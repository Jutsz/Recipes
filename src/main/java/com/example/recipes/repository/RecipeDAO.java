package com.example.recipes.repository;

import com.example.recipes.model.Recipe;
import com.example.recipes.model.RecipeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeDAO extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByIngredients_name(String ingredientName);

    List<Recipe> findAllByRecipeTypeIsAndByIngredients_name(RecipeType recipeType, String ingredientName);
}
