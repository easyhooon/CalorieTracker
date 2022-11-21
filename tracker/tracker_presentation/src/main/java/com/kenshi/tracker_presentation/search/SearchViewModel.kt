package com.kenshi.tracker_presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenshi.core.domain.use_case.FilterOutDigits
import com.kenshi.core.util.UiEvent
import com.kenshi.core.util.UiText
import com.kenshi.tracker_domain.use_case.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO 음식을 하나 등록하면 아침 점심 저녁 간식 모두에 저장되는 이슈
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDigits: FilterOutDigits
): ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.OnQueryChange -> {
                state = state.copy(query = event.query)
            }
            is SearchEvent.OnAmountForFoodChange -> {
                state = state.copy(
                    trackableFood = state.trackableFood.map {
                        if (it.food == event.food) {
                            it.copy(amount = event.amount)
                        } else it
                    }
                )
            }
            is SearchEvent.OnSearch -> {
                executeSearch()
            }
            is SearchEvent.OnToggleTrackableFood -> {
                state = state.copy(
                    trackableFood = state.trackableFood.map {
                        if (it.food == event.food) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }
            is SearchEvent.OnSearchFocusChange -> {
                state = state.copy(
                    isHintVisible = !event.isFocused && state.query.isBlank()
                )
            }

            is SearchEvent.OnTrackFoodClick -> {
                trackFood(event)
            }
        }
    }

    private fun executeSearch() = viewModelScope.launch {
        state = state.copy(
            isSearching = true,
            trackableFood = emptyList()
        )
        trackerUseCases
            .searchFood(state.query)
            .onSuccess { foods ->
                state = state.copy(
                    trackableFood = foods.map {
                        TrackableFoodUiState(it)
                    },
                    isSearching = false,
                    query = ""
                )
            }
            .onFailure {
                state = state.copy(isSearching = false)
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        UiText.StringResource(com.kenshi.core.R.string.error_something_went_wrong)
                    )
                )
            }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodClick) = viewModelScope.launch {
        val uiState = state.trackableFood.find { it.food == event.food }
        trackerUseCases.trackFood(
            food = uiState?.food ?: return@launch,
            amount = uiState.amount.toIntOrNull() ?: return@launch,
            mealType = event.mealType,
            date = event.date
        )
        _uiEvent.send(UiEvent.NavigateUp)
    }
}