package com.tuwaiq.husam.taskstodoapp.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tuwaiq.husam.taskstodoapp.ui.screens.suggested.SuggestedScreen
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants.SUGGESTED_SCREEN

fun NavGraphBuilder.suggestedComposable(
    sharedViewModel: SharedViewModel
) {
    composable(
        route = SUGGESTED_SCREEN
    ) {
//        SuggestedScreen(sharedViewModel = sharedViewModel)
    }
}