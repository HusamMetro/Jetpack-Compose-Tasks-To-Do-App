package com.tuwaiq.husam.taskstodoapp.ui.screens.register

import android.text.TextUtils
import android.util.Log
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
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.CommonPasswordTextField
import com.tuwaiq.husam.taskstodoapp.components.CommonTextField
import com.tuwaiq.husam.taskstodoapp.components.GradientButton
import com.tuwaiq.husam.taskstodoapp.data.models.User
import com.tuwaiq.husam.taskstodoapp.ui.theme.taskItemTextColor
import com.tuwaiq.husam.taskstodoapp.util.Constants.LIST_SCREEN
import com.tuwaiq.husam.taskstodoapp.util.Constants.REGISTER_SCREEN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

val userCollectionRef = Firebase.firestore.collection("users")

@Composable
fun RegisterContent(navController: NavHostController) {
    val focusManager: FocusManager = LocalFocusManager.current
    var nameValue by rememberSaveable { mutableStateOf("") }
    var emailValue by rememberSaveable { mutableStateOf("") }
    var phoneValue by rememberSaveable { mutableStateOf("") }
    var passwordValue by rememberSaveable { mutableStateOf("") }
    var confirmPasswordValue by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                modifier = Modifier.padding(top = 50.dp),
                painter = painterResource(id = R.drawable.ic_logo_dark),
                contentDescription = "TO DO ICON"
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
                        value = nameValue,
                        onValueChange = { nameValue = it },
                        strResId = R.string.name,
                        icon = Icons.Filled.Person,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )
                    CommonTextField(
                        value = emailValue,
                        onValueChange = { emailValue = it },
                        strResId = R.string.email_address,
                        icon = Icons.Filled.Email,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )
                    CommonTextField(
                        value = phoneValue,
                        onValueChange = { phoneValue = it },
                        strResId = R.string.phone_number,
                        icon = Icons.Filled.PhoneAndroid,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )
                    CommonPasswordTextField(
                        text = passwordValue,
                        onValueChange = { passwordValue = it },
                        strResId = R.string.password,
                        icon = Icons.Filled.Password,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )
                    CommonPasswordTextField(
                        text = confirmPasswordValue,
                        onValueChange = { confirmPasswordValue = it },
                        strResId = R.string.confirm_possword,
                        icon = Icons.Filled.Password,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    GradientButton(text = "Sign Up",
                        textColor = Color.White,
                        gradient = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colors.primary,
                                MaterialTheme.colors.secondary
                            )
                        ),
                        onClick = {
                            when {
                                TextUtils.isEmpty(emailValue.trim { it <= ' ' }) -> {
//                                    emailTextInputSignup.helperText = "*"
                                    Log.e("first","email")
                                }
                                TextUtils.isEmpty(passwordValue.trim { it <= ' ' }) -> {
//                                    passwordTextInputSignUp.helperText = "*"
                                    Log.e("second","password")
                                }
                                else -> {
                                    Log.e("else","Else")
                                    val userName: String = nameValue.trim { it <= ' ' }
                                    val email: String = emailValue.trim { it <= ' ' }
                                    val password: String = passwordValue.trim { it <= ' ' }
                                    val phoneNumber: String = phoneValue.trim { it <= ' ' }


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
                                                navController.navigate(LIST_SCREEN){
                                                    popUpTo(REGISTER_SCREEN) {
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
            Log.e( "FireStore","Successfully saved data")
        }
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {
            Log.e("FireStore", "${e.message}")
        }
    }
}

@Preview
@Composable
private fun RegisterContentPreview() {
    RegisterContent(rememberNavController())
}