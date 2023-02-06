package com.kenshi.calorietracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.kenshi.calorietracker.navigation.CoreFeatureNavigator
import com.kenshi.calorietracker.navigation.RootNavGraph
import com.kenshi.calorietracker.ui.theme.CalorieTrackerTheme
import com.kenshi.core.domain.preferences.Preferences
import com.kenshi.onboarding_presentation.OnboardingNavGraph
import com.kenshi.tracker_presentation.TrackerNavGraph
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                // for snackbar state
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    /*
                    NavHost(
                        navController = navController,
                        startDestination = if (showShowOnboarding) {
                            Route.WELCOME
                        } else {
                            Route.TRACKER_OVERVIEW
                        }
                    ) {
                        composable(Route.WELCOME) {
                            // 문법 확인
                            WelcomeScreen(onNextClick = {
                                navController.navigate(Route.GENDER)
                            })
                        }

                        composable(Route.GENDER) {
                            GenderScreen(onNextClick = {
                                navController.navigate(Route.AGE)
                            })
                        }

                        composable(Route.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.HEIGHT)
                                }
                            )
                        }

                        composable(Route.HEIGHT) {
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.WEIGHT)
                                }
                            )
                        }

                        composable(Route.WEIGHT) {
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.ACTIVITY)
                                }
                            )
                        }

                        composable(Route.ACTIVITY) {
                            ActivityScreen(onNextClick = {
                                navController.navigate(Route.GOAL)
                            })
                        }

                        composable(Route.GOAL) {
                            GoalScreen(onNextClick = {
                                navController.navigate(Route.NUTRIENT_GOAL)
                            })
                        }

                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.TRACKER_OVERVIEW)
                                }
                            )
                        }

                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(
                                onNavigateToSearch = { mealName, day, month, year ->
                                    navController.navigate(
                                        Route.SEARCH + "/$mealName" + "/$day" + "/$month" + "/$year"
                                    )
                                }
                            )
                        }

                        composable(
                            Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
                                    type = NavType.IntType
                                },
                            )
                        ) {
                            val mealName = it.arguments?.getString("mealName")!!
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year = it.arguments?.getInt("year")!!

                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }*/

                    DestinationsNavHost(
                        navController = navController,
                        navGraph = RootNavGraph,
                        startRoute = if(shouldShowOnboarding) OnboardingNavGraph else TrackerNavGraph,
                        dependenciesContainerBuilder = {
                            dependency(scaffoldState)
                            dependency(CoreFeatureNavigator(destination, navController))
                        }
                    )
                }
            }
        }
    }
}

