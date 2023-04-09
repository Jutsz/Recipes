package com.example.recipes.repository;

import com.example.recipes.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientDAO extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllByNameContainsIgnoreCase(String name);
}
