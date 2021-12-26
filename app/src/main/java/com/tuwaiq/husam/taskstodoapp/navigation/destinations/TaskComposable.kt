package com.tuwaiq.husam.taskstodoapp.navigation.destinations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.tuwaiq.husam.taskstodoapp.ui.screens.task.TaskScreen
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Action
import com.tuwaiq.husam.taskstodoapp.util.Constants.TASK_ARGUMENT_KEY
import com.tuwaiq.husam.taskstodoapp.util.Constants.TASK_SCREEN

@ExperimentalAnimationApi
fun NavGraphBuilder.taskComposable(
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        }),
        enterTransition = { _, _ ->
            slideInHorizontally(
                animationSpec = tween(durationMillis = 300),
                initialOffsetX = { fullWidth -> -fullWidth }
            )+  fadeIn(animationSpec = tween(durationMillis = 300))
        },
        exitTransition = {_,_ ->
            slideOutHorizontally(
                animationSpec = tween(durationMillis = 500),
                targetOffsetX = { fullWidth -> -fullWidth }
            )+  fadeOut(animationSpec = tween(durationMillis = 500))
        }
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        LaunchedEffect(key1 = taskId) {
            sharedViewModel.getSelectedTask(taskId = taskId)
        }
        val selectedTask by sharedViewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = selectedTask) {
            if (selectedTask != null || taskId == -1) {
                sharedViewModel.updateTaskFields(selectedTask = selectedTask)
            }
        }
        TaskScreen(
            selectedTask = selectedTask,
            sharedViewModel = sharedViewModel,
            navigateToListScreen = navigateToListScreen
        )
    }
}