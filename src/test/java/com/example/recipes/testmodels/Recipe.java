package com.example.recipes.testmodels;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Recipe {
    private Long id;
    private String name;
    private RecipeType recipeType;
    private String text;
    private List<String> ingredientNames;

    public Recipe(Long id, String name, RecipeType recipeType, String text, List<String> ingredientNames) {
        this.id = id;
        this.name = name;
        this.recipeType = recipeType;
        this.text = text;
        this.ingredientNames = ingredientNames;
    }
}
