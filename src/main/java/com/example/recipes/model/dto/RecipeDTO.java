package com.example.recipes.model.dto;

import com.example.recipes.model.types.RecipeType;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RecipeDTO {
    private Long id;
    private String name;
    private RecipeType recipeType;
    private String text;
    private List<String> ingredientNames;
}
