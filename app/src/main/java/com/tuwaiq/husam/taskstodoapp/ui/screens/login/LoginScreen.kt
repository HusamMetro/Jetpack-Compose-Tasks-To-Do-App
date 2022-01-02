package com.tuwaiq.husam.taskstodoapp.ui.screens.login

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var snackBoolean by remember { mutableStateOf(false) }
    var firstTime by remember { mutableStateOf(false) }
    if (firstTime) {
        LaunchedEffect(key1 = snackBoolean) {
            scope.launch {
               scaffoldState.snackbarHostState.showSnackbar(
                    message = "check your Email/Password",
                    actionLabel = context.getString(R.string.ok)
                )
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        /*topBar = {
            LoginAppBar()
        },*/
        content = {
            LoginContent(navController, sharedViewModel, snackBoolean = {
                firstTime = true
                snackBoolean = !snackBoolean
            })
        },
    )
}

/*
@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}*/
