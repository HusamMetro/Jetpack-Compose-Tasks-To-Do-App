package com.tuwaiq.husam.taskstodoapp.ui.screens.list

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.BottomBar
import com.tuwaiq.husam.taskstodoapp.ui.theme.fabBackgroundColor
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Action
import com.tuwaiq.husam.taskstodoapp.util.SearchAppBarState
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListScreen(
    action: Action,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {

    LaunchedEffect(key1 = action) {
        sharedViewModel.handleDatabaseAction(action = action)
    }


    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()

    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
//    sharedViewModel.handleDatabaseAction(action = action)
    DisplaySnackBar(
        scaffoldState = scaffoldState,
        onComplete = { sharedViewModel.action.value = it },
        onUndoClicked = {
            sharedViewModel.action.value = it
        },
        taskTitle = sharedViewModel.title.value,
        action = action,
        context = context
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
            ListContent(
                allTasks = allTasks,
                lowPriorityTasks = lowPriorityTasks,
                highPriorityTasks = highPriorityTasks,
                sortState = sortState,
                searchedTasks = searchedTasks,
                searchAppBarState = searchAppBarState,
                onSwipeToDelete = { action, task ->
                    sharedViewModel.action.value = action
                    sharedViewModel.updateTaskFields(task)
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
                navigateToTaskScreens = navigateToTaskScreen,
                onUpdate = { task, operation ->
                    sharedViewModel.updateTaskFields(task)
                    sharedViewModel.taskCounter.value =
                        getTaskCounterInt2(sharedViewModel.taskCounter.value,sharedViewModel.maxTask.value, operation).toString()
                    sharedViewModel.handleDatabaseAction(Action.UPDATE)
                }
            )
        }, bottomBar = {
            BottomBar(navController = navController)
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}
fun getTaskCounterInt2(taskCounter: String, maxTask: String , operation: Int) : Int{
    return try {
        var result = taskCounter.toInt()
        when(operation) {
            1 -> {
                if (result < maxTask.toInt() )
                    result++
            }
            -1 -> {
                if (result > 0)
                    result--
            }
        }
        result
    } catch (e: Exception) {
        0
    }
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_button),
            tint = Color.White
        )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    onComplete: (Action) -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action,
    context: Context
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action, taskTitle, context),
                    actionLabel = setActionLabel(action, context)
                )
                undoDeletedTask(
                    action = action,
                    snackbarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
            onComplete(Action.NO_ACTION)
        }
    }
}

private fun setActionLabel(action: Action, context: Context): String {
    return if (action.name == "DELETE") {
        context.getString(R.string.undo)
    } else {
        context.getString(R.string.ok)
    }
}

private fun setMessage(action: Action, taskTitle: String, context: Context): String {
    return when (action) {
        Action.DELETE_ALL -> context.getString(R.string.all_tasks_deleted)
        else -> "${action.name}: $taskTitle"
    }
}

private fun undoDeletedTask(
    action: Action,
    snackbarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackbarResult == SnackbarResult.ActionPerformed
        && action == Action.DELETE
    ) {
        onUndoClicked(Action.UNDO)
    }
}

/*
@Preview
@Composable
fun ListScreenPreview() {
    ListScreen(navigateToTaskScreen = {})
}*/
