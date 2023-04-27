package com.example.recipes.service;

import com.example.recipes.mapper.RecipeMapper;
import com.example.recipes.model.Ingredient;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.dto.RecipeDTO;
import com.example.recipes.model.types.RecipeType;
import com.example.recipes.repository.IngredientDAO;
import com.example.recipes.repository.RecipeDAO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeDAO.findAll();
        return createDTOFromRecipeList(recipes);
    }

    public Optional <RecipeDTO> getRecipeById(Long id) {
        return recipeDAO.findById(id)
                .stream()
                .map(recipeMapper::toDTO)
                .findFirst();
    }

    public List<RecipeDTO> getRecipeByIngredient(String ingredientName) {
        List<Recipe> recipes = recipeDAO.findAllByIngredients_nameContainsIgnoreCase(ingredientName);
        return createDTOFromRecipeList(recipes);
    }

    public List<RecipeDTO> getRecipesWithRecipeTypeByIngredient(RecipeType recipeType, String ingredientName) {
        List<Recipe> recipes = recipeDAO.findAllByRecipeTypeIsAndIngredients_nameContainsIgnoreCase(recipeType, ingredientName);
        return createDTOFromRecipeList(recipes);
    }

    private void createRecipeFromDTO(RecipeDTO recipeDTO, Recipe recipe) {
        recipe.setName(recipeDTO.getName());
        recipe.setRecipeType(recipeDTO.getRecipeType());
        recipe.setText(recipeDTO.getText());
        List<String> ingredientNamesOfRecipe = recipeDTO.getIngredientNames();
        Set<Ingredient> ingredients = new HashSet<>();
        for (String ingredientName : ingredientNamesOfRecipe) {
            Ingredient ingredient = ingredientDAO.findByNameContainsIgnoreCase(ingredientName);
            if(ingredient!=null){
                ingredients.add(ingredient);
            }
            else{
                Ingredient newIngredient = new Ingredient();
                newIngredient.setName(ingredientName);
                newIngredient.setRecipes(new HashSet<>());
                ingredients.add(newIngredient);
            }
        }
        ingredients.forEach(ingredient -> ingredient.addRecipe(recipe));
        recipe.setIngredients(ingredients);
    }

    public void addNewRecipe(@Valid RecipeDTO recipeDTO) {
        Recipe recipe = new Recipe();
        createRecipeFromDTO(recipeDTO, recipe);
        recipeDAO.save(recipe);
    }

    public Optional<Recipe> updateRecipe(Long id, @Valid RecipeDTO recipeDTO) {
        Optional<Recipe> recipe = recipeDAO.findById(id);
        createRecipeFromDTO(recipeDTO, recipe.orElseThrow());
        return Optional.of(recipeDAO.save(recipe.orElseThrow()));
    }

    public void deleteRecipe(Long id) {
        recipeDAO.deleteById(id);
    }

    public List<RecipeDTO> createDTOFromRecipeList(List<Recipe> recipeList) {
        return recipeList.stream()
                .map(recipeMapper::toDTO)
                .toList();
    }

}
