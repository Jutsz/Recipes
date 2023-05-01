package com.example.recipes.model.dto;

import com.example.recipes.model.types.RecipeType;
import lombok.*;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RecipeDTO {
    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private RecipeType recipeType;
    @NotEmpty
    private String text;
    @NotEmpty
    private List<String> ingredientNames;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeDTO recipeDTO = (RecipeDTO) o;
        return Objects.equals(id, recipeDTO.id) && Objects.equals(name, recipeDTO.name) && recipeType == recipeDTO.recipeType && Objects.equals(text, recipeDTO.text) && Objects.equals(ingredientNames, recipeDTO.ingredientNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, recipeType, text, ingredientNames);
    }
}
