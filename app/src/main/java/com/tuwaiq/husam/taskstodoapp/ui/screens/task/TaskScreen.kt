package com.tuwaiq.husam.taskstodoapp.ui.screens.task

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.dateFormatter
import com.tuwaiq.husam.taskstodoapp.components.getDisplayName
import com.tuwaiq.husam.taskstodoapp.components.showDatePicker
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
    val title: String by sharedViewModel.title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority
    val titleIsError: Boolean by sharedViewModel.titleIsError
    val startDate: String by sharedViewModel.startDate
    val endDate: String by sharedViewModel.endDate
    val maxTask: String by sharedViewModel.maxTask
    val taskCounter: String by sharedViewModel.taskCounter
    val context = LocalContext.current
    val activity = context as AppCompatActivity

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
                            sharedViewModel.titleIsError.value = true
                            displayToast(context = context)
                        }
                    }
                },
                onShareClicked = {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            getSharedTaskText(
                                title = title,
                                description = description,
                                priority = priority,
                                startDate = startDate,
                                endDate = endDate,
                                maxTask = maxTask,
                                taskCounter = taskCounter,
                                context = context
                            )
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
                    sharedViewModel.titleIsError.value = it.isEmpty()
                    sharedViewModel.updateTitle(it)
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
                    sharedViewModel.startDate.value = it
                },
                endDate = endDate,
                onEndDateChanged = {
                    sharedViewModel.endDate.value = it
                },
                maxTask = maxTask,
                onMaxTaskChanged = {
                    sharedViewModel.maxTask.value = removeSpecial(it)
                },
                taskCounter = taskCounter,
                onTaskCounterChanged = {
                    sharedViewModel.taskCounter.value = removeSpecial(it)
                },
                showStartDatePicker = {
                    showDatePicker(activity) { date: Long? ->
                        sharedViewModel.startDate.value = (dateFormatter(date)!!)
                    }
                },
                showEndDatePicker = {
                    showDatePicker(activity) { date: Long? ->
                        sharedViewModel.endDate.value = (dateFormatter(date)!!)
                    }
                }
            )
        }
    )
}

fun getSharedTaskText(
    title: String,
    description: String,
    priority: Priority,
    startDate: String,
    endDate: String,
    maxTask: String,
    taskCounter: String,
    context: AppCompatActivity
): String {
    var text = "${context.getString(R.string.title)} : $title"
    if (description.isNotEmpty()) {
        text += "\n${context.getString(R.string.description)} : $description"
    }
    text += "\n${context.getString(R.string.priority)} : ${
        getDisplayName(
            priority,
            context = context
        )
    }"
    if (startDate.isNotEmpty()) {
        text += "\n${context.getString(R.string.start_date)} : $startDate"
    }
    if (endDate.isNotEmpty()) {
        text += "\n${context.getString(R.string.end_date)} : $endDate"
    }
    text += "\n${context.getString(R.string.max_task)} : $maxTask"
    text += "\n${context.getString(R.string.task_counter)} : $taskCounter"
    return text
}

fun removeSpecial(input: String): String {
    Regex("[^A-Za-z0-9]+$").also {
        return it.replace(input, "")
    }
}

fun displayToast(context: Context) {
    Toast.makeText(
        context,
        context.getString(R.string.fields_empty),
        Toast.LENGTH_SHORT
    ).show()
}