package com.tuwaiq.husam.taskstodoapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.ToDoApplication
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
        "ToDoApplication.getToDoContext().getString(R.string.list_bottom_bar)",
        icon = Icons.Filled.Home
    )
    object Challenges : BottomBarScreen(
        CHALLENGES_SCREEN,
       " ToDoApplication.getToDoContext().getString(R.string.challenges_bottom_bar)",
        icon = Icons.Filled.Star
    )
    object Settings : BottomBarScreen(
        SETTINGS_SCREEN,
       " ToDoApplication.getToDoContext().getString(R.string.settings_bottom_bar)",
        icon = Icons.Filled.Settings
    )
}
