package com.example.recipes.service;

import com.example.recipes.mapper.IngredientMapper;
import com.example.recipes.model.Ingredient;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.dto.IngredientDTO;
import com.example.recipes.repository.IngredientDAO;
import com.example.recipes.repository.RecipeDAO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class IngredientService {
    private final IngredientDAO ingredientDAO;
    private final IngredientMapper ingredientMapper;
    private final RecipeDAO recipeDAO;

    public IngredientService(IngredientDAO ingredientDAO, IngredientMapper ingredientMapper, RecipeDAO recipeDAO) {
        this.ingredientDAO = ingredientDAO;
        this.ingredientMapper = ingredientMapper;
        this.recipeDAO = recipeDAO;
    }

    public List<IngredientDTO> createDTOFromIngredientList(List<Ingredient> ingredientList) {
        return ingredientList.stream()
                .map(ingredientMapper::toDTO)
                .toList();
    }

    public List<IngredientDTO> getAllIngredient() {
        List<Ingredient> ingredients = ingredientDAO.findAll();
        return createDTOFromIngredientList(ingredients);
    }

    public IngredientDTO getIngredientById(Long id) {
        Ingredient ingredient = ingredientDAO.findById(id).orElse(null);
        return ingredientMapper.toDTO(ingredient);
    }

    public List<IngredientDTO> getIngredientByName(String ingredientName) {
        List<Ingredient> ingredients = ingredientDAO.findAllByNameContainsIgnoreCase(ingredientName);
        return createDTOFromIngredientList(ingredients);
    }

    private Set<Recipe> getRecipesFromIngredientDTO(IngredientDTO ingredientDTO) {
        Set<String> recipeNames = ingredientDTO.getRecipeNames();
        Set<Recipe> recipes = new HashSet<>();
        if (!(recipeNames ==null)) {
            for (String recipeName : ingredientDTO.getRecipeNames()) {
                recipes.add(recipeDAO.findByNameContainsIgnoreCase(recipeName));
            }
        }
        return recipes;
    }
    private void createIngredientFromDTO(IngredientDTO ingredientDTO, Ingredient ingredient) {
        ingredient.setName(ingredientDTO.getName());
        Set<Recipe> recipes = getRecipesFromIngredientDTO(ingredientDTO);
        if (!recipes.isEmpty()) {
            ingredient.setRecipes(recipes);
        }
    }
    public void addIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient();
        createIngredientFromDTO(ingredientDTO, ingredient);
        ingredientDAO.save(ingredient);
    }

    public void updateIngredient(Long id, IngredientDTO ingredientDTO) {
        Ingredient ingredient = getIngredientByIdFromDatabase(id);
        createIngredientFromDTO(ingredientDTO, ingredient);
        ingredientDAO.save(ingredient);
    }

    private Ingredient getIngredientByIdFromDatabase(Long id) {
        Ingredient ingredient = ingredientDAO.findById(id).orElse(null);
        assert ingredient != null;
        return ingredient;
    }

    public void deleteIngredient(Long id) {
        Ingredient ingredient = getIngredientByIdFromDatabase(id);
        List<Recipe> recipesWithIngredient = recipeDAO.findAllByIngredients_nameContainsIgnoreCase(ingredient.getName());
        for (Recipe recipe : recipesWithIngredient) {
            recipe.getIngredients().remove(ingredient);
        }
        ingredientDAO.deleteById(id);
    }

}
