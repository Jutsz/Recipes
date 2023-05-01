package com.example.recipes.service;

import com.example.recipes.mapper.RecipeMapper;
import com.example.recipes.model.Ingredient;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.dto.RecipeDTO;
import com.example.recipes.model.types.RecipeType;
import com.example.recipes.repository.IngredientDAO;
import com.example.recipes.repository.RecipeDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    RecipeDAO recipeDAO;

    @Mock
    RecipeMapper recipeMapper;

    @Mock
    IngredientDAO ingredientDAO;

    @InjectMocks
    RecipeService recipeService;

    private static final List<Recipe> recipeList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Ingredient olaj = new Ingredient(1L, "olaj", new HashSet<>());
        Ingredient hagyma = new Ingredient(2L, "hagyma", new HashSet<>());
        Ingredient pirospaprika = new Ingredient(3L, "pirospaprika", new HashSet<>());
        Ingredient csirkemell = new Ingredient(4L, "csirkemell", new HashSet<>());
        Ingredient rizs = new Ingredient(5L, "rizs", new HashSet<>());
        Recipe rizseshús = new Recipe(1L, "rizseshús", RecipeType.EBÉD, "készítsünk rizseshúst",
                Set.of(olaj, hagyma, pirospaprika, csirkemell, rizs));
        olaj.addRecipe(rizseshús);
        hagyma.addRecipe(rizseshús);
        pirospaprika.addRecipe(rizseshús);
        csirkemell.addRecipe(rizseshús);
        rizs.addRecipe(rizseshús);
        Ingredient fokhagyma = new Ingredient(6L, "fokhagyma", new HashSet<>());
        Ingredient brokkoli = new Ingredient(7L, "brokkoli", new HashSet<>());
        Ingredient tészta = new Ingredient(8L, "tészta", new HashSet<>());
        Ingredient tejszín = new Ingredient(9L, "tejszín", new HashSet<>());
        Recipe brokkolisTészta = new Recipe(2L, "brokkolis tészta", RecipeType.EBÉD,
                "készítsünk brokkolis tésztát", Set.of(hagyma, fokhagyma, brokkoli, tészta, tejszín));
        fokhagyma.addRecipe(brokkolisTészta);
        brokkoli.addRecipe(brokkolisTészta);
        tészta.addRecipe(brokkolisTészta);
        tejszín.addRecipe(brokkolisTészta);
        hagyma.addRecipe(brokkolisTészta);
        recipeList.add(rizseshús);
        recipeList.add(brokkolisTészta);

    }

    List<RecipeDTO> convertRecipeListToDto(List<Recipe> recipeList) {
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            recipeDTOList.add(new RecipeDTO(recipe.getId(),
                    recipe.getName(),
                    RecipeType.valueOf(String.valueOf(recipe.getRecipeType())),
                    recipe.getText(),
                    recipe.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList())));
        }
        return recipeDTOList;
    }

    @Test
    void shouldReturnEmptyListWhenGetAllRecipes() {
        when(recipeDAO.findAll()).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<>(), recipeService.getAllRecipes());
    }

    @Test
    void shouldReturnRecipesWhenGetAllRecipes() {
        when(recipeDAO.findAll()).thenReturn(recipeList);
        for (int i = 0; i < recipeList.size(); i++) {
            doReturn(convertRecipeListToDto(recipeList).get(i)).when(recipeMapper).toDTO(recipeList.get(i));
        }
        Recipe firstExpectedRecipe = recipeList.get(0);
        RecipeDTO firstActualRecipe = recipeService.getAllRecipes().get(0);
        assertEquals(firstExpectedRecipe.getName(), firstActualRecipe.getName());
        assertEquals(firstExpectedRecipe.getRecipeType(), firstActualRecipe.getRecipeType());
        assertEquals(firstExpectedRecipe.getText(), firstActualRecipe.getText());
        assertEquals(firstExpectedRecipe.getIngredients().stream().map(Ingredient::getName).toList(), firstActualRecipe.getIngredientNames());
        assertEquals(recipeList.size(), recipeService.getAllRecipes().size());
    }

    @Test
    void shouldReturnRecipeWhenGetRecipeById() {
        Recipe recipeToFind = recipeList.get(0);
        Long idOfRecipeToFind = recipeToFind.getId();
        Optional<RecipeDTO> expectedRecipeDTO = Optional.of(convertRecipeListToDto(List.of(recipeToFind)).get(0));
        when(recipeDAO.findById(idOfRecipeToFind)).thenReturn(Optional.of(recipeToFind));
//        doReturn(expectedRecipeDTO).when(recipeMapper.toDTO(recipeToFind));
        when(recipeMapper.toDTO(recipeToFind)).thenReturn(convertRecipeListToDto(List.of(recipeToFind)).get(0));
        assertEquals(expectedRecipeDTO, recipeService.getRecipeById(idOfRecipeToFind));
    }

    @Test
    void getRecipeByIngredient() {
    }

    @Test
    void getRecipesWithRecipeTypeByIngredient() {
    }

    @Test
    void addNewRecipe() {
    }

    @Test
    void updateRecipe() {
    }

    @Test
    void deleteRecipe() {
    }
}