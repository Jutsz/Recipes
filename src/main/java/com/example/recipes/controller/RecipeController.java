package com.example.recipes.controller;

import com.example.recipes.model.Recipe;
import com.example.recipes.model.dto.RecipeDTO;
import com.example.recipes.model.types.RecipeType;
import com.example.recipes.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<RecipeDTO> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable("id")Long id) {
        Optional<RecipeDTO> recipeDTO = recipeService.getRecipeById(id);
        return recipeDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ingredient")
    public List<RecipeDTO> getRecipeByIngredient(@RequestParam(name="ingredient") String ingredientName) {
        return recipeService.getRecipeByIngredient(ingredientName);
    }

    @GetMapping("/recipetype/ingredient")
    public List<RecipeDTO> getRecipesWithRecipeTypeByIngredient(@RequestParam(name="recipeType") String recipeType,
                                                                @RequestParam(name="ingredient") String ingredientName) {
        return recipeService.getRecipesWithRecipeTypeByIngredient
                (RecipeType.valueOf(recipeType.toUpperCase()), ingredientName);
    }

    @PostMapping
    public ResponseEntity<Recipe> addNewRecipe(@RequestBody @Valid RecipeDTO recipeDTO, BindingResult errors) {
        if (errors.hasErrors()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(recipeService.addNewRecipe(recipeDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRecipe(@PathVariable Long id, @RequestBody @Valid RecipeDTO recipeDTO, BindingResult errors) {
        if (Objects.equals(id, recipeDTO.getId()) && recipeService.updateRecipe(id, recipeDTO).isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }
}
