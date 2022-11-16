package com.kenshi.tracker_domain.use_case

// UserCase 가 많으므로 이를 data class 로 관리
data class TrackerUseCases (
    val trackFood: TrackFood,
    val searchFood: SearchFood,
    val getFoodsForDate: GetFoodsForDate,
    val deleteTrackedFood: DeleteTrackedFood,
    val calculateMealNutrients: CalculateMealNutrients
)
