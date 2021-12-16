package com.tuwaiq.husam.taskstodoapp.ui.screens.suggested

import android.app.Application
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.tuwaiq.husam.taskstodoapp.navigation.BottomBar
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel

@Composable
fun SuggestedScreen(
    sharedViewModel: SharedViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            SuggestedAppBar()
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
        content = {
            SuggestedContent()
        }
    )
}

/*
@Composable
private fun SuggestedScreenPreview() {
    SuggestedScreen(sharedViewModel = SharedViewModel(Application()))
}*/
