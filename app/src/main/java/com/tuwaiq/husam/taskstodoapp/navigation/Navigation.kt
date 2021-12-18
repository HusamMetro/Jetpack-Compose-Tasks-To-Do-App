package com.tuwaiq.husam.taskstodoapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.tuwaiq.husam.taskstodoapp.navigation.destinations.listComposable
import com.tuwaiq.husam.taskstodoapp.navigation.destinations.splashComposable
import com.tuwaiq.husam.taskstodoapp.navigation.destinations.taskComposable
import com.tuwaiq.husam.taskstodoapp.ui.screens.settings.SettingsScreen
import com.tuwaiq.husam.taskstodoapp.ui.screens.challenges.ChallengesScreen
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants.SPLASH_SCREEN

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController) {
        Screens(navController)
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN
    ) {
        splashComposable(
            navigateToTaskScreen = screen.splash,
            sharedViewModel = sharedViewModel
        )
        listComposable(
            navigateToTaskScreen = screen.list,
            sharedViewModel = sharedViewModel,
            navController = navController
        )
        taskComposable(
            navigateToListScreen = screen.task,
            sharedViewModel = sharedViewModel
        )
        /* settingsComposable(
             sharedViewModel = sharedViewModel
         )
         suggestedComposable(
             sharedViewModel = sharedViewModel
         )*/
        /*composable(route = BottomBarScreen.Home.route) {
            ListScreen(
                action = Action.NO_ACTION,
                navigateToTaskScreen = { taskId ->
                    navController.navigate("task/$taskId")
                },
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }*/
        composable(route = BottomBarScreen.Challenges.route) {
            ChallengesScreen(
                sharedViewModel = sharedViewModel,
                navController = navController
            )
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen(
                sharedViewModel = sharedViewModel,
                navController = navController
            )
        }
    }
}
