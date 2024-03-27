package com.example.recipe.feature_recipe.presentation.add_edit_recipe

import androidx.compose.ui.focus.FocusState

sealed class AddEditRecipeEvent{
    data class EnteredTitle(val value: String): AddEditRecipeEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditRecipeEvent()
    data class EnteredContent(val value: String): AddEditRecipeEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditRecipeEvent()
    data class ChangeColor(val color: Int): AddEditRecipeEvent()
    object SaveRecipe: AddEditRecipeEvent()
}

