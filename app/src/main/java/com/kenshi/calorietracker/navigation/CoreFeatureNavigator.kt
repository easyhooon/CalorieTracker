package com.kenshi.calorietracker.navigation

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import com.kenshi.onboarding_presentation.OnboardingNavigator
import com.kenshi.onboarding_presentation.destinations.ActivityScreenDestination
import com.kenshi.onboarding_presentation.destinations.AgeScreenDestination
import com.kenshi.onboarding_presentation.destinations.GenderScreenDestination
import com.kenshi.onboarding_presentation.destinations.GoalScreenDestination
import com.kenshi.onboarding_presentation.destinations.HeightScreenDestination
import com.kenshi.onboarding_presentation.destinations.NutrientGoalScreenDestination
import com.kenshi.onboarding_presentation.destinations.OnboardingDestination
import com.kenshi.onboarding_presentation.destinations.WeightScreenDestination
import com.kenshi.onboarding_presentation.destinations.WelcomeScreenDestination
import com.kenshi.tracker_presentation.destinations.SearchScreenDestination
import com.kenshi.tracker_presentation.destinations.TrackerOverviewScreenDestination
import com.kenshi.tracker_presentation.search.SearchScreenNavigator
import com.kenshi.tracker_presentation.tracker_overview.TrackerOverviewScreenNavigator
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.DestinationSpec

@ExperimentalComposeUiApi
class CoreFeatureNavigator(
    private val currentDestination: DestinationSpec<*>,
    private val navController: NavController
) : SearchScreenNavigator, TrackerOverviewScreenNavigator, OnboardingNavigator {
    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun navigateToNextScreen() {
        currentDestination as? OnboardingDestination ?: throw RuntimeException("Trying to use Onboarding navigator from a non onboarding screen")
        val nextDirection = when (currentDestination) {
            WelcomeScreenDestination -> GenderScreenDestination
            GenderScreenDestination -> AgeScreenDestination
            AgeScreenDestination -> HeightScreenDestination
            HeightScreenDestination -> WeightScreenDestination
            WeightScreenDestination -> ActivityScreenDestination
            ActivityScreenDestination -> GoalScreenDestination
            GoalScreenDestination -> NutrientGoalScreenDestination
            NutrientGoalScreenDestination -> TrackerOverviewScreenDestination
        }

        navController.navigateTo(nextDirection)
    }

    override fun navigateToSearch(mealName: String, dayOfMonth: Int, month: Int, year: Int) {
        navController.navigateTo(
            SearchScreenDestination(
                mealName = mealName,
                dayOfMonth = dayOfMonth,
                month = month,
                year = year
            )
        )
    }

}