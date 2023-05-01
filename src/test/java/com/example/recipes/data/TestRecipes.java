package com.example.recipes.data;

import com.example.recipes.testmodels.Recipe;
import com.example.recipes.testmodels.RecipeType;

import java.util.List;

public interface TestRecipes {
    Recipe rizseshús = new Recipe(1L, "rizseshús", RecipeType.EBÉD, "készítsünk rizseshúst",
            List.of("olaj", "hagyma", "pirospaprika", "csirkemell", "rizs"));
    Recipe brokkolisTészta = new Recipe(2L, "brokkolis tészta", RecipeType.EBÉD,
            "készítsünk brokkolis tésztát", List.of("hagyma", "fokhagyma", "brokkoli", "tészta", "tejszín"));
    Recipe sajtosSzendvics = new Recipe(3L, "sajtos szendvics", RecipeType.REGGELI,
            "készítsünk sajtos szendvicset", List.of("teljes kíőrlésű kenyér", "margarin", "light sajt"));
    Recipe gyümölcsösJoghurt = new Recipe(4L, "gyümölcsös joghurt", RecipeType.TÍZORAI,
            "keverjük össze a joghurtot a gyümölccsel", List.of("gyümölcs", "light joghurt"));
    Recipe korpovitCottage = new Recipe(5L, "korpovit keksz cottage cheese-zel", RecipeType.UZSONNA,
            "4 db keksz, 100 g cottage", List.of("korpovit keksz", "cottage"));
    Recipe csirkemellesSaláta = new Recipe(6L, "csirkemelles saláta", RecipeType.VACSORA,
            "a csirkemellet megsütjük és összekeverjük a salátával",
            List.of("csirkemell, salátakeverék, teljes kiőrlésű kenyér"));

}
