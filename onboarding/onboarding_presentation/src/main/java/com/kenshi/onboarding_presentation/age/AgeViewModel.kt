package com.kenshi.onboarding_presentation.age

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
class AgeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    var age by mutableStateOf("20")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    // usercase 로 빼야할 부분
//    fun onAgeEnter(age: String) {
//        if (age.length <= 3) {
//            this.age = age.filter { it.isDigit() }
//        }
//    }

    fun onAgeEnter(age: String) {
        if (age.length <= 3) {
            this.age = filterOutDigits(age)
        }
    }

    fun onNextClick() = viewModelScope.launch {
        val ageNumber = age.toIntOrNull() ?: run {
            _uiEvent.send(
                // viewModel 에 context 를 참조해야하는 문제
                // -> helper class 를 통해 해결
                UiEvent.ShowSnackbar(
                    UiText.StringResource(com.kenshi.core.R.string.error_age_cant_be_empty)
                )
            )
            return@launch
        }
        preferences.saveAge(ageNumber)
        _uiEvent.send(UiEvent.Navigate(Route.WEIGHT))
    }
}