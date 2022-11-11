package com.kenshi.onboarding_presentation.height

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenshi.core.domain.preferences.Preferences
import com.kenshi.core.domain.use_case.FilterOutDigits
import com.kenshi.core.navigation.Route
import com.kenshi.core.util.UiEvent
import com.kenshi.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    var height by mutableStateOf("80")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHeightEnter(height: String) {
        if (height.length <= 3) {
            //usecase 로 선언했기 때문에 손쉽게 재활용이 가능하다
            this.height = filterOutDigits(height)
        }
    }

    fun onNextClick() = viewModelScope.launch {
        val heightNumber = height.toIntOrNull() ?: run {
            _uiEvent.send(
                // viewModel 에 context 를 참조해야하는 문제
                // -> helper class 를 통해 해결
                UiEvent.ShowSnackbar(
                    UiText.StringResource(com.kenshi.core.R.string.error_height_cant_be_empty)
                )
            )
            return@launch
        }
        preferences.saveHeight(heightNumber)
        _uiEvent.send(UiEvent.Navigate(Route.WEIGHT))
    }
}