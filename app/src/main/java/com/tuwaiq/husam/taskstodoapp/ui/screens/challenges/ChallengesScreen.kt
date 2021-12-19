package com.tuwaiq.husam.taskstodoapp.ui.screens.challenges

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.tuwaiq.husam.taskstodoapp.components.BottomBar
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel

@ExperimentalMaterialApi
@Composable
fun ChallengesScreen(
    sharedViewModel: SharedViewModel,
    navController: NavHostController
) {

    LaunchedEffect(key1 = Unit ){
        sharedViewModel.getMockTasks()
        Log.e("at Launched Effect", "After")
    }
    Log.e("at Challenges", "After")
//    val mockTasks by sharedViewModel.getMockTasks().observeAsState()
//    var checkMockTasks by remember { mutableStateOf(false) }
    /* var mockTasks by remember { mutableStateOf(MutableLiveData<List<MockToDoTask>>()) }
     if (mockTasks.value == null) {
         mockTasks = sharedViewModel.getMockTasks()
     }*/
//    val mockTasks = emptyList<MockToDoTask>()
//    val mockTasks by sharedViewModel.mockTasks.collectAsState()
    /*LaunchedEffect(key1 = true){
        sharedViewModel.getMockTasks()
    }*/

    Scaffold(
        topBar = {
            ChallengesAppBar()
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
        content = {
            if (sharedViewModel.errorMessage.isEmpty()) {
                ChallengesContent(sharedViewModel.mockTasks, sharedViewModel)
            } else {
                Text(text = sharedViewModel.errorMessage)
            }
            /*mockTasks?.let {
                ChallengesContent(it, sharedViewModel)
            }*/
        }
    )
}

/*
@Composable
private fun ChallengesScreenPreview() {
    SuggestedScreen(sharedViewModel = SharedViewModel(Application()))
}*/
