package com.example.recipe.feature_recipe.domain.use_case

import com.example.recipe.feature_recipe.domain.model.InvalidRecipeException
import com.example.recipe.feature_recipe.domain.model.Recipe
import com.example.recipe.feature_recipe.domain.repository.RecipeRepository

class AddRecipe(
    private val repository: RecipeRepository
) {

    @Throws(InvalidRecipeException::class)
    suspend operator fun invoke(recipe: Recipe) {
        if(recipe.recipeTitle.isBlank()) {
            throw InvalidRecipeException("The title of the recipe can't be empty.")
        }
        if(recipe.recipeContent.isBlank()) {
            throw InvalidRecipeException("The content of the recipe can't be empty.")
        }
        repository.insertRecipe(recipe)
    }
}