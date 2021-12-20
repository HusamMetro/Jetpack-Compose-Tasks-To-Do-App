package com.tuwaiq.husam.taskstodoapp.ui.screens.challenges

import androidx.compose.animation.animateContentSize
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuwaiq.husam.taskstodoapp.components.CustomComponent
import com.tuwaiq.husam.taskstodoapp.data.models.MockToDoTask
import com.tuwaiq.husam.taskstodoapp.ui.theme.*
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.RequestState
import kotlin.random.Random

@ExperimentalMaterialApi
@Composable
fun ChallengesContent(mockTasks: List<MockToDoTask>, sharedViewModel: SharedViewModel) {
        LazyColumn(
            Modifier.padding(bottom = TOP_APP_BAR_HEIGHT),
            contentPadding = PaddingValues(MEDIUM_PADDING),
            verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
        ){
            items(
                items = mockTasks,
                // There is a bug here if clicked fast the Key will repeat ... Fix Later
                key = { task ->
                    task.id
                }
            ) { task ->
                MockTaskItem(mockToDoTask = task,{})
            }
        }


}
@ExperimentalMaterialApi
@Composable
fun MockTaskItem(
    mockToDoTask: MockToDoTask,
    navigateToTaskScreens: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        onClick = {
            navigateToTaskScreens(mockToDoTask.id)
        }
    ) {
        var expandState by remember { mutableStateOf(false) }
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
                        end = LARGE_PADDING
                    )
                    .fillMaxWidth()
            ) {
                Row {
                    CustomComponent(
                        indicatorValue = Random.nextInt(6),
                        canvasSize = if (expandState) 60.dp else 32.dp,
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
                        text = mockToDoTask.title,
                        color = MaterialTheme.colors.taskItemTextColor,
                        style = if (!expandState) MaterialTheme.typography.h5 else MaterialTheme.typography.h4,
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
                Text(
                    text = mockToDoTask.description,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (expandState) {
                    Text(
                        text = "Start Date : 8/10/2021",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.taskItemTextColor
                    )
                    Text(
                        text = "End Date : 20/12/2021",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.taskItemTextColor
                    )
                    Row {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "n")
                        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                        Text(
                            text = "Anything ... just to fill the column",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colors.taskItemTextColor
                        )
                    }
                }
                Row(
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
                }
            }
        }
    }
}

/*

@Composable
@Preview
private fun ChallengesContentPreview() {
    ChallengesContent(mockTasks, sharedViewModel)
}*/
