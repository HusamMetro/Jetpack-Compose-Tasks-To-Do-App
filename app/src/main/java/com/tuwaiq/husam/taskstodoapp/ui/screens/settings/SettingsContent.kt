package com.tuwaiq.husam.taskstodoapp.ui.screens.settings

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.CommonTextField
import com.tuwaiq.husam.taskstodoapp.components.GradientButton
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants.LIST_SCREEN
import com.tuwaiq.husam.taskstodoapp.util.Constants.LOGIN_SCREEN

@Composable
fun SettingsContent(navController: NavHostController, sharedViewModel: SharedViewModel) {

    val focusManager: FocusManager = LocalFocusManager.current
    var nameValue by rememberSaveable { mutableStateOf("") }
    var emailValue by rememberSaveable { mutableStateOf("") }
    var phoneValue by rememberSaveable { mutableStateOf("") }
    var switchT by remember { mutableStateOf(sharedViewModel.darkThemeState.value) }
    /* var passwordValue by rememberSaveable { mutableStateOf("") }
     var confirmPasswordValue by rememberSaveable { mutableStateOf("") }
 */
    // Retrieve UserDate
    val userCollectionRef = Firebase.firestore.collection("users")
    val db = FirebaseFirestore.getInstance()
    val userUID = FirebaseAuth.getInstance().currentUser?.uid
    val docRef = db.collection("users").document("$userUID")

    LaunchedEffect(key1 = true) {
        nameValue = sharedViewModel.user.username
        emailValue = sharedViewModel.user.email
        phoneValue = sharedViewModel.user.phoneNumber
    }

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
                modifier = Modifier.fillMaxHeight(0.3f),
                painter = painterResource(id = R.drawable.ic_logo_dark),
                contentDescription = ""
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(
                    MaterialTheme.colors.surface
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    Text(text = "Dark Mode")
                    Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                    Switch(
                        checked = switchT,
                        onCheckedChange = {
                            switchT = it
                            sharedViewModel.persistDarkThemeState(it)
//                            sharedViewModel.readDarkThemeState()
                        },
                    )
                }
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
                Spacer(modifier = Modifier.padding(20.dp))
                GradientButton(text = "Update",
                    textColor = Color.White,
                    gradient = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colors.primary,
                            MaterialTheme.colors.secondary
                        )
                    ),
                    onClick = {
                        updateInFirestore(nameValue, phoneValue, it)
                    }
                )
                Spacer(modifier = Modifier.padding(10.dp))
                GradientButton(text = "Sign Out",
                    textColor = Color.White,
                    gradient = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colors.primary,
                            MaterialTheme.colors.secondary
                        )
                    ),
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                        sharedViewModel.persistRememberState(false)
                        navController.navigate(LOGIN_SCREEN) {
                            /*popUpTo(SETTINGS_SCREEN){
                                inclusive = true
                            }*/
                            popUpTo(LIST_SCREEN) {
                                inclusive = true
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.padding(20.dp))

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

private fun updateInFirestore(
    newUsername: String,
    newPhoneNumber: String,
    mutableState: MutableState<Boolean>
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
                mutableState.value = false
            } else {
                Log.e("update", "Failed")
                mutableState.value = false
            }
        }
}

/*
@Composable
@Preview
private fun SettingsContentPreview() {
    SettingsContent(rememberNavController())
}*/
