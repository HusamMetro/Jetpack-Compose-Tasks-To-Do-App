package com.tuwaiq.husam.taskstodoapp.ui.screens.register

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.data.models.User
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.coroutines.launch

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
                        .noSpecialCharacters()
                        .addErrorCallback {
                            nameIsErrorMsg = it
                        }.check()
                    name = newText
                },
                nameIsError = nameIsError,
                nameIsErrorMsg = nameIsErrorMsg,
                email = email,
                emailOnValueChange = { newText ->
                    emailIsError = !newText.validEmail { emailIsErrorMsg = it }
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
                            phoneIsErrorMsg = it
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
                            passwordIsErrorMsg = it
                        }.check()
                    password = newText
                },
                passwordIsError = passwordIsError,
                passwordIsErrorMsg = passwordIsErrorMsg,
                signUpOnClicked = { mutableBoolean ->
                    when {
                        nameIsError || emailIsError || passwordIsError || phoneIsError -> {
                            mutableBoolean.value = false
                        }
                        else -> {
                            Log.e("else", "Else")
                            val userName: String = name.trim { it <= ' ' }
                            val emailTrimmed: String = email.trim { it <= ' ' }
                            val phoneNumber: String = phone.trim { it <= ' ' }

                            // create an instance and create a register with email and password
                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(emailTrimmed, password)
                                .addOnCompleteListener { task ->

                                    // if the registration is successfully done
                                    if (task.isSuccessful) {
                                        //firebase register user
                                        val firebaseUser: FirebaseUser =
                                            task.result!!.user!!
                                        val user = User(userName, emailTrimmed, phoneNumber)
                                        saveUser(user)
                                        sharedViewModel.loadUserInformation()
                                        navController.navigate(Constants.LIST_SCREEN) {
                                            popUpTo(Constants.LOGIN_SCREEN) {
                                                inclusive = true
                                            }
                                        }
                                    } else {
                                        // if the registration is not successful then show error massage
                                        mutableBoolean.value = false
                                        firstTime = true
                                        snackBoolean = !snackBoolean
                                        Log.e("register", "${task.exception?.message}")
                                    }
                                }
                        }
                    }
                }
            )
        }
    )

}
/*
@Preview
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(rememberNavController())
}*/
