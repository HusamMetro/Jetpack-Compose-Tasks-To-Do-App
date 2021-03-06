package com.tuwaiq.husam.taskstodoapp.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.CustomComponent
import com.tuwaiq.husam.taskstodoapp.data.models.Priority
import com.tuwaiq.husam.taskstodoapp.data.models.ToDoTask
import com.tuwaiq.husam.taskstodoapp.ui.theme.*
import com.tuwaiq.husam.taskstodoapp.util.Action
import com.tuwaiq.husam.taskstodoapp.util.RequestState
import com.tuwaiq.husam.taskstodoapp.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListContent(
    allTasks: RequestState<List<ToDoTask>>,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    sortState: RequestState<Priority>,
    searchedTasks: RequestState<List<ToDoTask>>,
    searchAppBarState: SearchAppBarState,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreens: (taskId: Int) -> Unit,
    onUpdate: (ToDoTask, Int) -> Unit,
    cardBackgroundColor: @Composable (Boolean) -> Brush
) {
    if (sortState is RequestState.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = searchedTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreens = navigateToTaskScreens,
                        onUpdate,
                        cardBackgroundColor = cardBackgroundColor
                    )
                }
            }
            sortState.data == Priority.NONE -> {
                if (allTasks is RequestState.Success)
                    HandleListContent(
                        tasks = allTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreens = navigateToTaskScreens,
                        onUpdate,
                        cardBackgroundColor = cardBackgroundColor
                    )
            }
            sortState.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreens = navigateToTaskScreens,
                    onUpdate,
                    cardBackgroundColor = cardBackgroundColor
                )
            }
            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    tasks = highPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreens = navigateToTaskScreens,
                    onUpdate,
                    cardBackgroundColor = cardBackgroundColor
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreens: (taskId: Int) -> Unit,
    onUpdate: (ToDoTask, Int) -> Unit,
    cardBackgroundColor: @Composable (Boolean) -> Brush
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTask(
            tasks = tasks,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreens = navigateToTaskScreens,
            onUpdate,
            cardBackgroundColor = cardBackgroundColor
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun DisplayTask(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreens: (taskId: Int) -> Unit,
    onUpdate: (ToDoTask, Int) -> Unit,
    cardBackgroundColor: @Composable (Boolean) -> Brush
) {
    var itemAppeared by remember { mutableStateOf(false) }
    LazyColumn(
        Modifier.padding(bottom = TOP_APP_BAR_HEIGHT),
        contentPadding = PaddingValues(MEDIUM_PADDING),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
    ) {
        items(
            items = tasks,
            key = { task ->
                task.id
            }
        ) { task ->
            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissedEndToStart = dismissState.isDismissed(DismissDirection.EndToStart)
            val isDismissedStartToEnd = dismissState.isDismissed(DismissDirection.StartToEnd)

            if (isDismissedEndToStart && dismissDirection == DismissDirection.EndToStart) {
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(300)
                    onSwipeToDelete(Action.DELETE, task)
                }
            }

            if (isDismissedStartToEnd && dismissDirection == DismissDirection.StartToEnd) {
                LaunchedEffect(key1 = false) {
                    navigateToTaskScreens(task.id)
                }
            }

            val degrees by animateFloatAsState(
                targetValue = if (dismissState.targetValue == DismissValue.Default)
                    0f
                else
                    -45f
            )
            val degreesEdit by animateFloatAsState(
                targetValue = if (dismissState.targetValue == DismissValue.Default)
                    -45f
                else
                    0f
            )
            LaunchedEffect(key1 = true) {
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared && !isDismissedEndToStart,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 200
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
                    dismissThresholds = { FractionalThreshold(fraction = 0.2f) },
                    background = {
                        if (dismissDirection == DismissDirection.EndToStart) RedBackground(degrees = degrees) else GreenBackground(
                            degrees = degreesEdit
                        )
                    },
                    dismissContent = {
                        TaskItem(
                            toDoTask = task,
                            onUpdate = onUpdate,
                            cardBackgroundColor = cardBackgroundColor
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(HighPriorityColor)
            .padding(LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_icon),
            tint = Color.White
        )
    }
}

@Composable
fun GreenBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(LowPriorityColor)
            .padding(LARGEST_PADDING),
        contentAlignment = Alignment.CenterStart
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Edit,
            contentDescription = stringResource(R.string.edit_icon),
            tint = Color.White
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    onUpdate: (ToDoTask, Int) -> Unit,
    cardBackgroundColor: @Composable (Boolean) -> Brush
) {
    var expandState by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        onClick = {
            expandState = !expandState
        }
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .background(
                    cardBackgroundColor(toDoTask.gold)
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing
                    )
                ),
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            border = BorderStroke(2.dp, MaterialTheme.colors.cardBorderColor)
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = LARGE_PADDING,
                        top = LARGE_PADDING,
                        end = LARGE_PADDING,
                        bottom = LARGE_PADDING
                    )
                    .fillMaxWidth()
            ) {
                if (expandState) {
                    CardExpanded(toDoTask = toDoTask, onUpdate)
                } else {
                    Row(Modifier.padding(bottom = SMALL_PADDING)) {
                        Row(
                            Modifier.weight(19f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CustomComponent(
                                canvasSize = 32.dp,
                                indicatorValue = getTaskCounterInt(toDoTask.taskCounter),
                                maxIndicatorValue = getMaxTaskInt(toDoTask.maxTask),
                                backgroundIndicatorColor = MaterialTheme.colors.backgroundIndicatorColor,
                                backgroundIndicatorStrokeWidth = 10f,
                                foregroundIndicatorColor = MaterialTheme.colors.foregroundIndicatorColor,
                                foregroundIndicatorStrokeWidth = 10f,
                                bigTextFontSize = 10.sp,
                                smallText = "",
                                smallTextFontSize = 0.sp,
                            )
                            Text(
                                modifier = Modifier
                                    .weight(8f)
                                    .padding(start = 10.dp),
                                text = toDoTask.title,
                                color = MaterialTheme.colors.taskItemTextColor,
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            Canvas(
                                modifier = Modifier.size(PRIORITY_INDICATOR_SIZE),
                            ) {
                                drawCircle(
                                    toDoTask.priority.color
                                )
                            }
                        }
                    }
                    Text(
                        text = toDoTask.description,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.taskItemTextColor,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }
        }
    }
}

@Composable
fun CardExpanded(
    toDoTask: ToDoTask,
    onUpdate: (ToDoTask, Int) -> Unit
) {

    Row(Modifier.padding(bottom = SMALL_PADDING)) {
        Text(
            modifier = Modifier
                .weight(8f),
            text = toDoTask.title,
            color = MaterialTheme.colors.taskItemTextColor,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.TopEnd
        ) {
            Canvas(
                modifier = Modifier.size(PRIORITY_INDICATOR_SIZE),
            ) {
                drawCircle(
                    toDoTask.priority.color
                )
            }
        }
    }
    if (toDoTask.description.isNotEmpty()) {
        Text(
            text = toDoTask.description,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.taskItemTextColor,
            style = MaterialTheme.typography.subtitle1,
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MEDIUM_PADDING),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (toDoTask.startDate.isNotEmpty()) {
                Row(
                    Modifier.padding(vertical = LARGE_PADDING),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarToday,
                        contentDescription = ""
                    )
                    Text(
                        text = toDoTask.startDate,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.taskItemTextColor
                    )
                }
            }
            if (toDoTask.endDate.isNotEmpty()) {
                Row(
                    Modifier.padding(vertical = LARGE_PADDING),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CalendarToday,
                        contentDescription = "",
                    )
                    Text(
                        text = toDoTask.endDate,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.taskItemTextColor
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            IconButton(
                modifier = Modifier.padding(horizontal = SMALL_PADDING),
                onClick = { onUpdate(toDoTask, -1) }
            ) {
                Icon(imageVector = Icons.Filled.Remove, contentDescription = "")
            }
            CustomComponent(
                canvasSize = 48.dp,
                indicatorValue = getTaskCounterInt(toDoTask.taskCounter),
                maxIndicatorValue = getMaxTaskInt(toDoTask.maxTask),
                backgroundIndicatorColor = MaterialTheme.colors.backgroundIndicatorColor,
                backgroundIndicatorStrokeWidth = 12f,
                foregroundIndicatorColor = MaterialTheme.colors.foregroundIndicatorColor,
                foregroundIndicatorStrokeWidth = 12f,
                bigTextFontSize = 15.sp,
                smallText = "",
                smallTextFontSize = 0.sp,
            )
            IconButton(
                modifier = Modifier.padding(horizontal = SMALL_PADDING),
                onClick = { onUpdate(toDoTask, +1) }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        }
    }
}

fun getTaskCounterInt(taskCounter: String): Int {
    return try {
        taskCounter.toInt()
    } catch (e: Exception) {
        0
    }
}

fun getMaxTaskInt(maxTask: String): Int {
    return try {
        maxTask.toInt()
    } catch (e: Exception) {
        1
    }
}

@ExperimentalMaterialApi
@Composable
@Preview(showBackground = true)
private fun TaskItemPreview() {
    Box(
        Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        TaskItem(
            toDoTask = ToDoTask(
                1,
                "Finish Project",
                "Before the End of 30 December",
                Priority.HIGH,
                "20/12/21",
                "30/12/21",
                "20",
                "10"
            ),
            onUpdate = { _, _ -> }
        ) {
            MaterialTheme.colors.cardColor
        }
    }
}
