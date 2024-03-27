package com.example.recipe.feature_recipe.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
