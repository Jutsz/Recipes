package com.example.recipes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
//    @NotBlank
    private String name;
    private RecipeType recipeType;
    private String text;



}
