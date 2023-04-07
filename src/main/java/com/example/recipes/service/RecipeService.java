package com.example.recipes.service;

import com.example.recipes.model.Recipe;
import com.example.recipes.repository.RecipeDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private RecipeDAO recipeDAO;

    public RecipeService(RecipeDAO recipeDAO) {
        this.recipeDAO = recipeDAO;
    }

    public List<Recipe> getAllRecipes(){
        return recipeDAO.findAll();
    }

    public Recipe getRecipeById(Long id){
        return recipeDAO.findById(id).orElseThrow();
    }

    public List<Recipe> getRecipeByIngredient (String ingredient){
        return null;
//        return recipeDAO.find
    }

}
