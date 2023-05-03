package com.example.recipes.controller;

import com.example.recipes.model.dto.IngredientDTO;
import com.example.recipes.service.IngredientService;
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
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Operation(
            summary = "Get all ingredients of the cookbook",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Responds with a list of ingredients."),
            }
    )
    @GetMapping
    public List<IngredientDTO> getAllIngredient() {
        return ingredientService.getAllIngredient();
    }

    @Operation(
            summary = "Get an ingredient by id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Responds with an ingredient that matches the given ID."),
                    @ApiResponse(responseCode = "404", description = "No ingredient was found with the given id.")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable Long id) {
        Optional<IngredientDTO> ingredientDTOOptional = ingredientService.getIngredientById(id);
        return ingredientDTOOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(
            summary = "Get a list of ingredients matching the provided name.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Responds with an ingredient with a given name"),
                    @ApiResponse(responseCode = "404", description = "No ingredient was found with the given name.")
            }
    )
    @GetMapping("/name")
    public ResponseEntity<List<IngredientDTO>> getIngredientByName(@RequestParam(name = "name") String ingredientName) {
        List<IngredientDTO> ingredientDTOS = ingredientService.getIngredientByName(ingredientName);
        if (ingredientDTOS.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredientDTOS);
    }

    @Operation(
            summary = "Add a new ingredient to the cookbook.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The ingredient has been added to the cookbook.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = IngredientDTO.class))}),
                    @ApiResponse(responseCode = "400", description = "The ingredient was tried with bad values added.")
            }
    )
    @PostMapping
    public ResponseEntity<String> addIngredient(@RequestBody @Valid IngredientDTO ingredientDTO, BindingResult errors) {
        if (errors.hasErrors()) return ResponseEntity.badRequest().build();
        else {
            ingredientService.addIngredient(ingredientDTO);
            return ResponseEntity.ok().build();
        }
    }

    @Operation(
            summary = "Update an ingredient in the cookbook.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The ingredient has been updated in the cookbook.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = IngredientDTO.class))}),
                    @ApiResponse(responseCode = "400", description = "The ingredient was tried with bad values updated.")
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateIngredient(@PathVariable Long id, @RequestBody @Valid IngredientDTO ingredientDTO) {
        if (Objects.equals(id, ingredientDTO.getId()) && ingredientService.updateIngredient(id, ingredientDTO).isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Delete an ingredient from the cookbook with a given id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The ingredient has been deleted from the cookbook.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = IngredientDTO.class))}),
            }
    )
    @DeleteMapping("/delete/{id}")
    public void deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
    }
}
