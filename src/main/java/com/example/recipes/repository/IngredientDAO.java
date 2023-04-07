package com.example.recipes.repository;

import com.example.recipes.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientDAO extends JpaRepository<Ingredient, Long> {
}
