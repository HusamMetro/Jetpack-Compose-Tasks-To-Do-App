package com.tuwaiq.husam.taskstodoapp.ui.screens.settings

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.ui.theme.topAppBarBackgroundColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.topAppBarContentColor

@Composable
fun SettingsAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.settings),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
    )
}

@Composable
@Preview
private fun SettingsAppBarPreview() {
    SettingsAppBar()
}