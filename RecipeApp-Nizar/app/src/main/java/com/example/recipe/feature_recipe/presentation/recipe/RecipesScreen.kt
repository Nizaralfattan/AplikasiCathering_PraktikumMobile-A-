package com.example.recipe.feature_recipe.presentation.recipe

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.recipe.feature_recipe.presentation.recipe.components.OrderSection
import com.example.recipe.feature_recipe.presentation.recipe.components.RecipeItem
import com.example.recipe.feature_recipe.presentation.util.Screen
import com.example.recipe.ui.theme.LightGreen
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun RecipesScreen(
    navController: NavController,
    viewModel: RecipesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditRecipeScreen.route)
                },
                backgroundColor = MaterialTheme.colors.error
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add recipe")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = LightGreen)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Recipe",
                    style = MaterialTheme.typography.h4.copy(color = Color.Black),
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                )
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    recipesOrder = state.recipesOrder,
                    onOrderChange = {
                        viewModel.onEvent(RecipesEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.recipes) { recipe ->
                    RecipeItem(
                        recipe = recipe,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp,)
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditRecipeScreen.route +
                                            "?recipeId=${recipe.id}&recipeColor=${recipe.color}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(RecipesEvent.DeleteRecipe(recipe))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(RecipesEvent.RestoreRecipe)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}