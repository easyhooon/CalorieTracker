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
import com.kenshi.onboarding_presentation.age.AgeScreen
import com.kenshi.onboarding_presentation.gender.GenderScreen
import com.kenshi.onboarding_presentation.welcome.WelcomeScreen
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

                        }

                        composable(Route.WEIGHT) {

                        }

                        composable(Route.NUTRIENT_GOAL) {

                        }

                        composable(Route.ACTIVITY) {

                        }

                        composable(Route.GOAL) {

                        }

                        composable(Route.TRACKER_OVERVIEW) {

                        }

                        composable(Route.SEARCH) {

                        }
                    }
                }
            }
        }
    }
}
