package com.tuwaiq.husam.taskstodoapp.ui.screens.login

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.ui.theme.LARGEST_PADDING
import com.tuwaiq.husam.taskstodoapp.ui.theme.cardFirstColor
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun LoginScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var snackBoolean by remember { mutableStateOf(false) }
    var firstTime by remember { mutableStateOf(false) }
    if (firstTime) {
        LaunchedEffect(key1 = snackBoolean) {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = context.getString(R.string.email_password_check_message),
                    actionLabel = context.getString(R.string.ok)
                )
            }
        }
    }

    var email by remember { mutableStateOf("") }
    var emailIsError by remember { mutableStateOf(false) }
    var emailIsErrorMsg by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var passwordIsError by remember { mutableStateOf(false) }
    var passwordIsErrorMsg by remember { mutableStateOf("") }

    var checked by remember { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        /*topBar = {
            LoginAppBar()
        },*/
        content = {
            LoginContent(
                registerOnClicked = {
                    navController.navigate(Constants.REGISTER_SCREEN)
                },
                onForgotPassword = {

                },
                emailOnValueChange = { newText ->
                    emailIsError = !newText.validEmail { emailIsErrorMsg = it }
                    email = newText
                },
                email = email,
                emailIsError = emailIsError,
                emailIsErrorMsg = emailIsErrorMsg,
                password = password,
                passwordOnValueChange = { newText ->
                    passwordIsError = !newText.validator()
                        .nonEmpty()
                        .atleastOneUpperCase()
                        .atleastOneNumber()
                        .minLength(6)
                        .maxLength(12)
                        .addErrorCallback {
                            passwordIsErrorMsg = it
                        }.check()
                    password = newText
                },
                passwordIsError = passwordIsError,
                passwordIsErrorMsg = passwordIsErrorMsg,
                checked = checked,
                onCheckedChange = {
                    checked = it
                },
                signInOnClicked = { mutableBoolean ->
                    when {
                        emailIsError || passwordIsError -> {
//                                    emailTextInputSignup.helperText = "*"
                            mutableBoolean.value = false
                        }
                        else -> {
                            // create an instance and create a register with email and password
                            FirebaseAuth.getInstance()
                                .signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->

                                    // if the registration is sucessfully done
                                    if (task.isSuccessful) {
                                        //firebase register user
//                                                val firebaseUser: FirebaseUser =
//                                                    task.result!!.user!!
//                                                val user = User(userName, email, phoneNumber)
//                                                saveUser(user)
                                        sharedViewModel.persistRememberState(checked)
                                        sharedViewModel.loadUserInformation()
                                        navController.navigate(Constants.LIST_SCREEN) {
                                            popUpTo(Constants.LOGIN_SCREEN) {
                                                inclusive = true
                                            }
                                        }
                                    } else {
                                        // if the registration is not successful then show error massage

                                        firstTime = true
                                        snackBoolean = !snackBoolean

                                        mutableBoolean.value = false
                                        Log.e("register", "${task.exception?.message}")
                                    }
                                }
                        }
                    }
                }
            )
        },
    )
}

/*
@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}*/
