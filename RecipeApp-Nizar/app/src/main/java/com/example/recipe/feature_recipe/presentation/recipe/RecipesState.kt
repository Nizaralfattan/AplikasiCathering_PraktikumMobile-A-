package com.example.recipe.feature_recipe.presentation.recipe

import com.example.recipe.feature_recipe.domain.model.Recipe
import com.example.recipe.feature_recipe.domain.util.OrderType
import com.example.recipe.feature_recipe.domain.util.RecipeOrder

data class RecipesState(
    val recipes: List<Recipe> = emptyList(),
    val recipesOrder: RecipeOrder = RecipeOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
