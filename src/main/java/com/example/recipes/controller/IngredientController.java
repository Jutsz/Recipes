package com.example.recipes.controller;

import com.example.recipes.model.dto.IngredientDTO;
import com.example.recipes.service.IngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public List<IngredientDTO> getAllIngredient() {
        return ingredientService.getAllIngredient();
    }

    @GetMapping("/{id}")
    public IngredientDTO getIngredientById(@PathVariable Long id) {
        return ingredientService.getIngredientById(id);
    }

    @GetMapping("/name")
    public List<IngredientDTO> getIngredientByName(@RequestParam(name="name") String ingredientName) {
        return ingredientService.getIngredientByName(ingredientName);
    }
}