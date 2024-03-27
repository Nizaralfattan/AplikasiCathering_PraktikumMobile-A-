package com.example.recipe.feature_recipe.presentation.recipe

import com.example.recipe.feature_recipe.domain.model.Recipe
import com.example.recipe.feature_recipe.domain.util.RecipeOrder

sealed class RecipesEvent {
    data class Order(val recipesOrder: RecipeOrder): RecipesEvent()
    data class DeleteRecipe(val recipe: Recipe): RecipesEvent()
    object RestoreRecipe: RecipesEvent()
    object ToggleOrderSection: RecipesEvent()
}
