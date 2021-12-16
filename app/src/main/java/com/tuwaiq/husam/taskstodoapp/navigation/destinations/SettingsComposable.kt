package com.tuwaiq.husam.taskstodoapp.navigation.destinations

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tuwaiq.husam.taskstodoapp.ui.screens.settings.SettingsScreen
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants.SETTINGS_SCREEN

fun NavGraphBuilder.settingsComposable(
    sharedViewModel: SharedViewModel
) {
    composable(
        route = SETTINGS_SCREEN
    ){
//        SettingsScreen(sharedViewModel = sharedViewModel)
    }
}