package com.example.recipes.model.dto;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class IngredientDTO {
    private Long id;
    private String name;
    private Set<String> recipeNames;
}
