package com.tuwaiq.husam.taskstodoapp.ui.screens.challenges

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.AnimatedShimmerEffect
import com.tuwaiq.husam.taskstodoapp.components.BottomBar
import com.tuwaiq.husam.taskstodoapp.ui.theme.EMPTY_ICON_SIZE
import com.tuwaiq.husam.taskstodoapp.ui.theme.MEDIUM_PADDING
import com.tuwaiq.husam.taskstodoapp.ui.theme.MediumGray
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel

@ExperimentalMaterialApi
@Composable
fun ChallengesScreen(
    sharedViewModel: SharedViewModel,
    navController: NavHostController
) {

    LaunchedEffect(key1 = Unit) {
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
                if (sharedViewModel.mockTasks.isEmpty()) {
                    Column(
                        modifier = Modifier.padding(MEDIUM_PADDING),
                        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
                    ) {
                        repeat(8) {
                            AnimatedShimmerEffect()
                        }
                    }
                } else {
                    ChallengesContent(sharedViewModel.mockTasks, sharedViewModel)
                }
            } else {
                EmptyContentNoConnection()
            }
            /*mockTasks?.let {
                ChallengesContent(it, sharedViewModel)
            }*/
        }
    )
}

@Composable
fun EmptyContentNoConnection() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(EMPTY_ICON_SIZE),
            painter = painterResource(id = R.drawable.ic_baseline_wifi_off_24),
            contentDescription = stringResource(
                R.string.sad_face_icon
            ),
            tint = MediumGray
        )
        Text(
            text = "No Network Please Reconnect",
            color = MediumGray,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize
        )
    }
}

/*@Composable
@Preview
fun Preview() {
    EmptyContentNoConnection()
}*/

/*@Preview
@Composable
private fun ChallengesScreenPreview() {
    Column(
        modifier = Modifier.padding(MEDIUM_PADDING),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ) {
        repeat(8) {
            AnimatedShimmerEffect()
        }
    }
}*/
