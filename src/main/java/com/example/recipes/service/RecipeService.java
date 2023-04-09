package com.example.recipes.service;

import com.example.recipes.mapper.RecipeMapper;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.dto.RecipeDTO;
import com.example.recipes.model.types.RecipeType;
import com.example.recipes.repository.RecipeDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeDAO recipeDAO;
    private final RecipeMapper recipeMapper;

    public RecipeService(RecipeDAO recipeDAO, RecipeMapper recipeMapper) {
        this.recipeDAO = recipeDAO;
        this.recipeMapper = recipeMapper;
    }

    public List<RecipeDTO> getAllRecipes(){
        List<Recipe> recipes = recipeDAO.findAll();
        return createDTOFromRecipeList(recipes);
    }

    public RecipeDTO getRecipeById(Long id){
        return recipeDAO.findById(id)
                .stream()
                .map(recipeMapper::toDTO)
                .findFirst()
                .orElse(null);
    }

    public List<RecipeDTO> getRecipeByIngredient (String ingredientName){
        List<Recipe> recipes = recipeDAO.findAllByIngredients_nameContainsIgnoreCase(ingredientName);
        return createDTOFromRecipeList(recipes);
    }

    public List<RecipeDTO> getRecipesWithRecipeTypeByIngredient(RecipeType recipeType, String ingredientName){
        List<Recipe> recipes = recipeDAO.findAllByRecipeTypeIsAndIngredients_nameContainsIgnoreCase(recipeType, ingredientName);
        return createDTOFromRecipeList(recipes);
    }

    public void addNewRecipe(Recipe recipe){
        recipeDAO.save(recipe);
    }

    public void updateRecipe (Long id, Recipe recipe){
        Recipe recipeToUpdate = recipeDAO.findById(id).orElseThrow();
        recipe.setId(recipeToUpdate.getId());
        recipeDAO.save(recipe);
    }

    public void deleteRecipe(Long id){
        recipeDAO.deleteById(id);
    }

    public List<RecipeDTO> createDTOFromRecipeList(List<Recipe> recipeList){
        return recipeList.stream()
                .map(recipeMapper::toDTO)
                .toList();
    }

}
