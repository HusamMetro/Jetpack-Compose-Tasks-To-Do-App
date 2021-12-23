package com.tuwaiq.husam.taskstodoapp.ui.screens.login

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel

@Composable
fun LoginScreen(navController: NavHostController,sharedViewModel: SharedViewModel) {

    Scaffold(
        /*topBar = {
            LoginAppBar()
        },*/
        content = {
            LoginContent(navController,sharedViewModel)
        },
    )
}

/*
@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}*/
