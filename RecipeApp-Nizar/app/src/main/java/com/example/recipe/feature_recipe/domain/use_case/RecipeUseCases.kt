package com.example.recipe.feature_recipe.domain.use_case

data class RecipeUseCases(
    val getRecipes: GetRecipes,
    val deleteRecipe: DeleteRecipe,
    val addRecipe: AddRecipe,
    val getRecipe: GetRecipe
)
