package com.tuwaiq.husam.taskstodoapp.ui.screens.suggested

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tuwaiq.husam.taskstodoapp.ui.theme.HighPriorityColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.LowPriorityColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.MediumPriorityColor

@Composable
fun SuggestedContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(LowPriorityColor)
        ) {
            Text(
                text = "Suggested Screen",
                fontSize = MaterialTheme.typography.h4.fontSize
            )
        }
        Box(
            modifier = Modifier
                .background(HighPriorityColor)
        ) {
            Text(
                text = "Suggested Screen",
                fontSize = MaterialTheme.typography.h4.fontSize
            )
        }
        Box(
            modifier = Modifier
                .background(MediumPriorityColor)
        ) {
            Text(
                text = "Suggested Screen",
                fontSize = MaterialTheme.typography.h4.fontSize
            )
        }
    }
}

@Composable
@Preview
private fun SuggestedContentPreview() {
    SuggestedContent()
}