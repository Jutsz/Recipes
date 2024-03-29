package com.example.recipes.service;

import com.example.recipes.mapper.IngredientMapper;
import com.example.recipes.model.Ingredient;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.dto.IngredientDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {
    @Mock
    private IngredientDAO ingredientDAO;

    @Mock
    private IngredientMapper ingredientMapper;

    @Mock
    private RecipeDAO recipeDAO;

    @InjectMocks
    private IngredientService ingredientService;

    private static List<Ingredient> ingredientList = new ArrayList<>();

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
        ingredientList= List.of(olaj, hagyma, fokhagyma, pirospaprika, csirkemell, rizs, brokkoli, tészta, tej, zabpehely, zsír, tojás);
    }

    @AfterEach
    void clear() {
        ingredientList = new ArrayList<>();
    }

    @Test
    void shouldReturnAllIngredient() {
        IngredientDTO ingredientDTO1 = new IngredientDTO();
        ingredientDTO1.setId(1L);
        ingredientDTO1.setName(ingredientList.get(0).getName());

        IngredientDTO ingredientDTO2 = new IngredientDTO();
        ingredientDTO2.setId(2L);
        ingredientDTO2.setName(ingredientList.get(1).getName());

        when(ingredientDAO.findAll()).thenReturn(ingredientList);
        when(ingredientMapper.toDTO(ingredientList.get(0))).thenReturn(ingredientDTO1);
        when(ingredientMapper.toDTO(ingredientList.get(1))).thenReturn(ingredientDTO2);

        List<IngredientDTO> ingredientDTOList = ingredientService.getAllIngredient();

        assertEquals(12, ingredientDTOList.size());
        assertEquals("olaj", ingredientDTOList.get(0).getName());
        assertEquals("hagyma", ingredientDTOList.get(1).getName());
    }

    @Test
    void shouldReturnIngredientById() {
        Long id = 1L;
        Ingredient ingredient = ingredientList.get(0);
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(id);
        when(ingredientDAO.findById(id)).thenReturn(Optional.of(ingredient));
        when(ingredientMapper.toDTO(ingredient)).thenReturn(ingredientDTO);

        Optional<IngredientDTO> result = ingredientService.getIngredientById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void shouldReturnIngredientByName() {
        Ingredient ingredient = ingredientList.get(0);
        IngredientDTO ingredientDTO = new IngredientDTO();
        String ingredientName = ingredient.getName();
        ingredientDTO.setName(ingredientName);
        when(ingredientDAO.findAllByNameContainsIgnoreCase(ingredientName)).thenReturn(List.of(ingredient));
        when(ingredientMapper.toDTO(ingredient)).thenReturn(ingredientDTO);

        List<IngredientDTO> result = ingredientService.getIngredientByName(ingredientName);

        assertEquals("olaj", result.get(0).getName());

    }

    @Test
    void addNewIngredientIsSuccessful() {
        Ingredient ingredientToAdd = ingredientList.get(0);
        IngredientDTO ingredientDTOToAdd = new IngredientDTO(1L, ingredientToAdd.getName(), new HashSet<>());
        ingredientService.addIngredient(ingredientDTOToAdd);
        verify(ingredientDAO, times(1)).save(ingredientToAdd);
    }

    @Test
    void updateIngredientIsSuccessful() {
        Ingredient originalIngredientToUpdate = ingredientList.get(0);
        IngredientDTO ingredientDTOToUpdate = new IngredientDTO(1L, originalIngredientToUpdate.getName()+"_updated", new HashSet<>());
        when(ingredientDAO.findById(1L)).thenReturn(Optional.of(originalIngredientToUpdate));
        when(ingredientDAO.save(any(Ingredient.class))).thenAnswer(invocation -> invocation.getArgument(0));
        assertFalse(originalIngredientToUpdate.getName().contains("updated"));
        Optional<Ingredient> updatedIngredientOptional = ingredientService.updateIngredient(1L, ingredientDTOToUpdate);
        assertTrue(updatedIngredientOptional.isPresent());
        Ingredient updatedIngredient = updatedIngredientOptional.get();
        assertEquals(1L, updatedIngredient.getId());
        assertEquals(ingredientDTOToUpdate.getName(), updatedIngredient.getName());
        assertTrue(updatedIngredient.getName().contains("updated"));

    }

    @Test
    void deleteIngredient_removesIngredientFromRecipeAndDeletesFromRepository() {

        Long ingredientId = 1L;
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        ingredient.setName("Test Ingredient");
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Test Recipe");
        Set<Ingredient> ingredientSet = new HashSet<>();
        ingredientSet.add(ingredient);
        recipe.setIngredients(ingredientSet);
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(recipe);
        when(ingredientDAO.findById(ingredientId)).thenReturn(Optional.of(ingredient));
        when(recipeDAO.findAllByIngredients_nameContainsIgnoreCase("Test Ingredient")).thenReturn(recipeList);

        ingredientService.deleteIngredient(ingredientId);

        verify(ingredientDAO).deleteById(ingredientId);
        assert(recipe.getIngredients().isEmpty());
    }
}