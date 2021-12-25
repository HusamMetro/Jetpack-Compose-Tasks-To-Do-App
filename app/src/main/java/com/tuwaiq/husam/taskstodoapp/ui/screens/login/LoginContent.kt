package com.tuwaiq.husam.taskstodoapp.ui.screens.login

import android.text.TextUtils
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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
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
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.CommonPasswordTextField
import com.tuwaiq.husam.taskstodoapp.components.CommonTextField
import com.tuwaiq.husam.taskstodoapp.components.GradientButton
import com.tuwaiq.husam.taskstodoapp.ui.theme.cardSecondColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.gradientButtonColors
import com.tuwaiq.husam.taskstodoapp.ui.theme.signUpColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.taskItemTextColor
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants.LIST_SCREEN
import com.tuwaiq.husam.taskstodoapp.util.Constants.LOGIN_SCREEN
import com.tuwaiq.husam.taskstodoapp.util.Constants.REGISTER_SCREEN


@Composable
fun LoginContent(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val focusManager: FocusManager = LocalFocusManager.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
//    val checkedRememberState by sharedViewModel.rememberState.collectAsState()
    var checked by rememberSaveable { mutableStateOf(false) }

    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 80f,
        targetValue = 150f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                modifier = Modifier.offset( y = translateAnimation.value.dp),
                painter = painterResource(id = R.drawable.ic_logo_dark),
                contentDescription = stringResource(id = R.string.application_logo)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(
                    MaterialTheme.colors.surface
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sign In",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),
                    color = MaterialTheme.colors.taskItemTextColor,
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CommonTextField(
                        value = email,
                        onValueChange = { newText -> email = newText },
                        strResId = R.string.email_address,
                        singleLine = true,
                        icon = Icons.Filled.Email,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )

                    CommonPasswordTextField(
                        text = password,
                        onValueChange = { newText -> password = newText },
                        strResId = R.string.password,
                        icon = Icons.Filled.Password,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { checked = it },
                            colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
                        )
                        Text(modifier = Modifier.padding(horizontal = 10.dp), text = "Remember Me")
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    GradientButton(
                        text = "Log In",
                        textColor = Color.White,
                        gradient = MaterialTheme.colors.gradientButtonColors,
                        onClick = {
                            when {
                                TextUtils.isEmpty(email.trim { it <= ' ' }) -> {
//                                    emailTextInputSignup.helperText = "*"
                                    Log.e("first", "email")
                                }
                                TextUtils.isEmpty(password.trim { it <= ' ' }) -> {
//                                    passwordTextInputSignUp.helperText = "*"
                                    Log.e("second", "password")
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
                                                navController.navigate(LIST_SCREEN) {
                                                    popUpTo(LOGIN_SCREEN) {
                                                        inclusive = true
                                                    }
                                                }
                                            } else {
                                                // if the registration is not successful then show error massage
                                                it.value = false
                                                Log.e("register", "${task.exception?.message}")
                                            }
                                        }
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = {
                            navController.navigate(REGISTER_SCREEN)
                        }) {
                            Text(text = "Sign Up", color = MaterialTheme.colors.signUpColor)
                        }
                        TextButton(onClick = { }) {
                            Text(text = "Forgot Password?", color = Color.Gray)
                        }
                    }
                }
            }

        }
    }
}

/*
@Composable
@Preview
private fun LoginContentPreview() {
    LoginContent(rememberNavController(), sharedViewModel)
}

*/
