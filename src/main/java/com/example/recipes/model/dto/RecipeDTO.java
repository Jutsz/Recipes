package com.example.recipes.model.dto;

import com.example.recipes.model.types.RecipeType;
import lombok.*;
import jakarta.validation.constraints.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RecipeDTO {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private RecipeType recipeType;
    @NotEmpty
    private String text;
    @NotEmpty
    private List<String> ingredientNames;
}
