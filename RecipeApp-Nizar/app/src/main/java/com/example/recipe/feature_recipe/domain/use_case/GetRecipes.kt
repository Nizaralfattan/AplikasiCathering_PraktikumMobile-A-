package com.example.recipe.feature_recipe.domain.use_case

import com.example.recipe.feature_recipe.domain.model.Recipe
import com.example.recipe.feature_recipe.domain.repository.RecipeRepository
import com.example.recipe.feature_recipe.domain.util.RecipeOrder
import com.example.recipe.feature_recipe.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRecipes(
    private val repository: RecipeRepository
) {

    operator fun invoke(
        recipesOrder: RecipeOrder = RecipeOrder.Date(OrderType.Descending)
    ): Flow<List<Recipe>> {
        return repository.getRecipes().map { recipess ->
            when(recipesOrder.orderType) {
                is OrderType.Ascending -> {
                    when(recipesOrder) {
                        is RecipeOrder.Title -> recipess.sortedBy { it.recipeTitle.lowercase() }
                        is RecipeOrder.Date -> recipess.sortedBy { it.timestamp }
                        is RecipeOrder.Color -> recipess.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(recipesOrder) {
                        is RecipeOrder.Title -> recipess.sortedByDescending { it.recipeTitle.lowercase() }
                        is RecipeOrder.Date -> recipess.sortedByDescending { it.timestamp }
                        is RecipeOrder.Color -> recipess.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}