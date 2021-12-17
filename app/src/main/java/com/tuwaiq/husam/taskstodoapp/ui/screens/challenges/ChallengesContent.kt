package com.tuwaiq.husam.taskstodoapp.ui.screens.challenges

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
fun ChallengesContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MediumPriorityColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
            Text(
                text = "Challenges Screen",
                fontSize = MaterialTheme.typography.h4.fontSize
            )
    }
}

@Composable
@Preview
private fun ChallengesContentPreview() {
    ChallengesContent()
}