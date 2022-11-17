package com.kenshi.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kenshi.calorietracker.navigation.navigate
import com.kenshi.calorietracker.ui.theme.CalorieTrackerTheme
import com.kenshi.core.navigation.Route
import com.kenshi.onboarding_presentation.activity.ActivityScreen
import com.kenshi.onboarding_presentation.age.AgeScreen
import com.kenshi.onboarding_presentation.gender.GenderScreen
import com.kenshi.onboarding_presentation.goal.GoalScreen
import com.kenshi.onboarding_presentation.height.HeightScreen
import com.kenshi.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.kenshi.onboarding_presentation.weight.WeightScreen
import com.kenshi.onboarding_presentation.welcome.WelcomeScreen
import com.kenshi.tracker_presentation.tracker_overview.TrackerOverViewScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                // for snackbar
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.WELCOME
                    ) {
                        composable(Route.WELCOME) {
                            // 문법 확인
                            WelcomeScreen(onNavigate = navController::navigate)
                        }

                        composable(Route.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }

                        composable(Route.GENDER) {
                            GenderScreen(onNavigate = navController::navigate)
                        }

                        composable(Route.HEIGHT) {
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }

                        composable(Route.WEIGHT) {
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }

                        composable(Route.ACTIVITY) {
                            ActivityScreen(onNavigate = navController::navigate)
                        }

                        composable(Route.GOAL) {
                            GoalScreen(onNavigate = navController::navigate)
                        }

                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }

                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverViewScreen(
                                onNavigate = navController::navigate
                            )
                        }

                        composable(Route.SEARCH) {

                        }
                    }
                }
            }
        }
    }
}

