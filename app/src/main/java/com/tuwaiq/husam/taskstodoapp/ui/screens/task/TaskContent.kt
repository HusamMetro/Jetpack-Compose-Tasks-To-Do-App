package com.tuwaiq.husam.taskstodoapp.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nature
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.CustomComponent
import com.tuwaiq.husam.taskstodoapp.components.PriorityDropDown
import com.tuwaiq.husam.taskstodoapp.data.models.Priority
import com.tuwaiq.husam.taskstodoapp.ui.theme.*
import kotlin.random.Random

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
    startDate: String,
    onStartDateChanged: (String) -> Unit,
    endDate: String,
    onEndDateChanged: (String) -> Unit,
    maxTask: String,
    onMaxTaskChanged: (String) -> Unit,
    taskCounter: String,
    onTaskCounterChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(LARGE_PADDING)

    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(R.string.title)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true
        )
        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPrioritySelected,
            modifier = Modifier
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                /*.fillMaxHeight(0.3f)*/,
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = stringResource(R.string.description)) },
            textStyle = MaterialTheme.typography.body1,
            maxLines = 3,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = startDate,
            onValueChange = { onStartDateChanged(it) },
            label = { Text(text = stringResource(R.string.start_date)) },
            textStyle = MaterialTheme.typography.body1,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = endDate,
            onValueChange = { onEndDateChanged(it) },
            label = { Text(text = stringResource(R.string.end_date)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LARGE_PADDING)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = taskCounter,
                onValueChange = { onTaskCounterChanged(it) },
                label = { Text(text = stringResource(R.string.max_task)) },
                textStyle = MaterialTheme.typography.body1,
                singleLine = true,
            )
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = maxTask,
                onValueChange = { onMaxTaskChanged(it) },
                label = { Text(text = stringResource(R.string.max_task)) },
                textStyle = MaterialTheme.typography.body1,
                singleLine = true
            )
        }
        Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.onBackground.copy(0.2f))
            .padding(15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        ) {
            CustomComponent(
                indicatorValue = taskCounter.toInt(),
                canvasSize = 120.dp,
                backgroundIndicatorStrokeWidth = 30f,
                foregroundIndicatorStrokeWidth = 30f,
                foregroundIndicatorColor = MaterialTheme.colors.foregroundIndicatorColor,
                backgroundIndicatorColor = MaterialTheme.colors.backgroundIndicatorColor,
                bigTextFontSize = 20.sp,
                smallTextFontSize = 0.sp,
                maxIndicatorValue = maxTask.toInt(),
                bigTextSuffix = "",
                smallText = "",
            )
        }

    }
}

@Composable
@Preview
fun TaskContentPreview() {
    TaskContent(
        title = "",
        onTitleChange = {},
        description = "",
        onDescriptionChange = {},
        priority = Priority.LOW,
        onPrioritySelected = {},
        startDate = "00/00/00",
        onStartDateChanged = {

        },
        endDate = "00/00/00",
        onEndDateChanged = {

        },
        maxTask = "10",
        onMaxTaskChanged = {

        },
        taskCounter = "2",
        onTaskCounterChanged = {

        }
    )
}