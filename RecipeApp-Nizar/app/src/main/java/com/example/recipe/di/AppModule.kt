package com.example.recipe.di

import android.app.Application
import androidx.room.Room
import com.example.recipe.feature_recipe.data.data_source.RecipeDatabase
import com.example.recipe.feature_recipe.data.repository.RecipeRepositoryImpl
import com.example.recipe.feature_recipe.domain.repository.RecipeRepository
import com.example.recipe.feature_recipe.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): RecipeDatabase {
        return Room.databaseBuilder(
            app,
            RecipeDatabase::class.java,
            RecipeDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: RecipeDatabase): RecipeRepository {
        return RecipeRepositoryImpl(db.recipeDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: RecipeRepository): RecipeUseCases {
        return RecipeUseCases(
            getRecipes = GetRecipes(repository),
            deleteRecipe = DeleteRecipe(repository),
            addRecipe = AddRecipe(repository),
            getRecipe = GetRecipe(repository)
        )
    }
}