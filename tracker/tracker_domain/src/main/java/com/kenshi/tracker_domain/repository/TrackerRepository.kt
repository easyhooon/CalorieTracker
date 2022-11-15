package com.kenshi.tracker_domain.repository

import com.kenshi.tracker_domain.model.TrackableFood
import com.kenshi.tracker_domain.model.TrackedFood
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

// 문제
// 반환타입인 Result 안에 dto 클래스를 넣고 싶지만 도메인 레이어는 어느 레이어의 의존을 하면 안되므로
// data 클래스의 dto 클래스를 함부로 참조하면 안된다. 그럼 어떻게 해결할 것인가
// domain 모델을 만들면 해결 (domain 모델은 모든 레이어에서 접근이 가능한 모델임)
interface TrackerRepository {

    suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<List<TrackableFood>>

    suspend fun insertTrackedFood(food: TrackedFood)

    suspend fun deleteTrackedFood(food: TrackedFood)

    fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>>
}