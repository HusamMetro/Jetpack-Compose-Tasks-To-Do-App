package com.tuwaiq.husam.taskstodoapp.ui.screens.register

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
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants
import com.tuwaiq.husam.taskstodoapp.util.getInvalidMessage
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun RegisterScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var snackBoolean by remember { mutableStateOf(false) }
    var firstTime by remember { mutableStateOf(false) }
    if (firstTime) {
        LaunchedEffect(key1 = snackBoolean) {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = context.getString(R.string.email_exists_message),
                    actionLabel = context.getString(R.string.ok)
                )
            }
        }
    }

    var name by remember { mutableStateOf("") }
    var nameIsError by remember { mutableStateOf(false) }
    var nameIsErrorMsg by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }
    var emailIsError by remember { mutableStateOf(false) }
    var emailIsErrorMsg by remember { mutableStateOf("") }

    var phone by remember { mutableStateOf("") }
    var phoneIsError by remember { mutableStateOf(false) }
    var phoneIsErrorMsg by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var passwordIsError by remember { mutableStateOf(false) }
    var passwordIsErrorMsg by remember { mutableStateOf("") }

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            RegisterContent(
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
                email = email,
                emailOnValueChange = { newText ->
                    emailIsError = !newText.validEmail {
                        emailIsErrorMsg = getInvalidMessage(it, context)
                    }
                    email = newText

                },
                emailIsError = emailIsError,
                emailIsErrorMsg = emailIsErrorMsg,
                phone = phone,
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
                phoneIsError = phoneIsError,
                phoneIsErrorMsg = phoneIsErrorMsg,
                password = password,
                passwordOnValueChange = { newText ->
                    passwordIsError = !newText.validator()
                        .nonEmpty()
                        .atleastOneUpperCase()
                        .atleastOneNumber()
                        .minLength(6)
                        .maxLength(12)
                        .addErrorCallback {
                            passwordIsErrorMsg = getInvalidMessage(it, context)
                        }.check()
                    password = newText
                },
                passwordIsError = passwordIsError,
                passwordIsErrorMsg = passwordIsErrorMsg,
                signUpOnClicked = { mutableBoolean ->
                    when {
                        nameIsError || emailIsError || passwordIsError || phoneIsError ||
                                name.isEmpty() || email.isEmpty() ||
                                password.isEmpty() || phone.isEmpty()
                        -> {
                            mutableBoolean.value = false
                        }
                        else -> {
                            Log.e("else", "Else")
                            val userName: String = name.trim()
                            val emailTrimmed: String = email.trim()
                            val phoneNumber: String = phone.trim()
                            sharedViewModel.registerFirebase(
                                email = email,
                                password = password,
                                userName = userName,
                                emailTrimmed = emailTrimmed,
                                phoneNumber = phoneNumber,
                                lifecycleOwner = context as MainActivity
                            ).observe(context) {
                                if (it) {
                                    navController.navigate(Constants.LIST_SCREEN) {
                                        popUpTo(Constants.LOGIN_SCREEN) {
                                            inclusive = true
                                        }
                                    }
                                } else {
                                    mutableBoolean.value = false
                                    firstTime = true
                                    snackBoolean = !snackBoolean
                                }
                            }
                        }
                    }
                }
            )
        }
    )

}
