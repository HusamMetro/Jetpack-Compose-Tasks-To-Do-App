package com.tuwaiq.husam.taskstodoapp.navigation

import androidx.navigation.NavHostController
import com.tuwaiq.husam.taskstodoapp.util.Action
import com.tuwaiq.husam.taskstodoapp.util.Constants.LIST_SCREEN

class Screens(navController: NavHostController) {
    val list: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}"){
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }
    val task : (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}