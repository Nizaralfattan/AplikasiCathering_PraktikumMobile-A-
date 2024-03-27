package com.example.recipe.feature_recipe.presentation.recipe

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe.feature_recipe.domain.model.Recipe
import com.example.recipe.feature_recipe.domain.use_case.RecipeUseCases
import com.example.recipe.feature_recipe.domain.util.OrderType
import com.example.recipe.feature_recipe.domain.util.RecipeOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
) : ViewModel() {

    private val _state = mutableStateOf(RecipesState())
    val state: State<RecipesState> = _state

    private var recentlyDeletedRecipe: Recipe? = null

    private var getRecipesJob: Job? = null

    init {
        getRecipes(RecipeOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: RecipesEvent) {
        when (event) {
            is RecipesEvent.Order -> {
                if (state.value.recipesOrder::class == event.recipesOrder::class &&
                    state.value.recipesOrder.orderType == event.recipesOrder.orderType
                ) {
                    return
                }
                getRecipes(event.recipesOrder)
            }
            is RecipesEvent.DeleteRecipe -> {
                viewModelScope.launch {
                    recipeUseCases.deleteRecipe(event.recipe)
                    recentlyDeletedRecipe = event.recipe
                }
            }
            is RecipesEvent.RestoreRecipe -> {
                viewModelScope.launch {
                    recipeUseCases.addRecipe(recentlyDeletedRecipe ?: return@launch)
                    recentlyDeletedRecipe = null
                }
            }
            is RecipesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getRecipes(recipesOrder: RecipeOrder) {
        getRecipesJob?.cancel()
        getRecipesJob = recipeUseCases.getRecipes(recipesOrder)
            .onEach { recipes ->
                _state.value = state.value.copy(
                    recipes = recipes,
                    recipesOrder = recipesOrder
                )
            }
            .launchIn(viewModelScope)
    }
}