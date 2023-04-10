package com.example.recipes.service;

import com.example.recipes.mapper.RecipeMapper;
import com.example.recipes.model.Ingredient;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.dto.RecipeDTO;
import com.example.recipes.model.types.RecipeType;
import com.example.recipes.repository.IngredientDAO;
import com.example.recipes.repository.RecipeDAO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecipeService {
    private final RecipeDAO recipeDAO;
    private final RecipeMapper recipeMapper;
    private final IngredientDAO ingredientDAO;

    public RecipeService(RecipeDAO recipeDAO, RecipeMapper recipeMapper, IngredientDAO ingredientDAO) {
        this.recipeDAO = recipeDAO;
        this.recipeMapper = recipeMapper;
        this.ingredientDAO = ingredientDAO;
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

    public void addNewRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeDTO.getName());
        Set<Ingredient> ingredientsOfRecipe = new HashSet<>();
        if (!recipeDTO.getIngredientNames().isEmpty()) {
            for (String ingredientName : recipeDTO.getIngredientNames()) {
                ingredientsOfRecipe.add(ingredientDAO.findByNameContainsIgnoreCase(ingredientName));
            }
        }
        recipe.setRecipeType(recipeDTO.getRecipeType());
        recipe.setText(recipeDTO.getText());
        recipe.setIngredients(ingredientsOfRecipe);
        recipeDAO.save(recipe);
    }
    public void updateRecipe (Long id, RecipeDTO recipeDTO){
        Recipe recipeToUpdate = recipeDAO.findById(id).orElse(null);
        Recipe recipe = new Recipe();
        assert recipeToUpdate != null;
        recipe.setId(recipeToUpdate.getId());
        recipeDAO.save(recipe);
    }
    /*TODO make the connection with ingredients*/

    public void deleteRecipe(Long id){
        recipeDAO.deleteById(id);
        /*TODO make sure that deleting a recipe not deletes the ingredients also*/
    }

    public List<RecipeDTO> createDTOFromRecipeList(List<Recipe> recipeList){
        return recipeList.stream()
                .map(recipeMapper::toDTO)
                .toList();
    }

}
