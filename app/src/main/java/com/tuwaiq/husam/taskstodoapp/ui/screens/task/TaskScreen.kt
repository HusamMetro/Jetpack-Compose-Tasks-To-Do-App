package com.tuwaiq.husam.taskstodoapp.ui.screens.task

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.data.models.Priority
import com.tuwaiq.husam.taskstodoapp.data.models.ToDoTask
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Action

@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    val title: String by sharedViewModel. title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority
    var titleIsError: Boolean by remember { mutableStateOf(false) }
    var startDate: String by sharedViewModel.startDate
    var endDate: String by sharedViewModel.endDate
    var maxTask: String by sharedViewModel.maxTask
    var taskCounter: String by sharedViewModel.taskCounter
    val context = LocalContext.current
//    BackHandler(onBackPressed = { navigateToListScreen(Action.NO_ACTION) })
    BackHandler {
        navigateToListScreen(Action.NO_ACTION)
    }

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreen(action)
                    } else {
                        if (sharedViewModel.validateFields()) {
                            navigateToListScreen(action)
                        } else {
                            titleIsError = true
                            displayToast(context = context)
                        }
                    }
                },
                onShareClicked = {
                    Log.e("Shared", "Shared Clicked")
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Title : ${title}\nDescription: $description"
                        )
                        type = "text/plain"
                    }
                    context.startActivity(sendIntent)
                }
            )
        },
        content = {
            TaskContent(
                title = title,
                onTitleChange = {
                    titleIsError = it.isEmpty()
                    sharedViewModel.updateTitle(it)
//                    sharedViewModel.title.value = it
                },
                titleIsError = titleIsError,
                description = description,
                onDescriptionChange = {
                    sharedViewModel.description.value = it
                },
                priority = priority,
                onPrioritySelected = {
                    sharedViewModel.priority.value = it
                },
                startDate = startDate,
                onStartDateChanged = {
                    startDate = it
                },
                endDate = endDate,
                onEndDateChanged = {
                    endDate = it
                },
                maxTask = maxTask,
                onMaxTaskChanged = {
                    maxTask = it
                },
                taskCounter = taskCounter,
                onTaskCounterChanged = {
                    taskCounter = it
                }
            )
        }
    )
}

fun displayToast(context: Context) {
    Toast.makeText(
        context,
        context.getString(R.string.fields_empty),
        Toast.LENGTH_SHORT
    ).show()
}
/*
@Composable
fun BackHandler(
    backDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)
    val backCallBack = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }
    DisposableEffect(key1 = backDispatcher) {
        backDispatcher?.addCallback(backCallBack)
        onDispose {
            backCallBack.remove()
        }
    }
}*/
