package com.kenshi.calorietracker.navigation

import androidx.compose.ui.ExperimentalComposeUiApi
import com.kenshi.onboarding_presentation.OnboardingNavGraph
import com.kenshi.tracker_presentation.TrackerNavGraph
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

@ExperimentalComposeUiApi
object RootNavGraph: NavGraphSpec {

    override val route = "root"

    override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()

    override val startRoute = OnboardingNavGraph

    override val nestedNavGraphs = listOf(
        OnboardingNavGraph,
        TrackerNavGraph
    )
}