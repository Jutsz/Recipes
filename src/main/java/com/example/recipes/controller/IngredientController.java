package com.example.recipes.controller;

import com.example.recipes.model.dto.IngredientDTO;
import com.example.recipes.service.IngredientService;
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

    @GetMapping
    public List<IngredientDTO> getAllIngredient() {
        return ingredientService.getAllIngredient();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable Long id) {
        Optional<IngredientDTO> ingredientDTOOptional = ingredientService.getIngredientById(id);
        return ingredientDTOOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name")
    public ResponseEntity<String> getIngredientByName(@RequestParam(name = "name") String ingredientName) {
        List<IngredientDTO> ingredientDTOS = ingredientService.getIngredientByName(ingredientName);
        if (ingredientDTOS.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredientDTOS.toString());

    }

    @PostMapping
    public ResponseEntity<String> addIngredient(@RequestBody @Valid IngredientDTO ingredientDTO, BindingResult errors) {
        if (errors.hasErrors()) return ResponseEntity.badRequest().build();
        else {
            ingredientService.addIngredient(ingredientDTO);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateIngredient(@PathVariable Long id, @RequestBody @Valid IngredientDTO ingredientDTO) {
        if (Objects.equals(id, ingredientDTO.getId()) && ingredientService.updateIngredient(id, ingredientDTO).isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
    }
}
