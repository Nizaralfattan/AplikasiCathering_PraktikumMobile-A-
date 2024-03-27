package com.example.recipe.feature_recipe.presentation.add_edit_recipe

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe.feature_recipe.domain.model.InvalidRecipeException
import com.example.recipe.feature_recipe.domain.model.Recipe
import com.example.recipe.feature_recipe.domain.use_case.RecipeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditRecipeViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _recipeTitle = mutableStateOf(RecipeTextFieldState(
        hint = "Enter title..."
    ))
    val recipeTitle: State<RecipeTextFieldState> = _recipeTitle

    private val _recipeContent = mutableStateOf(RecipeTextFieldState(
        hint = "Enter some content"
    ))
    val recipeContent: State<RecipeTextFieldState> = _recipeContent

    private val _recipeColor = mutableStateOf(Recipe.recipeColor.random().toArgb())
    val recipeColor: State<Int> = _recipeColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentRecipeId: Int? = null

    init {
        savedStateHandle.get<Int>("recipeId")?.let { recipeId ->
            if(recipeId != -1) {
                viewModelScope.launch {
                    recipeUseCases.getRecipe(recipeId)?.also { recipe ->
                        currentRecipeId = recipe.id
                        _recipeTitle.value = recipeTitle.value.copy(
                            text = recipe.recipeTitle,
                            isHintVisible = false
                        )
                        _recipeContent.value = _recipeContent.value.copy(
                            text = recipe.recipeContent,
                            isHintVisible = false
                        )
                        _recipeColor.value = recipe.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditRecipeEvent) {
        when(event) {
            is AddEditRecipeEvent.EnteredTitle -> {
                _recipeTitle.value = recipeTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditRecipeEvent.ChangeTitleFocus -> {
                _recipeTitle.value = recipeTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            recipeTitle.value.text.isBlank()
                )
            }
            is AddEditRecipeEvent.EnteredContent -> {
                _recipeContent.value = _recipeContent.value.copy(
                    text = event.value
                )
            }
            is AddEditRecipeEvent.ChangeContentFocus -> {
                _recipeContent.value = _recipeContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _recipeContent.value.text.isBlank()
                )
            }
            is AddEditRecipeEvent.ChangeColor -> {
                _recipeColor.value = event.color
            }
            is AddEditRecipeEvent.SaveRecipe -> {
                viewModelScope.launch {
                    try {
                        recipeUseCases.addRecipe(
                            Recipe(
                                recipeTitle = recipeTitle.value.text,
                                recipeContent = recipeContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = recipeColor.value,
                                id = currentRecipeId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveRecipe)
                    } catch(e: InvalidRecipeException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save recipe"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveRecipe: UiEvent()
    }
}