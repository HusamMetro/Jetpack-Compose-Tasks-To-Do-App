package com.tuwaiq.husam.taskstodoapp.ui.screens.settings

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.tuwaiq.husam.taskstodoapp.ui.theme.HighPriorityColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.LowPriorityColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.MediumGray
import com.tuwaiq.husam.taskstodoapp.ui.theme.MediumPriorityColor

@Composable
fun SettingsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Settings Screen",
            fontSize = MaterialTheme.typography.h4.fontSize
        )
        Box(
            modifier = Modifier
                .background(Color.Gray)
        ) {
            Text(
                text = "Settings Screen",
                fontSize = MaterialTheme.typography.h4.fontSize
            )
        }
        Box(
            modifier = Modifier
                .background(Color.Green)
        ) {
            Text(
                text = "Settings Screen",
                fontSize = MaterialTheme.typography.h4.fontSize
            )
        }
        Box(
            modifier = Modifier
                .background(Color.Blue)
        ) {
            Text(
                text = "Settings Screen",
                fontSize = MaterialTheme.typography.h4.fontSize
            )
        }
    }
}

@Composable
@Preview
private fun SettingsContentPreview() {
    SettingsContent()
}