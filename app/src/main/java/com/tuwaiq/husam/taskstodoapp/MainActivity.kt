package com.tuwaiq.husam.taskstodoapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.tuwaiq.husam.taskstodoapp.navigation.SetupNavigation
import com.tuwaiq.husam.taskstodoapp.ui.theme.TasksToDoAppTheme
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavHostController

    //    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("start", "Main Activity ")
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        setContent {
            sharedViewModel.readDarkThemeState()
            sharedViewModel.readRememberState()
            val darkTheme by sharedViewModel.darkThemeState.collectAsState()
            TasksToDoAppTheme(darkTheme = darkTheme) {
//                TestLayout(context = this,sharedViewModel,this)
                navController = rememberAnimatedNavController()
                SetupNavigation(
                    navController = navController,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun TestLayout(context: Context,viewModel: SharedViewModel,mainActivity: MainActivity) {
    Button(onClick = { viewModel.displayNotification(mainActivity)}) {
        Text(text = "Click me")
    }

   /* var datePicked : String? by remember {
        mutableStateOf(null)
    }

    val updatedDate = { date : Long? ->
        datePicked = DateFormater(date)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        DatePickerview( datePicked, updatedDate )
    }*/

/*
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            if (checkForInternet(context)) {
                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show()
            }
        }) {
        Text("Click B")
        }

    }*/
}

private fun checkForInternet(context: Context): Boolean {

    // register activity with the connectivity manager service
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // if the android version is equal to M
    // or greater we need to use the
    // NetworkCapabilities to check what type of
    // network has the internet connection

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        // Returns a Network object corresponding to
        // the currently active default data network.
        val network = connectivityManager.activeNetwork ?: return false

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            // else return false
            else -> false
        }
    } else {
        // if the android version is below M
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}

