package com.example.recipes.data;

import com.example.recipes.testmodels.Recipe;
import com.example.recipes.testmodels.RecipeType;

import java.util.List;

public interface TestRecipes {
    Recipe rizseshús = new Recipe(null, "rizseshús", RecipeType.EBÉD, "készítsünk rizseshúst", List.of("olaj, hagyma, pirospaprika, csirkemell, rizs"));
}
