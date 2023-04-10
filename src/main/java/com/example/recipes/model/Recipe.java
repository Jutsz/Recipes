package com.example.recipes.model;

import com.example.recipes.model.types.RecipeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
//    @NotBlank
    private String name;
    @Enumerated(EnumType.STRING)
    private RecipeType recipeType;
    private String text;
    @ManyToMany
    @JoinTable(
            name="recipes_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients;

    public void addIngredient (Ingredient ingredient) {
        if (ingredients != null) {
            ingredients.add(ingredient);
        }
    }



}
