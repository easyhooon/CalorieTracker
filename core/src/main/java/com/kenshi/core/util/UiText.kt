package com.kenshi.core.util

import android.content.Context

sealed class UiText {
    data class DynamicString(val text: String): UiText()
    data class StringResource(val resId: Int): UiText()

    // Ui Layer unwrapped this (ui layer have context)
    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> text
            is StringResource -> context.getString(resId)
        }
    }
}
