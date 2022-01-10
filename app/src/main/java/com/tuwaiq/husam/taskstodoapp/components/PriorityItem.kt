package com.tuwaiq.husam.taskstodoapp.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.tuwaiq.husam.taskstodoapp.data.models.Priority
import com.tuwaiq.husam.taskstodoapp.ui.theme.LARGE_PADDING
import com.tuwaiq.husam.taskstodoapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.tuwaiq.husam.taskstodoapp.ui.theme.Typography

@Composable
fun PriorityItem(priority: Priority) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
            drawCircle(color = priority.color)
        }
        Text(
            modifier = Modifier.padding(start = LARGE_PADDING),
            text = getDisplayName(priority, context = LocalContext.current),
            style = Typography.subtitle2,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
@Preview
private fun PriorityItemPreview() {
    PriorityItem(priority = Priority.LOW)
}