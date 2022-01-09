package com.tuwaiq.husam.taskstodoapp.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.tuwaiq.husam.taskstodoapp.ui.theme.ERROR_MESSAGE_PADDING
import com.tuwaiq.husam.taskstodoapp.ui.theme.customOutlinedTextFieldColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.taskItemTextColor


@Composable
fun CommonTextField(
    modifier: Modifier = Modifier.fillMaxWidth(0.8f),
    value: String,
    onValueChange: (updatedText: String) -> Unit,
    strResId: Int,
    singleLine: Boolean = true,
    icon: ImageVector,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    textColor: Color = MaterialTheme.colors.taskItemTextColor,
    colors: TextFieldColors = MaterialTheme.colors.customOutlinedTextFieldColor /*TextFieldDefaults.outlinedTextFieldColors(
        textColor = textColor,
    )*/,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMsg: String = ""
) {
    Column(
        modifier = Modifier
            .padding(
                bottom = if (isError) {
                    0.dp
                } else {
                    ERROR_MESSAGE_PADDING
                }
            )
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = stringResource(id = strResId)) },
            singleLine = singleLine,
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "${stringResource(strResId)} Icon "
                )
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = colors,
            enabled = enabled,
            isError = isError
        )
        if (isError) {
            Text(
                text = errorMsg,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun CommonPasswordTextField(
    modifier: Modifier = Modifier.fillMaxWidth(0.8f),
    text: String,
    onValueChange: (updatedText: String) -> Unit,
    strResId: Int,
    icon: ImageVector,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    textColor: Color = MaterialTheme.colors.taskItemTextColor,
    colors: TextFieldColors = MaterialTheme.colors.customOutlinedTextFieldColor /*TextFieldDefaults.outlinedTextFieldColors(
        textColor = textColor,
    )*/,
    isError: Boolean = false,
    errorMsg: String = ""
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    val iconB = if (passwordVisibility)
        Icons.Filled.Visibility
    else
        Icons.Filled.VisibilityOff
    Column(
        modifier = Modifier
            .padding(
                bottom = if (isError) {
                    0.dp
                } else {
                    ERROR_MESSAGE_PADDING
                }
            )
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = text,
            onValueChange = onValueChange,
            label = { Text(text = stringResource(id = strResId)) },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "${stringResource(id = strResId)} Icon "
                )
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = colors,
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(iconB, contentDescription = "Visibility Icon")
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            isError = isError,
        )
        if (isError) {
            Text(
                text = errorMsg,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

}


@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    progressIndicatorColor: Color = Color.White,
    onClick: (MutableState<Boolean>) -> Unit,
) {
    var clicked = remember { mutableStateOf(false) }
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = { clicked.value = !clicked.value; onClick(clicked) },
        contentPadding = PaddingValues(),
    ) {
        Row(
            modifier = Modifier
                .background(gradient)
                .padding(horizontal = 50.dp, vertical = 10.dp)
//                .fillMaxWidth(0.20f)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = text, color = textColor)
            if (clicked.value) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            }
        }
    }
}

