package com.example.recipe.feature_recipe.domain.repository

import com.example.recipe.feature_recipe.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun getRecipes(): Flow<List<Recipe>>

    suspend fun getRecipeById(id: Int): Recipe?

    suspend fun insertRecipe(note: Recipe)

    suspend fun deleteRecipe(note: Recipe)
}