package com.example.recipes.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

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
}
