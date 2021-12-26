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
import kotlin.random.Random

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
    navigateToTaskScreens: (taskId: Int) -> Unit
) {
//    Log.e("listPage","${FirebaseAuth.getInstance().currentUser?.uid}")
    if (sortState is RequestState.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = searchedTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreens = navigateToTaskScreens
                    )
                }
            }
            sortState.data == Priority.NONE -> {
                if (allTasks is RequestState.Success)
                    HandleListContent(
                        tasks = allTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreens = navigateToTaskScreens
                    )
            }
            sortState.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreens = navigateToTaskScreens
                )
            }
            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    tasks = highPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreens = navigateToTaskScreens
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
    navigateToTaskScreens: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTask(
            tasks = tasks,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreens = navigateToTaskScreens
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
    navigateToTaskScreens: (taskId: Int) -> Unit
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
            // move it to Above the LazyColumn to fix bug animation
//            var itemAppeared by remember { mutableStateOf(false) }
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
                            navigateToTaskScreens = navigateToTaskScreens
                        )
                    }
                )
            }
            /*
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = { FractionalThreshold(fraction = 0.2f) },
                background = { RedBackground(degrees = degrees) },
                dismissContent = {
                    TaskItem(
                        toDoTask = task,
                        navigateToTaskScreens = navigateToTaskScreens
                    )
                }
            )*/
            /*TaskItem(
                toDoTask = task,
                navigateToTaskScreens = navigateToTaskScreens
            )*/
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
    navigateToTaskScreens: (taskId: Int) -> Unit
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
        val rotationState by animateFloatAsState(targetValue = if (expandState) 180f else 0f)
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
//                .clip(shape = RoundedCornerShape(10.dp))
                .background(
                    MaterialTheme.colors.cardColor
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
                    CardExpanded(toDoTask = toDoTask)
                } else {
                    Row(Modifier.padding(bottom = SMALL_PADDING)) {
                        Row(
                            Modifier.weight(19f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CustomComponent(
                                indicatorValue = Random.nextInt(6),
                                canvasSize = 32.dp,
                                backgroundIndicatorStrokeWidth = 10f,
                                foregroundIndicatorStrokeWidth = 10f,
                                foregroundIndicatorColor = MaterialTheme.colors.foregroundIndicatorColor,
                                backgroundIndicatorColor = MaterialTheme.colors.backgroundIndicatorColor,
                                bigTextFontSize = 10.sp,
                                smallTextFontSize = 0.sp,
                                maxIndicatorValue = 5,
                                bigTextSuffix = "",
                                smallText = "",
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

                    /*
                    if (expandState) {
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
                                horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
                            ) {
                                Icon(imageVector = Icons.Filled.CalendarToday, contentDescription = "")
                                Text(
                                    text = "Start Date :",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colors.taskItemTextColor
                                )
                                Text(
                                    text = "8/10/2021",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colors.taskItemTextColor
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
                            ) {
                                Icon(imageVector = Icons.Filled.CalendarToday, contentDescription = "")
                                Text(
                                    text = "End Date :",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colors.taskItemTextColor
                                )
                                Text(
                                    text = "20/12/2021",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colors.taskItemTextColor
                                )
                            }
    
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
                            ) {
                                Icon(imageVector = Icons.Filled.Person, contentDescription = "n")
                                Text(
                                    text = "Anything ... just to fill the column",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colors.taskItemTextColor
                                )
                            }
                        }
                    }
                    */

                    /*Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(modifier = Modifier
                            .offset(15.dp)
    //                        .alpha(ContentAlpha.medium)
                            .rotate(rotationState),
                            onClick = {
                                expandState = !expandState
                            }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Drop-Down Arrow",
                                tint = MaterialTheme.colors.taskItemTextColor
                            )
                        }
                    }*/
                }
            }
        }
    }
}

@Composable
fun CardExpanded(toDoTask: ToDoTask) {

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
    Text(
        text = toDoTask.description,
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemTextColor,
        style = MaterialTheme.typography.subtitle1,
//        maxLines = 2,
//        overflow = TextOverflow.Ellipsis
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MEDIUM_PADDING),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ) {
        Divider(
            Modifier
                .fillMaxWidth()
                .padding(vertical = LARGE_PADDING, horizontal = SMALL_PADDING)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarToday,
                    contentDescription = ""
                )
                /*Text(
                    text = "Start Date :",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.taskItemTextColor
                )*/
                Text(
                    text = "8/10/2021",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.taskItemTextColor
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "",
                )
                /*Text(
                    text = "End Date :",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.taskItemTextColor
                )*/
                Text(
                    text = "20/12/2021",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.taskItemTextColor
                )
            }

        }
        Divider(Modifier.padding(vertical = LARGE_PADDING , horizontal = SMALL_PADDING ))
        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(top = MEDIUM_PADDING)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = Modifier.padding(horizontal = SMALL_PADDING),
                onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Remove, contentDescription = "")
            }
            CustomComponent(
                indicatorValue = Random.nextInt(6),
                canvasSize = 48.dp,
                backgroundIndicatorStrokeWidth = 12f,
                foregroundIndicatorStrokeWidth = 12f,
                foregroundIndicatorColor = MaterialTheme.colors.foregroundIndicatorColor,
                backgroundIndicatorColor = MaterialTheme.colors.backgroundIndicatorColor,
                bigTextFontSize = 15.sp,
                smallTextFontSize = 0.sp,
                maxIndicatorValue = 5,
                bigTextSuffix = "",
                smallText = "",
            )
            IconButton(
                modifier = Modifier.padding(horizontal = SMALL_PADDING),
                onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        }
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
                Priority.HIGH
            )
        ) {}
    }
}
/*

@Composable
@Preview
private fun RedBackgroundPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        RedBackground(0f)
    }
}*/
