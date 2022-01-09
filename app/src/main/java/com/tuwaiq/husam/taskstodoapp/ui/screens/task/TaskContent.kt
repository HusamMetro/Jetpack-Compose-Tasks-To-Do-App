package com.tuwaiq.husam.taskstodoapp.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.CustomComponent
import com.tuwaiq.husam.taskstodoapp.components.OutlineTextFieldWithErrorView
import com.tuwaiq.husam.taskstodoapp.components.PriorityDropDown
import com.tuwaiq.husam.taskstodoapp.data.models.Priority
import com.tuwaiq.husam.taskstodoapp.ui.theme.*

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    titleIsError: Boolean = false,
    titleErrorMsg: String = stringResource(R.string.title_invalid),
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
    onTaskCounterChanged: (String) -> Unit,
    showStartDatePicker: () -> Unit,
    showEndDatePicker: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState())
            .padding(LARGE_PADDING)

    ) {
        OutlineTextFieldWithErrorView(
            modifier = Modifier
                .fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(R.string.title)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            isError = titleIsError,
            errorMsg = titleErrorMsg
        )
        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )
        Column(modifier = Modifier.padding(bottom = ERROR_MESSAGE_PADDING)) {
            PriorityDropDown(
                priority = priority,
                onPrioritySelected = onPrioritySelected,
                modifier = Modifier
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(DESCRIPTION_HEIGHT),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = stringResource(R.string.description)) },
            textStyle = MaterialTheme.typography.body1,
            maxLines = 4,
            colors = MaterialTheme.colors.customOutlinedTextFieldColor
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = SMALL_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LARGE_PADDING)
        ) {

            OutlinedTextField(
                modifier = Modifier
//                    .fillMaxWidth(),
                    .weight(1f)
                    .clickable {
                        showStartDatePicker()
                    },
                value = startDate,
                onValueChange = { onStartDateChanged(it) },
                label = { Text(text = stringResource(R.string.start_date)) },
                textStyle = MaterialTheme.typography.body1,
                singleLine = true,
                readOnly = true,
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.CalendarToday,
                        contentDescription = ""
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                    disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    disabledTrailingIconColor = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity)
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        showEndDatePicker()
                    },
                value = endDate,
                onValueChange = { onEndDateChanged(it) },
                label = { Text(text = stringResource(R.string.end_date)) },
                textStyle = MaterialTheme.typography.body1,
                singleLine = true,
                readOnly = true,
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.CalendarToday,
                        contentDescription = ""
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                    disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    disabledTrailingIconColor = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity)
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = SMALL_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LARGE_PADDING)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = taskCounter,
                onValueChange = { onTaskCounterChanged(it) },
                label = { Text(text = stringResource(R.string.task_counter)) },
                textStyle = MaterialTheme.typography.body1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                colors = MaterialTheme.colors.customOutlinedTextFieldColor
            )
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = maxTask,
                onValueChange = { onMaxTaskChanged(it) },
                label = { Text(text = stringResource(R.string.max_task)) },
                textStyle = MaterialTheme.typography.body1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                colors = MaterialTheme.colors.customOutlinedTextFieldColor
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val taskCounterInt = try {
                taskCounter.toInt()
            } catch (e: Exception) {
                0
            }
            val maxInt = try {
                maxTask.toInt()
            } catch (e: Exception) {
                1
            }
            CustomComponent(
                indicatorValue = taskCounterInt,
                canvasSize = 100.dp,
                backgroundIndicatorStrokeWidth = 20f,
                foregroundIndicatorStrokeWidth = 20f,
                foregroundIndicatorColor = MaterialTheme.colors.foregroundIndicatorColor,
                backgroundIndicatorColor = MaterialTheme.colors.backgroundIndicatorColor2,
                bigTextFontSize = 20.sp,
                smallTextFontSize = 15.sp,
                maxIndicatorValue = maxInt,
                bigTextSuffix = "",
                smallText = "$maxInt",
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
        startDate = "",
        onStartDateChanged = {

        },
        endDate = "",
        onEndDateChanged = {

        },
        maxTask = "",
        onMaxTaskChanged = {

        },
        taskCounter = "",
        onTaskCounterChanged = {

        },
        showStartDatePicker = {

        },
        showEndDatePicker = {

        }
    )
}