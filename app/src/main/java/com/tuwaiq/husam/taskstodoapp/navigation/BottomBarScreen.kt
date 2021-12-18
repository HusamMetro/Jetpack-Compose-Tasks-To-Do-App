package com.tuwaiq.husam.taskstodoapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.tuwaiq.husam.taskstodoapp.util.Constants.LIST_SCREEN
import com.tuwaiq.husam.taskstodoapp.util.Constants.SETTINGS_SCREEN
import com.tuwaiq.husam.taskstodoapp.util.Constants.CHALLENGES_SCREEN

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        LIST_SCREEN,
        "LIST",
        icon = Icons.Filled.Home
    )
    object Challenges : BottomBarScreen(
        CHALLENGES_SCREEN,
        "CHALLENGES",
        icon = Icons.Filled.Star
    )
    object Settings : BottomBarScreen(
        SETTINGS_SCREEN,
        "SETTINGS",
        icon = Icons.Filled.Settings
    )
}
