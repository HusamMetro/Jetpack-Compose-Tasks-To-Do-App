package com.tuwaiq.husam.taskstodoapp.ui.screens.challenges

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuwaiq.husam.taskstodoapp.components.CustomComponent
import com.tuwaiq.husam.taskstodoapp.data.models.Languages
import com.tuwaiq.husam.taskstodoapp.data.models.MockToDoTask
import com.tuwaiq.husam.taskstodoapp.ui.screens.list.getMaxTaskInt
import com.tuwaiq.husam.taskstodoapp.ui.screens.list.getTaskCounterInt
import com.tuwaiq.husam.taskstodoapp.ui.theme.*

@ExperimentalMaterialApi
@Composable
fun ChallengesContent(
    mockTasks: List<MockToDoTask>,
    lang: String,
    onAdd: (MockToDoTask) -> Unit
) {
    LazyColumn(
        Modifier.padding(bottom = TOP_APP_BAR_HEIGHT),
        contentPadding = PaddingValues(MEDIUM_PADDING),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
    ) {
        try {
            items(
                items = mockTasks,
                // There is a bug here if clicked fast the Key will repeat ... Fix Later
                key = { task ->
                    task.id
                }
            ) { task ->
                MockTaskItem(
                    mockToDoTask = task,
                    lang = lang,
                    onAdd = onAdd
                )
            }
        } catch (e: Throwable) {
            Log.e("lazyColumn", e.message.toString())
        }

    }


}

@ExperimentalMaterialApi
@Composable
fun MockTaskItem(
    mockToDoTask: MockToDoTask,
    lang: String,
    onAdd: (MockToDoTask) -> Unit
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
                if (!expandState) {
                    MockCardExpanded(
                        mockToDoTask = mockToDoTask,
                        onAdd = onAdd,
                        lang = lang
                    )
                } /*
                else {
                    Row(Modifier.padding(bottom = SMALL_PADDING)) {
                        Row(
                            Modifier.weight(19f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CustomComponent(
                                indicatorValue = getTaskCounterInt(mockToDoTask.taskCounter),
                                maxIndicatorValue = getMaxTaskInt(mockToDoTask.maxTask),
                                canvasSize = 32.dp,
                                backgroundIndicatorStrokeWidth = 10f,
                                foregroundIndicatorStrokeWidth = 10f,
                                foregroundIndicatorColor = MaterialTheme.colors.foregroundIndicatorColor,
                                backgroundIndicatorColor = MaterialTheme.colors.backgroundIndicatorColor,
                                bigTextFontSize = 10.sp,
                                smallTextFontSize = 0.sp,
                                bigTextSuffix = "",
                                smallText = "",
                            )
                            Text(
                                modifier = Modifier
                                    .weight(8f)
                                    .padding(start = 10.dp),
                                text = getTitleLang(mockToDoTask, lang = lang),
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
                                    mockToDoTask.priority.color
                                )
                            }
                        }
                    }
//                    if (toDoTask.description.isNotEmpty()) {
                    Text(
                        text = getDescriptionLang(mockToDoTask, lang = lang),
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.taskItemTextColor,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }*/
            }
        }
    }
}

fun getDescriptionLang(mockToDoTask: MockToDoTask, lang: String): String =
    if (lang == Languages.English.lang) mockToDoTask.description
    else mockToDoTask.descriptionAR


fun getTitleLang(mockToDoTask: MockToDoTask, lang: String): String =
    if (lang == Languages.English.lang) mockToDoTask.title
    else mockToDoTask.titleAR

@Composable
fun MockCardExpanded(
    mockToDoTask: MockToDoTask,
    onAdd: (MockToDoTask) -> Unit,
    lang: String
) {

    Row(Modifier.padding(bottom = SMALL_PADDING)) {
        Text(
            modifier = Modifier
                .weight(8f),
            text = getTitleLang(mockToDoTask, lang),
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
                    mockToDoTask.priority.color
                )
            }
        }
    }
    if (mockToDoTask.description.isNotEmpty()) {
        Text(
            text = getDescriptionLang(mockToDoTask, lang),
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.taskItemTextColor,
            style = MaterialTheme.typography.subtitle1,
//        maxLines = 2,
//        overflow = TextOverflow.Ellipsis
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MEDIUM_PADDING),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ) {
        if (mockToDoTask.startDate.isNotEmpty()) {
            /* Divider(
                 Modifier
                     .fillMaxWidth()
                     .padding(vertical = LARGE_PADDING, horizontal = SMALL_PADDING)
             )*/
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
                        text = mockToDoTask.startDate,
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
                        text = mockToDoTask.endDate,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.taskItemTextColor
                    )
                }

            }
//            Divider(Modifier.padding(vertical = LARGE_PADDING, horizontal = SMALL_PADDING))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(top = MEDIUM_PADDING)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CustomComponent(
                indicatorValue = getTaskCounterInt(mockToDoTask.taskCounter),
                maxIndicatorValue = getMaxTaskInt(mockToDoTask.maxTask),
                canvasSize = 48.dp,
                backgroundIndicatorStrokeWidth = 12f,
                foregroundIndicatorStrokeWidth = 12f,
                foregroundIndicatorColor = MaterialTheme.colors.foregroundIndicatorColor,
                backgroundIndicatorColor = MaterialTheme.colors.backgroundIndicatorColor,
                bigTextFontSize = 15.sp,
                smallTextFontSize = 0.sp,
                bigTextSuffix = "",
                smallText = "",
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { onAdd(mockToDoTask) }) {
                Text(text = "Add Task", color = MaterialTheme.colors.topAppBarContentColor)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview(showBackground = true)
private fun ChallengesContentPreview() {
    MockTaskItem(
        MockToDoTask(
            title = "Mock Task",
            description = "This is a description of the Mock Task , Say hello Description",
            startDate = "02/05/22",
            endDate = "30/05/22",
            maxTask = "9",
            taskCounter = "9",
        ),
        lang = "en",
        onAdd = {}
    )
}
