package com.tuwaiq.husam.taskstodoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.tuwaiq.husam.taskstodoapp.navigation.SetupNavigation
import com.tuwaiq.husam.taskstodoapp.ui.theme.TasksToDoAppTheme
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import java.util.*

@ExperimentalAnimationApi
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavHostController
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        setContent {

            ToDoApplication.appContext = LocalContext.current

            sharedViewModel.readDarkThemeState()
            sharedViewModel.readRememberState()
            sharedViewModel.readLangStateState()

            val darkTheme by sharedViewModel.darkThemeState.collectAsState()
            val lang by sharedViewModel.langState.collectAsState()

            SetLanguage(lang, this)

            TasksToDoAppTheme(darkTheme = darkTheme) {
                notification(sharedViewModel, this)
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
@Composable
fun SetLanguage(lang: String, mainActivity: MainActivity) {
    val locale = Locale(lang)
    val configuration = LocalConfiguration.current
    val oldLan = LocalConfiguration.current.locales[0].language.trim()
    configuration.setLocale(locale)
    val resources = LocalContext.current.resources
    resources.updateConfiguration(configuration, resources.displayMetrics)
    Log.e("language", LocalConfiguration.current.locales[0].language.trim())
    if (oldLan != lang) {
        mainActivity.startActivity(Intent(mainActivity, MainActivity::class.java))
        mainActivity.finish()
    }
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun notification(viewModel: SharedViewModel, mainActivity: MainActivity) {
    viewModel.displayNotification(mainActivity)
}


