package com.tuwaiq.husam.taskstodoapp.ui.screens.settings

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.BottomBar
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.coroutines.launch

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
        /*topBar = {
            SettingsAppBar()
        },*/
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomBar(navController = navController)
        },
        content = {
            SettingsContent(
                onLaunchedEffect = {
                    name = sharedViewModel.user.username
                    email = sharedViewModel.user.email
                    phone = sharedViewModel.user.phoneNumber
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
                            phoneIsErrorMsg = it
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
                        .noSpecialCharacters()
                        .addErrorCallback {
                            nameIsErrorMsg = it
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
                            updateInFirestore(
                                newUsername = name,
                                newPhoneNumber = phone,
                                mutableBoolean = mutableBoolean,
                                onSucceed = {
                                    firstTime = true
                                    snackMessageBoolean = true
                                    snackBoolean = !snackBoolean
                                },
                                onFailed = {
                                    firstTime = true
                                    snackMessageBoolean = false
                                    snackBoolean = !snackBoolean
                                }
                            )
                            sharedViewModel.loadUserInformation()
                        }
                    }
                },
                signOutOnClicked = {
                    FirebaseAuth.getInstance().signOut()
                    sharedViewModel.persistRememberState(false)
                    navController.navigate(Constants.LOGIN_SCREEN) {
                        /*popUpTo(SETTINGS_SCREEN){
                        inclusive = true
                    } */
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

fun updateInFirestore(
    newUsername: String,
    newPhoneNumber: String,
    mutableBoolean: MutableState<Boolean>,
    onSucceed: () -> Unit,
    onFailed: () -> Unit,

    ) {
    val userUID = FirebaseAuth.getInstance().currentUser?.uid
    val collectionRef = Firebase.firestore.collection("users")
    collectionRef.document(userUID.toString())
        .update(
            "username",
            newUsername,
            "phoneNumber",
            newPhoneNumber
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.e("update", "Successful")
                onSucceed()
            } else {
                Log.e("update", "Failed")
                onFailed()
            }
            mutableBoolean.value = false
        }

}
/*
@Composable
@Preview
private fun SettingsScreenPreview() {
    SettingsScreen(sharedViewModel =  SharedViewModel(context = Application()))
}*/
