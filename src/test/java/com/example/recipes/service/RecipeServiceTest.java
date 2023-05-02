package com.example.recipes.service;

import com.example.recipes.mapper.RecipeMapper;
import com.example.recipes.model.Ingredient;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.dto.RecipeDTO;
import com.example.recipes.model.types.RecipeType;
import com.example.recipes.repository.IngredientDAO;
import com.example.recipes.repository.RecipeDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

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

    private static List<Recipe> recipeList = new ArrayList<>();

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
        Ingredient tej = new Ingredient(9L, "tejszín", new HashSet<>());
        Recipe brokkolisTészta = new Recipe(2L, "brokkolis tészta", RecipeType.EBÉD,
                "készítsünk brokkolis tésztát", Set.of(olaj, hagyma, fokhagyma, brokkoli, tészta, tej));
        olaj.addRecipe(brokkolisTészta);
        fokhagyma.addRecipe(brokkolisTészta);
        brokkoli.addRecipe(brokkolisTészta);
        tészta.addRecipe(brokkolisTészta);
        tej.addRecipe(brokkolisTészta);
        hagyma.addRecipe(brokkolisTészta);
        Ingredient zabpehely = new Ingredient(10L, "zabpehely", new HashSet<>());
        Recipe zabkása = new Recipe(3L, "zabkása", RecipeType.REGGELI, "keverjük össze az alapanyagokat", Set.of(zabpehely, tej));
        zabpehely.addRecipe(zabkása);
        tej.addRecipe(zabkása);
        Ingredient zsír = new Ingredient(11L, "zsír", new HashSet<>());
        Ingredient tojás = new Ingredient(12L, "tojás", new HashSet<>());
        Recipe rántotta = new Recipe(4L, "rántotta", RecipeType.REGGELI, "süssünk rántottát", Set.of(zsír, tojás, hagyma));
        zsír.addRecipe(rántotta);
        tojás.addRecipe(rántotta);
        hagyma.addRecipe(rántotta);
        recipeList.add(rizseshús);
        recipeList.add(brokkolisTészta);
        recipeList.add(zabkása);
        recipeList.add(rántotta);
    }

    @AfterEach
    void clear() {
        recipeList = new ArrayList<>();
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
        RecipeDTO expectedRecipeDTO = convertRecipeListToDto(List.of(recipeToFind)).get(0);
        when(recipeDAO.findById(idOfRecipeToFind)).thenReturn(Optional.of(recipeToFind));
        when(recipeMapper.toDTO(recipeToFind)).thenReturn(expectedRecipeDTO);
        assertEquals(Optional.of(expectedRecipeDTO), recipeService.getRecipeById(idOfRecipeToFind));
    }

    @Test
    void shouldReturnOneRecipeByIngredient() {
        Recipe recipeToFind = recipeList.get(0);
        when(recipeDAO.findAllByIngredients_nameContainsIgnoreCase("pirospaprika")).thenReturn(List.of(recipeToFind));
        RecipeDTO expectedRecipeDTO = convertRecipeListToDto(List.of(recipeToFind)).get(0);
        when(recipeMapper.toDTO(recipeToFind)).thenReturn(expectedRecipeDTO);
        assertEquals(expectedRecipeDTO, recipeService.getRecipeByIngredient("pirospaprika").get(0));
    }

    @Test
    void shouldReturnTwoRecipeByIngredient() {
        List<Recipe> expectedRecipes = List.of(recipeList.get(0), recipeList.get(1));
        when(recipeDAO.findAllByIngredients_nameContainsIgnoreCase("olaj")).thenReturn(expectedRecipes);
        List<RecipeDTO> expectedRecipeDTOList = convertRecipeListToDto(expectedRecipes);
        for (int i = 0; i < expectedRecipes.size(); i++) {
            doReturn(convertRecipeListToDto(expectedRecipes).get(i)).when(recipeMapper).toDTO(expectedRecipes.get(i));
        }
        assertEquals(expectedRecipeDTOList, recipeService.getRecipeByIngredient("olaj"));
        assertEquals(2, recipeService.getRecipeByIngredient("olaj").size());
    }

    @Test
    void shouldReturnTwoRecipesWithRecipeTypeByIngredient() {
        List<Recipe> expectedRecipes = List.of(recipeList.get(0), recipeList.get(1));
        List<RecipeDTO> expectedRecipeDtos = convertRecipeListToDto(expectedRecipes);
        when(recipeDAO.findAllByRecipeTypeIsAndIngredients_nameContainsIgnoreCase(RecipeType.EBÉD, "hagyma")).thenReturn(expectedRecipes);
        for (int i = 0; i < expectedRecipes.size(); i++) {
            doReturn(convertRecipeListToDto(expectedRecipes).get(i)).when(recipeMapper).toDTO(expectedRecipes.get(i));
        }
        List<RecipeDTO> actualRecipeDtos = recipeService.getRecipesWithRecipeTypeByIngredient(RecipeType.EBÉD, "hagyma");
        assertEquals(expectedRecipeDtos, actualRecipeDtos);
        assertEquals(2, actualRecipeDtos.size());
    }

    @Test
    void shouldReturnOneRecipesWithRecipeTypeByIngredient() {
        List<Recipe> expectedRecipes = List.of(recipeList.get(0));
        List<RecipeDTO> expectedRecipeDtos = convertRecipeListToDto(expectedRecipes);
        when(recipeDAO.findAllByRecipeTypeIsAndIngredients_nameContainsIgnoreCase(RecipeType.EBÉD, "rizs")).thenReturn(expectedRecipes);
        for (int i = 0; i < expectedRecipes.size(); i++) {
            doReturn(convertRecipeListToDto(expectedRecipes).get(i)).when(recipeMapper).toDTO(expectedRecipes.get(i));
        }
        List<RecipeDTO> actualRecipeDtos = recipeService.getRecipesWithRecipeTypeByIngredient(RecipeType.EBÉD, "rizs");
        assertEquals(expectedRecipeDtos, actualRecipeDtos);
        assertEquals(1, actualRecipeDtos.size());
    }

    @Test
    void shouldReturnEmptyRecipesWithRecipeTypeByIngredient() {
        List<Recipe> expectedRecipes = new ArrayList<>();
        List<RecipeDTO> expectedRecipeDtos = convertRecipeListToDto(expectedRecipes);
        when(recipeDAO.findAllByRecipeTypeIsAndIngredients_nameContainsIgnoreCase(RecipeType.EBÉD, "tojás")).thenReturn(expectedRecipes);
        for (int i = 0; i < expectedRecipes.size(); i++) {
            doReturn(convertRecipeListToDto(expectedRecipes).get(i)).when(recipeMapper).toDTO(expectedRecipes.get(i));
        }
        List<RecipeDTO> actualRecipeDtos = recipeService.getRecipesWithRecipeTypeByIngredient(RecipeType.EBÉD, "tojás");
        assertEquals(expectedRecipeDtos, actualRecipeDtos);
        assertEquals(0, actualRecipeDtos.size());

    }

    @Test
    void addNewRecipeIsSuccessful() {
        Recipe recipeToAdd = recipeList.get(0);
        RecipeDTO recipeDTOToAdd = convertRecipeListToDto(List.of(recipeToAdd)).get(0);
        recipeService.addNewRecipe(recipeDTOToAdd);
        verify(recipeDAO, times(1)).save(recipeToAdd);
    }
    @Test
    void updateRecipeIsSuccessful() {
        Recipe recipeToUpdate = recipeList.get(0);
        RecipeDTO recipeDTOToUpdate = convertRecipeListToDto(List.of(recipeToUpdate)).get(0);
        when(recipeDAO.findById(1L)).thenReturn(Optional.of(recipeToUpdate));
        when(recipeDAO.save(any(Recipe.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Optional<Recipe> updatedRecipeOptional = recipeService.updateRecipe(1L, recipeDTOToUpdate);
        assertTrue(updatedRecipeOptional.isPresent());
        Recipe updatedRecipe = updatedRecipeOptional.get();
        assertEquals(1L, updatedRecipe.getId());
        assertEquals(recipeDTOToUpdate.getName(), updatedRecipe.getName());
        assertEquals(recipeToUpdate.getRecipeType(), updatedRecipe.getRecipeType());
        assertEquals(recipeToUpdate.getText(), updatedRecipe.getText());
        Set<String> ingredientNames = updatedRecipe.getIngredients().stream()
                .map(Ingredient::getName)
                .collect(Collectors.toSet());
        assertTrue(recipeDTOToUpdate.getIngredientNames().containsAll(ingredientNames));
    }
    @Test
    void deleteRecipeIsSuccessful() {
        recipeService.deleteRecipe(1L);
        verify(recipeDAO, times(1)).deleteById(1L);
    }
}