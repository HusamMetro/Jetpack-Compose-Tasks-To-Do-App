package com.tuwaiq.husam.taskstodoapp.ui.screens.register

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel

@Composable
fun RegisterScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {
    RegisterContent(navController,sharedViewModel)
}
/*
@Preview
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(rememberNavController())
}*/
