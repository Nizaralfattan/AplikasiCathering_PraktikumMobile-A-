package com.example.recipe.feature_recipe.presentation.recipe.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipe.feature_recipe.domain.util.OrderType
import com.example.recipe.feature_recipe.domain.util.RecipeOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    recipesOrder: RecipeOrder = RecipeOrder.Date(OrderType.Descending),
    onOrderChange: (RecipeOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = recipesOrder is RecipeOrder.Title,
                onSelect = { onOrderChange(RecipeOrder.Title(recipesOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = recipesOrder is RecipeOrder.Date,
                onSelect = { onOrderChange(RecipeOrder.Date(recipesOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = recipesOrder is RecipeOrder.Color,
                onSelect = { onOrderChange(RecipeOrder.Color(recipesOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = recipesOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(recipesOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = recipesOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(recipesOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}