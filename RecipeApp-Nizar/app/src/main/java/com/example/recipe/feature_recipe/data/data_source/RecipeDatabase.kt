package com.example.recipe.feature_recipe.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipe.feature_recipe.domain.model.Recipe

@Database(
    entities = [Recipe::class],
    version = 2
)
abstract class RecipeDatabase: RoomDatabase() {

    abstract val recipeDao: RecipeDao

    companion object {
        const val DATABASE_NAME = "recipe_db"
    }
}