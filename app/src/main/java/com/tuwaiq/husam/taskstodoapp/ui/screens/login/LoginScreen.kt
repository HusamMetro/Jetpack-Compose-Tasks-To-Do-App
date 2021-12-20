package com.tuwaiq.husam.taskstodoapp.ui.screens.login

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun LoginScreen(navController: NavHostController) {
    Scaffold(
        /*topBar = {
            LoginAppBar()
        },*/
        content = {
            LoginContent(navController)
        },
    )
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}