package com.kenshi.core.util

sealed class UiEvent {
    object Success: UiEvent()
    object NavigateUp: UiEvent()
    // data class ShowSnackbar(val message: String): UiEvent()
    data class ShowSnackbar(val message: UiText): UiEvent()
}