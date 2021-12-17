package com.tuwaiq.husam.taskstodoapp.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants.CHALLENGES_SCREEN

fun NavGraphBuilder.challengesComposable(
    sharedViewModel: SharedViewModel
) {
    composable(
        route = CHALLENGES_SCREEN
    ) {
//        SuggestedScreen(sharedViewModel = sharedViewModel)
    }
}