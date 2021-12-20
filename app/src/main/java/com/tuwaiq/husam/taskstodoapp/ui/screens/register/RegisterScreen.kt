package com.tuwaiq.husam.taskstodoapp.ui.screens.register

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun RegisterScreen(navController: NavHostController) {
    RegisterContent()
}
@Preview
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(rememberNavController())
}