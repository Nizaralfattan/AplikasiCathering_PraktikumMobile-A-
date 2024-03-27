package com.example.recipe.feature_recipe.domain.use_case

import com.example.recipe.feature_recipe.domain.model.Recipe
import com.example.recipe.feature_recipe.domain.repository.RecipeRepository

class DeleteRecipe(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(recipe: Recipe) {
        repository.deleteRecipe(recipe)
    }
}