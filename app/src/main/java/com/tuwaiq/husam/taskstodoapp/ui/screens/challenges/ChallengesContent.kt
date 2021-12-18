package com.tuwaiq.husam.taskstodoapp.ui.screens.challenges

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuwaiq.husam.taskstodoapp.components.CustomComponent

@Composable
fun ChallengesContent() {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var valueState by remember { mutableStateOf("0") }
        CustomComponent(
            indicatorValue = if (valueState.isEmpty()) 0 else valueState.toInt() ,
            canvasSize = 70.dp,
            backgroundIndicatorStrokeWidth = 15f,
            foregroundIndicatorStrokeWidth = 15f,
            bigTextFontSize = 15.sp,
            smallTextFontSize = 0.sp,
            bigTextSuffix = "%",
            smallText = "",
        )
        Spacer(modifier = Modifier.padding(16.dp))
        TextField(value = valueState,
            onValueChange = {
                valueState = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext ={} )

        )

    }
}

@Composable
@Preview
private fun ChallengesContentPreview() {
    ChallengesContent()
}