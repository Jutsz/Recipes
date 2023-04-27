package com.example.recipes.data;

import com.example.recipes.testmodels.Recipe;
import com.example.recipes.testmodels.RecipeType;

import java.util.List;

public interface TestRecipes {
    Recipe rizseshús = new Recipe(null, "rizseshús", RecipeType.EBÉD, "készítsünk rizseshúst",
            List.of("olaj", "hagyma", "pirospaprika", "csirkemell", "rizs"));
    Recipe brokkolisTészta = new Recipe(null, "brokkolis tészta", RecipeType.EBÉD,
            "készítsünk brokkolis tésztát", List.of("hagyma", "fokhagyma", "brokkoli", "tészta", "tejszín"));
    Recipe sajtosSzendvics = new Recipe(null, "sajtos szendvics", RecipeType.REGGELI,
            "készítsünk sajtos szendvicset", List.of("teljes kíőrlésű kenyér", "margarin", "light sajt"));
    Recipe gyümölcsösJoghurt = new Recipe(null, "gyümölcsös joghurt", RecipeType.TÍZORAI,
            "keverjük össze a joghurtot a gyümölccsel", List.of("gyümölcs", "light joghurt"));
    Recipe korpovitCottage = new Recipe(null, "korpovit keksz cottage cheese-zel", RecipeType.UZSONNA,
            "4 db keksz, 100 g cottage", List.of("korpovit keksz", "cottage"));
    Recipe csirkemellesSaláta = new Recipe(null, "csirkemelles saláta", RecipeType.VACSORA,
            "a csirkemellet megsütjük és összekeverjük a salátával",
            List.of("csirkemell, salátakeverék, teljes kiőrlésű kenyér"));

}
