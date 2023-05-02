package com.example.recipes.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class IngredientDTO {
    private Long id;
    @NotEmpty
    private String name;
    private Set<String> recipeNames;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientDTO that = (IngredientDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(recipeNames, that.recipeNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, recipeNames);
    }
}
