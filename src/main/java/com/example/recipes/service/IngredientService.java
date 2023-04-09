package com.example.recipes.service;

import com.example.recipes.mapper.IngredientMapper;
import com.example.recipes.model.Ingredient;
import com.example.recipes.model.dto.IngredientDTO;
import com.example.recipes.repository.IngredientDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientDAO ingredientDAO;
    private final IngredientMapper ingredientMapper;

    public IngredientService(IngredientDAO ingredientDAO, IngredientMapper ingredientMapper) {
        this.ingredientDAO = ingredientDAO;
        this.ingredientMapper = ingredientMapper;
    }

    public List<IngredientDTO> createDTOFromIngredientList(List<Ingredient> ingredientList){
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

    public List<IngredientDTO> getIngredientByName(String ingredientName){
        List<Ingredient> ingredients = ingredientDAO.findAllByNameContainsIgnoreCase(ingredientName);
        return createDTOFromIngredientList(ingredients);
    }


}
