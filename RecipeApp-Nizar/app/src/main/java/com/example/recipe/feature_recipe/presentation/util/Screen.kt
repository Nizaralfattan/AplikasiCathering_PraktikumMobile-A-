package com.example.recipe.feature_recipe.presentation.util

sealed class Screen(val route: String) {
    object RecipesScreen: Screen("recipes_screen")
    object AddEditRecipeScreen: Screen("add_edit_recipe_screen")
}
