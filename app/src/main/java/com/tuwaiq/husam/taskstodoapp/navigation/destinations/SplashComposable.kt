package com.tuwaiq.husam.taskstodoapp.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tuwaiq.husam.taskstodoapp.ui.screens.splash.SplashScreen
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants.SPLASH_SCREEN

fun NavGraphBuilder.splashComposable(
    navigateToTaskScreen: () -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = SPLASH_SCREEN
    ) {
        SplashScreen(navigateToListScreen = navigateToTaskScreen)
    }
}