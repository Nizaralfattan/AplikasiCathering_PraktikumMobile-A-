package com.example.recipe.feature_recipe.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipe.ui.theme.*

@Entity
data class Recipe(
    val recipeTitle: String,
    val recipeContent: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val recipeColor = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidRecipeException(message: String): Exception(message)