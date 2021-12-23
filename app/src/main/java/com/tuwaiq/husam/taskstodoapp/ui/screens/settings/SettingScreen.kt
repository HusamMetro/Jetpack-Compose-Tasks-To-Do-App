package com.tuwaiq.husam.taskstodoapp.ui.screens.settings

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.tuwaiq.husam.taskstodoapp.components.BottomBar
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel

@Composable
fun SettingsScreen(
    sharedViewModel: SharedViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            SettingsAppBar()
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
        content = {
            SettingsContent(navController = navController, sharedViewModel = sharedViewModel)
        },
    )
}

/*
@Composable
@Preview
private fun SettingsScreenPreview() {
    SettingsScreen(sharedViewModel =  SharedViewModel(context = Application()))
}*/
