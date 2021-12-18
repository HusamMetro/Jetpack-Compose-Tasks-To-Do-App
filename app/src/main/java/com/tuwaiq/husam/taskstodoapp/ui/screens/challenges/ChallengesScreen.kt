package com.tuwaiq.husam.taskstodoapp.ui.screens.challenges

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.tuwaiq.husam.taskstodoapp.components.BottomBar
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel

@Composable
fun ChallengesScreen(
    sharedViewModel: SharedViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            ChallengesAppBar()
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
        content = {
            ChallengesContent()
        }
    )
}

/*
@Composable
private fun ChallengesScreenPreview() {
    SuggestedScreen(sharedViewModel = SharedViewModel(Application()))
}*/
