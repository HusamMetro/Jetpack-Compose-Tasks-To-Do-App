package com.tuwaiq.husam.taskstodoapp.ui.screens.register

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.CommonPasswordTextField
import com.tuwaiq.husam.taskstodoapp.components.CommonTextField
import com.tuwaiq.husam.taskstodoapp.components.GradientButton
import com.tuwaiq.husam.taskstodoapp.data.models.User
import com.tuwaiq.husam.taskstodoapp.ui.theme.gradientButtonColors
import com.tuwaiq.husam.taskstodoapp.ui.theme.taskItemTextColor
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants.LIST_SCREEN
import com.tuwaiq.husam.taskstodoapp.util.Constants.LOGIN_SCREEN
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validNumber
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

val userCollectionRef = Firebase.firestore.collection("users")

@Composable
fun RegisterContent(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val focusManager: FocusManager = LocalFocusManager.current

    var name by rememberSaveable { mutableStateOf("") }
    var nameIsError by rememberSaveable { mutableStateOf(false) }
    var nameIsErrorMsg by rememberSaveable { mutableStateOf("") }

    var email by rememberSaveable { mutableStateOf("") }
    var emailIsError by rememberSaveable { mutableStateOf(false) }
    var emailIsErrorMsg by rememberSaveable { mutableStateOf("") }

    var phone by rememberSaveable { mutableStateOf("") }
    var phoneIsError by rememberSaveable { mutableStateOf(false) }
    var phoneIsErrorMsg by rememberSaveable { mutableStateOf("") }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordIsError by rememberSaveable { mutableStateOf(false) }
    var passwordIsErrorMsg by rememberSaveable { mutableStateOf("") }

//    var confirmPasswordValue by rememberSaveable { mutableStateOf("") }

    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 30f,
        targetValue = 80f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                modifier = Modifier.offset(y = translateAnimation.value.dp),
                painter = painterResource(id = R.drawable.ic_logo_dark),
                contentDescription = stringResource(id = R.string.application_logo)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(MaterialTheme.colors.surface)
                .padding(top = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Sign Up",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),
                    color = MaterialTheme.colors.taskItemTextColor,
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CommonTextField(
                        value = name,
                        onValueChange = { newText ->
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
                        strResId = R.string.name,
                        icon = Icons.Filled.Person,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                        isError = nameIsError,
                        errorMsg = nameIsErrorMsg
                    )
                    CommonTextField(
                        value = email,
                        onValueChange = { newText ->
                            emailIsError = !newText.validEmail { emailIsErrorMsg = it }
                            email = newText
                        },
                        strResId = R.string.email_address,
                        icon = Icons.Filled.Email,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                        isError = emailIsError,
                        errorMsg = emailIsErrorMsg
                    )
                    CommonTextField(
                        value = phone,
                        onValueChange = { newText ->
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
                        strResId = R.string.phone_number,
                        icon = Icons.Filled.Phone,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                        isError = phoneIsError,
                        errorMsg = phoneIsErrorMsg
                    )
                    CommonPasswordTextField(
                        text = password,
                        onValueChange = { newText ->
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
                        strResId = R.string.password,
                        icon = Icons.Filled.Password,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                        isError = passwordIsError,
                        errorMsg = passwordIsErrorMsg
                    )
                    /* CommonPasswordTextField(
                         text = confirmPasswordValue,
                         onValueChange = { confirmPasswordValue = it },
                         strResId = R.string.confirm_possword,
                         icon = Icons.Filled.Password,
                         keyboardOptions = KeyboardOptions(
                             keyboardType = KeyboardType.Password,
                             imeAction = ImeAction.Done
                         ),
                         keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                     )*/
                    Spacer(modifier = Modifier.padding(20.dp))
                    GradientButton(text = "Sign Up",
                        textColor = Color.White,
                        gradient = MaterialTheme.colors.gradientButtonColors,
                        onClick = { mutableBoolean ->
                            when {
                                nameIsError || emailIsError || passwordIsError || phoneIsError -> {
                                    mutableBoolean.value = false
                                }
                                else -> {
                                    Log.e("else", "Else")
                                    val userName: String = name.trim { it <= ' ' }
                                    val email: String = email.trim { it <= ' ' }
                                    val password: String = password.trim { it <= ' ' }
                                    val phoneNumber: String = phone.trim { it <= ' ' }


                                    // create an instance and create a register with email and password
                                    FirebaseAuth.getInstance()
                                        .createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener { task ->

                                            // if the registration is sucessfully done
                                            if (task.isSuccessful) {
                                                //firebase register user
                                                val firebaseUser: FirebaseUser =
                                                    task.result!!.user!!
                                                val user = User(userName, email, phoneNumber)
                                                saveUser(user)
                                                sharedViewModel.loadUserInformation()
                                                navController.navigate(LIST_SCREEN) {
                                                    popUpTo(LOGIN_SCREEN) {
                                                        inclusive = true
                                                    }
                                                }
                                            } else {
                                                // if the registration is not successful then show error massage
                                                Log.e("register", "${task.exception?.message}")
                                            }
                                        }
                                }
                            }
                        }
                    )
//                    Spacer(modifier = Modifier.padding(20.dp))
                    /*Text(
                        text = "Login Instead",
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate("login_page"){
                                popUpTo = navController.graph.startDestination
                                launchSingleTop = true
                            }
                        })
                    )
                    Spacer(modifier = Modifier.padding(20.dp))*/
                }
            }
        }
    }

}

fun saveUser(user: User) = CoroutineScope(Dispatchers.IO).launch {
    val userUid = FirebaseAuth.getInstance().currentUser!!.uid
    try {
        userCollectionRef.document(userUid).set(user).await()
        withContext(Dispatchers.Main) {
            Log.e("FireStore", "Successfully saved data")
        }
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {
            Log.e("FireStore", "${e.message}")
        }
    }
}

/*
@Preview
@Composable
private fun RegisterContentPreview() {
    RegisterContent(rememberNavController())
}*/
