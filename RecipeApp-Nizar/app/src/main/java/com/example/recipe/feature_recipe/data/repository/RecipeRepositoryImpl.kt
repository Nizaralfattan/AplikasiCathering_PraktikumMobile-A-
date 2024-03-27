package com.example.recipe.feature_recipe.data.repository

import com.example.recipe.feature_recipe.data.data_source.RecipeDao
import com.example.recipe.feature_recipe.domain.model.Recipe
import com.example.recipe.feature_recipe.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class RecipeRepositoryImpl(
    private val dao: RecipeDao
) : RecipeRepository {

    override fun getRecipes(): Flow<List<Recipe>> {
        return dao.getRecipes()
    }

    override suspend fun getRecipeById(id: Int): Recipe? {
        return dao.getRecipeById(id)
    }

    override suspend fun insertRecipe(note: Recipe) {
        dao.insertRecipe(note)
    }

    override suspend fun deleteRecipe(note: Recipe) {
        dao.deleteRecipe(note)
    }
}