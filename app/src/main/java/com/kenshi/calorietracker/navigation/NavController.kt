package com.kenshi.calorietracker.navigation

import androidx.navigation.NavController
import com.kenshi.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}