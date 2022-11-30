package com.kenshi.calorietracker.repository

import com.kenshi.tracker_domain.model.TrackableFood
import com.kenshi.tracker_domain.model.TrackedFood
import com.kenshi.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.time.LocalDate
import kotlin.random.Random

// simulate behavior of real repository
class FakeTrackerRepository: TrackerRepository {

    var shouldReturnError = false

    private val trackedFood = mutableListOf<TrackedFood>()
    var searchResults = listOf<TrackableFood>()

    private val getFoodsForDateFlow = MutableSharedFlow<List<TrackedFood>>(replay = 1)

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return if (shouldReturnError) {
            Result.failure(Throwable())
        } else {
            Result.success(searchResults)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        trackedFood.add(food.copy(id = Random.nextInt()))
        getFoodsForDateFlow.emit(trackedFood)
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        trackedFood.remove(food)
        getFoodsForDateFlow.emit(trackedFood)
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return getFoodsForDateFlow
    }

}