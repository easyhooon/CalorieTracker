package com.kenshi.tracher_data.repository

import com.kenshi.tracher_data.local.TrackerDao
import com.kenshi.tracher_data.mapper.toTrackableFood
import com.kenshi.tracher_data.mapper.toTrackedFood
import com.kenshi.tracher_data.mapper.toTrackedFoodEntity
import com.kenshi.tracher_data.remote.OpenFoodApi
import com.kenshi.tracker_domain.model.TrackableFood
import com.kenshi.tracker_domain.model.TrackedFood
import com.kenshi.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
) : TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            // repository interface 에서 자정한 type 으로 return 하기 위해선
            // mapper 가 필요
            Result.success(
                searchDto.products
                    .filter {
                        val calculatedCalories =
                            it.nutriments.carbohydrates100g * 4f +
                                    it.nutriments.proteins100g * 4f +
                                    it.nutriments.fat100g * 9f
                        val lowerBound = calculatedCalories * 0.99f
                        val upperBound = calculatedCalories * 1.01f
                        it.nutriments.energyKcal100g in (lowerBound..upperBound)
                    }
                    .mapNotNull { it.toTrackableFood() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year
        ).map { entities ->
            // list의 모든 trackedFoodEntity 객체들을 TrackedFood로 변환
            entities.map { it.toTrackedFood() }
        }
    }
}