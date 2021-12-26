package com.tuwaiq.husam.taskstodoapp.navigation.destinations

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.tuwaiq.husam.taskstodoapp.ui.screens.list.ListScreen
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Action
import com.tuwaiq.husam.taskstodoapp.util.Constants.LIST_ARGUMENT_KEY
import com.tuwaiq.husam.taskstodoapp.util.Constants.LIST_SCREEN
import com.tuwaiq.husam.taskstodoapp.util.Constants.TASK_SCREEN
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
        }),
        /*exitTransition = { _,_ ->
            fadeOut(animationSpec = tween(durationMillis = 300))},
        enterTransition = { _,_ ->
            fadeIn(animationSpec = tween(durationMillis = 300))
        },*/
        /*exitTransition = { initial, target ->
            if (target.destination.navigatorName == TASK_SCREEN ) {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                ) + fadeOut(animationSpec = tween(durationMillis = 300))
            }else{
                fadeOut(animationSpec = tween(durationMillis = 300))
            }
        },
        popEnterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
            )*//*+fadeIn(animationSpec = tween(durationMillis = 300))*//*
        }*/
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