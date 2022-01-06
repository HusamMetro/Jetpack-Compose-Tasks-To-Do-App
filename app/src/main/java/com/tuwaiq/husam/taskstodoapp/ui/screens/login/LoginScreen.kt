package com.tuwaiq.husam.taskstodoapp.ui.screens.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.CommonTextField
import com.tuwaiq.husam.taskstodoapp.ui.theme.SMALL_PADDING
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants
import com.tuwaiq.husam.taskstodoapp.util.getInvalidMessage
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
    var snackMessage by remember { mutableStateOf("") }
    var firstTime by remember { mutableStateOf(false) }
    if (firstTime) {
        LaunchedEffect(key1 = snackBoolean) {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = snackMessage,
                    actionLabel = context.getString(R.string.ok)
                )
            }
        }
    }
    val focusManager: FocusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var emailIsError by remember { mutableStateOf(false) }
    var emailIsErrorMsg by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var passwordIsError by remember { mutableStateOf(false) }
    var passwordIsErrorMsg by remember { mutableStateOf("") }

    var checked by remember { mutableStateOf(false) }

    var openDialog by remember { mutableStateOf(false) }

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
                    openDialog = true
                },
                emailOnValueChange = { newText ->
                    emailIsError = !newText.validEmail {
                        emailIsErrorMsg = getInvalidMessage(it, context)
                    }
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
                            passwordIsErrorMsg = getInvalidMessage(it, context)
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
                        emailIsError || passwordIsError || email.isEmpty() || password.isEmpty() -> {
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
                                        snackMessage =
                                            context.getString(R.string.email_password_check_message)
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
            if (openDialog) {
                AlertDialog(
                    title = {
                        Text(
                            text = stringResource(id = R.string.reset_password),
                            fontSize = MaterialTheme.typography.h5.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING),
                        ) {
                            Text(
                                text = "",
                                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                                fontWeight = FontWeight.Normal
                            )
                            CommonTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = email,
                                onValueChange = { newText ->
                                    emailIsError = !newText.validEmail {
                                        emailIsErrorMsg = getInvalidMessage(it, context)
                                    }
                                    email = newText
                                },
                                strResId = R.string.email_address,
                                singleLine = true,
                                icon = Icons.Filled.Email,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                isError = emailIsError,
                                errorMsg = emailIsErrorMsg
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                when {
                                    emailIsError || email.isEmpty() -> {
//                                    emailTextInputSignup.helperText = "*"
                                    }
                                    else -> {
                                        openDialog = false
                                        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    snackMessage =
                                                        context.getString(R.string.password_reset_success)
                                                    firstTime = true
                                                    snackBoolean = !snackBoolean
                                                    Log.e(
                                                        "forgot",
                                                        context.getString(R.string.password_reset_success)
                                                    )
                                                } else {
                                                    Log.e(
                                                        "forgot",
                                                        task.exception!!.message.toString()
                                                    )
                                                    snackMessage =
                                                        task.exception!!.message.toString()
                                                    firstTime = true
                                                    snackBoolean = !snackBoolean
                                                }
                                            }
                                    }
                                }
                            }
                        ) {
                            Text(text = stringResource(R.string.send))
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            onClick = {
                                openDialog = false
                            }
                        ) {
                            Text(text = stringResource(R.string.cancel))
                        }
                    },
                    onDismissRequest = { openDialog = false },
                )
            }
        },
    )
}

/*
@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}*/
