package com.tuwaiq.husam.taskstodoapp.ui.screens.login

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(
    /*sharedViewModel: SharedViewModel,
    navController: NavHostController*/
) {
    Scaffold(
        topBar = {
            LoginAppBar()
        },
        content = {
            LoginContent()
        },
    )
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}