package com.example.recipes.mapper;

import com.example.recipes.model.Ingredient;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.dto.RecipeDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RecipeMapper {
    public RecipeDTO toDTO (Recipe recipe){
        if (recipe==null){
            return null;
        }
        return RecipeDTO.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .recipeType(recipe.getRecipeType())
                .text(recipe.getText())
                .ingredientNames(recipe.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList()))
                .build();
    }
}
