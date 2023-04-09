package com.example.recipes.mapper;

import com.example.recipes.model.Ingredient;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.dto.IngredientDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class IngredientMapper {
    public IngredientDTO toDTO (Ingredient ingredient){
        if (ingredient==null){
            return null;
        }
        return IngredientDTO.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .recipeNames(ingredient.getRecipes().stream().map(Recipe::getName).collect(Collectors.toSet()))
                .build();
    }
}
