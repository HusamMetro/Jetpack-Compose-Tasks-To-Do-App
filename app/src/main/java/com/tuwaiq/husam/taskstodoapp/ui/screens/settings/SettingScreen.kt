package com.tuwaiq.husam.taskstodoapp.ui.screens.settings

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.tuwaiq.husam.taskstodoapp.MainActivity
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.BottomBar
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants
import com.tuwaiq.husam.taskstodoapp.util.getInvalidMessage
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun SettingsScreen(
    sharedViewModel: SharedViewModel,
    navController: NavHostController
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var snackBoolean by remember { mutableStateOf(false) }
    var snackMessageBoolean by remember { mutableStateOf(false) }
    var firstTime by remember { mutableStateOf(false) }

    if (firstTime) {
        LaunchedEffect(key1 = snackBoolean) {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = if (snackMessageBoolean) context.getString(R.string.update_successful_message)
                    else context.getString(R.string.update_failed_message),
                    actionLabel = context.getString(R.string.ok)
                )
            }
        }
    }

    var phone by remember { mutableStateOf("") }
    var phoneIsError by remember { mutableStateOf(false) }
    var phoneIsErrorMsg by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }

    var name by remember { mutableStateOf("") }
    var nameIsError by remember { mutableStateOf(false) }
    var nameIsErrorMsg by remember { mutableStateOf("") }

    var darkMode by remember { mutableStateOf(sharedViewModel.darkThemeState.value) }
    var language by remember { mutableStateOf(sharedViewModel.langState.value) }


    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomBar(navController = navController)
        },
        content = {
            SettingsContent(
                onLaunchedEffect = {
                    val user = sharedViewModel.getUserInformation()
                    name = user.username
                    email = user.email
                    phone = user.phoneNumber
                },
                email = email,
                emailOnValueChange = { email = it },
                phone = phone,
                phoneIsError = phoneIsError,
                phoneIsErrorMsg = phoneIsErrorMsg,
                phoneOnValueChange = { newText ->
                    phoneIsError = !newText.validator()
                        .nonEmpty()
                        .noSpecialCharacters()
                        .onlyNumbers()
                        .minLength(10)
                        .maxLength(12)
                        .addErrorCallback {
                            phoneIsErrorMsg = getInvalidMessage(it, context)
                        }
                        .check()
                    phone = newText
                },
                name = name,
                nameOnValueChange = { newText ->
                    nameIsError = !newText.validator()
                        .nonEmpty()
                        .minLength(3)
                        .noNumbers()
                        .addErrorCallback {
                            nameIsErrorMsg = getInvalidMessage(it, context)
                        }.check()
                    name = newText
                },
                nameIsError = nameIsError,
                nameIsErrorMsg = nameIsErrorMsg,
                updateOnClicked = { mutableBoolean ->
                    when {
                        nameIsError || phoneIsError || name.isEmpty() || phone.isEmpty() -> {
                            mutableBoolean.value = false
                        }
                        else -> {
                            sharedViewModel.updateUserFirestore(
                                name = name,
                                phoneNumber = phone,
                                lifecycleOwner = context as MainActivity
                            ).observe(context) {
                                if (it) {
                                    Log.e("update", "Successful")
                                    firstTime = true
                                    snackMessageBoolean = true
                                    snackBoolean = !snackBoolean
                                    mutableBoolean.value = false
                                } else {
                                    Log.e("update", "Failed")
                                    firstTime = true
                                    snackMessageBoolean = false
                                    snackBoolean = !snackBoolean
                                    mutableBoolean.value = false
                                }
                            }
                        }
                    }
                },
                signOutOnClicked = {
                    sharedViewModel.signOutFirebase()
                    navController.navigate(Constants.LOGIN_SCREEN) {
                        popUpTo(Constants.LIST_SCREEN) {
                            inclusive = true
                        }
                    }
                },
                darkMode = darkMode,
                darkModeOnChange = { darkThemeState ->
                    darkMode = darkThemeState
                    sharedViewModel.persistDarkThemeState(darkThemeState)
                },
                language = language,
                languageOnChange = { langState ->
                    language = langState
                    sharedViewModel.persistLangState(langState)
                }
            )
        },
    )
}
