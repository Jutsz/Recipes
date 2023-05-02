package com.example.recipes.model;

import com.example.recipes.model.types.RecipeType;
import jakarta.persistence.*;

import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Recipe {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private RecipeType recipeType;
    private String text;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name= "recipes_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id) && Objects.equals(name, recipe.name) && recipeType == recipe.recipeType && Objects.equals(text, recipe.text) && ingredients.containsAll(recipe.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, recipeType, text, ingredients);
    }
}
