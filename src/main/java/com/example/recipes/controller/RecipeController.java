package com.example.recipes.controller;

import com.example.recipes.model.dto.RecipeDTO;
import com.example.recipes.model.types.RecipeType;
import com.example.recipes.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Get all recipes of the cookbook",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Responds with a list of Recipe entities."),
            }
    )
    @GetMapping
    public List<RecipeDTO> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @Operation(
            summary = "Get a recipe by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Responds with a recipe with a given id"),
                    @ApiResponse(responseCode = "404", description = "Recipe by the given id not found.")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable("id") Long id) {
        Optional<RecipeDTO> recipeDTO = recipeService.getRecipeById(id);
        return recipeDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get a recipe by an ingredient",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Responds with a recipe with a given ingredient"),
                    @ApiResponse(responseCode = "404", description = "Recipe with the given id not found.")
            }
    )
    @GetMapping("/ingredient")
    public ResponseEntity<List<RecipeDTO>> getRecipeByIngredient(@RequestParam(name = "ingredient") String ingredientName) {
        List<RecipeDTO> recipeDTOList = recipeService.getRecipeByIngredient(ingredientName);
        if (!recipeDTOList.isEmpty()) {
            return ResponseEntity.ok(recipeDTOList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get a recipe by an ingredient, in a given recipe type",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Responds with a recipe in the given recipe type, with the given ingredient"),
                    @ApiResponse(responseCode = "404", description = "Recipe with the given ingredient and recipe type not found.")
            }
    )
    @GetMapping("/recipetype/ingredient")
    public ResponseEntity<List<RecipeDTO>> getRecipesWithRecipeTypeByIngredient(@RequestParam(name = "recipeType") String recipeType,
                                                                @RequestParam(name = "ingredient") String ingredientName) {
        List<RecipeDTO> recipeDTOList = recipeService.getRecipesWithRecipeTypeByIngredient
                (RecipeType.valueOf(recipeType.toUpperCase()), ingredientName);
        if (recipeDTOList.isEmpty()){
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok(recipeDTOList);
    }

    @Operation(
            summary = "Add a new recipe to the cookbook",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The recipe has been added to the cookbook.",
                            content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeDTO.class)) }),
                    @ApiResponse(responseCode = "400", description = "The recipe was tried with bad values added.")
            }
    )
    @PostMapping
    public ResponseEntity<String> addNewRecipe(@RequestBody @Valid RecipeDTO recipeDTO, BindingResult errors) {
        if (errors.hasErrors()) return ResponseEntity.badRequest().build();
        else {
            recipeService.addNewRecipe(recipeDTO);
            return ResponseEntity.ok().build();
        }
    }

    @Operation(
            summary = "Update a recipe in the cookbook wih a given id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The recipe has been updated in the cookbook.",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RecipeDTO.class)) }),
                    @ApiResponse(responseCode = "400", description = "The recipe was tried with bad values updated.")
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRecipe(@PathVariable Long id, @RequestBody @Valid RecipeDTO recipeDTO) {
        if (Objects.equals(id, recipeDTO.getId()) && recipeService.updateRecipe(id, recipeDTO).isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Delete a recipe in the cookbook with a given id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The recipe has been deleted from the cookbook.",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RecipeDTO.class)) }),
                    @ApiResponse(responseCode = "500", description = "There is no recipe with the given id in the cookbook.")
            }
    )
    @DeleteMapping("/delete/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }
}
