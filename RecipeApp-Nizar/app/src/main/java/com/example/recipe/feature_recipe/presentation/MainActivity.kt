package com.example.recipe.feature_recipe.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipe.feature_recipe.presentation.add_edit_recipe.AddEditRecipeScreen
import com.example.recipe.feature_recipe.presentation.recipe.RecipesScreen
import com.example.recipe.feature_recipe.presentation.util.Screen
import com.example.recipe.ui.theme.RecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.RecipesScreen.route
                    ) {
                        composable(route = Screen.RecipesScreen.route) {
                            RecipesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditRecipeScreen.route +
                                    "?recipeId={recipeId}&recipeColor={recipeColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "recipeId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "recipeColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("recipeColor") ?: -1
                            AddEditRecipeScreen(
                                navController = navController,
                                recipeColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}
