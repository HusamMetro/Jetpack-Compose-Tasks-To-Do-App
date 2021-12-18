package com.tuwaiq.husam.taskstodoapp.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.tuwaiq.husam.taskstodoapp.ui.screens.list.ListScreen
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Action
import com.tuwaiq.husam.taskstodoapp.util.Constants.LIST_ARGUMENT_KEY
import com.tuwaiq.husam.taskstodoapp.util.Constants.LIST_SCREEN
import com.tuwaiq.husam.taskstodoapp.util.toAction

@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
    navController: NavHostController
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

        var myAction by rememberSaveable { mutableStateOf(Action.NO_ACTION) }

        LaunchedEffect(key1 = myAction) {
            if (action != myAction) {
                myAction = action
                sharedViewModel.action.value = action
            }
        }

        val dataBaseAction by sharedViewModel.action

        ListScreen(
            action = dataBaseAction,
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel,
            navController = navController
        )
    }
}