package com.example.recipe.feature_recipe.domain.use_case

import com.example.recipe.feature_recipe.domain.model.Recipe
import com.example.recipe.feature_recipe.domain.repository.RecipeRepository

class GetRecipe(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(id: Int): Recipe? {
        return repository.getRecipeById(id)
    }
}