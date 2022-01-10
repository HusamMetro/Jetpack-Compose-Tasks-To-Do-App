package com.tuwaiq.husam.taskstodoapp.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.tuwaiq.husam.taskstodoapp.ui.screens.splash.SplashScreen
import com.tuwaiq.husam.taskstodoapp.util.Constants.SPLASH_SCREEN

@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    navigateToTaskScreen: () -> Unit
) {
    composable(
        route = SPLASH_SCREEN,
        exitTransition = { _, _ ->
            slideOutVertically(
                animationSpec = tween(durationMillis = 300),
                targetOffsetY = { fullHeight -> -fullHeight }
            )
        }
    ) {
        SplashScreen(navigateToListScreen = navigateToTaskScreen)
    }
}