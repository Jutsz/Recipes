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
import java.util.Optional;
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

    public void addIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientDTO.getId());
        ingredient.setName(ingredientDTO.getName());
        Set<String> recipeNames = ingredientDTO.getRecipeNames();
        Set<Recipe> recipes = new HashSet<>();
        if (!recipeNames.isEmpty()) {
            for (String recipeName : ingredientDTO.getRecipeNames()) {
                recipes.add(recipeDAO.findByNameContainsIgnoreCase(recipeName));
            }
        }
        ingredient.setRecipes(recipes);
        ingredientDAO.save(ingredient);
    }

    public void updateIngredient(Long id, IngredientDTO ingredientDTO) {
        Optional<Ingredient> ingredient = ingredientDAO.findById(id);
        /*TODO finish the method, make connection with recipes*/


    }
    public void deleteIngredient(Long id) {
        ingredientDAO.deleteById(id);
        /*TODO make sure that not recipes will be deleted also*/
    }

}
