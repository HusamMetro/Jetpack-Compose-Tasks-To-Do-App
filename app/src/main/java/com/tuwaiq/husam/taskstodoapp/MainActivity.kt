package com.tuwaiq.husam.taskstodoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tuwaiq.husam.taskstodoapp.navigation.SetupNavigation
import com.tuwaiq.husam.taskstodoapp.ui.theme.TasksToDoAppTheme
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    //    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var sharedViewModel: SharedViewModel

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("start","Main Activity ")
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        setContent {
            TasksToDoAppTheme {
                navController = rememberNavController()
                SetupNavigation(
                    navController = navController,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }
}

