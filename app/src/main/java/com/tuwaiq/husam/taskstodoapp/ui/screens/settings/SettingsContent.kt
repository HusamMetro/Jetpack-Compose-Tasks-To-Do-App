package com.tuwaiq.husam.taskstodoapp.ui.screens.settings

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.CommonTextField
import com.tuwaiq.husam.taskstodoapp.components.GradientButton
import com.tuwaiq.husam.taskstodoapp.data.models.Languages
import com.tuwaiq.husam.taskstodoapp.data.models.UiMode
import com.tuwaiq.husam.taskstodoapp.ui.theme.*
import com.tuwaiq.husam.taskstodoapp.ui.viewmodels.SharedViewModel
import com.tuwaiq.husam.taskstodoapp.util.Constants.LIST_SCREEN
import com.tuwaiq.husam.taskstodoapp.util.Constants.LOGIN_SCREEN

@Composable
fun SettingsContent(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
) {

    val focusManager: FocusManager = LocalFocusManager.current
    var nameValue by remember { mutableStateOf("") }
    var emailValue by remember { mutableStateOf("") }
    var phoneValue by remember { mutableStateOf("") }

    var switchT by remember { mutableStateOf(sharedViewModel.darkThemeState.value) }
    var langaguge by remember { mutableStateOf(Languages.English) }

    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 20f,
        targetValue = 60f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    /* var passwordValue by rememberSaveable { mutableStateOf("") }
     var confirmPasswordValue by rememberSaveable { mutableStateOf("") }
 */
    // Retrieve UserDate
    /*val userCollectionRef = Firebase.firestore.collection("users")
    val db = FirebaseFirestore.getInstance()
    val userUID = FirebaseAuth.getInstance().currentUser?.uid
    val docRef = db.collection("users").document("$userUID")*/

    LaunchedEffect(key1 = true) {
        nameValue = sharedViewModel.user.username
        emailValue = sharedViewModel.user.email
        phoneValue = sharedViewModel.user.phoneNumber
    }


    /*ScreenTestingCards(
        name = nameValue,
        email = emailValue,
        phone = phoneValue,
        switchT = switchT,
        onCheckedChange = {
            switchT = it
            sharedViewModel.persistDarkThemeState(it)
//                          sharedViewModel.readDarkThemeState()
        },
        language = langaguge,
        onLanguageSelected = {
            langaguge = it
        }
    )*/

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
                modifier = Modifier
                    .fillMaxHeight(0.2f)
                    .offset(y = translateAnimation.value.dp),
                painter = painterResource(id = R.drawable.ic_logo_dark),
                contentDescription = stringResource(id = R.string.application_logo)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(
                    MaterialTheme.colors.surface
                )
                .verticalScroll(rememberScrollState())
                .padding(bottom = TOP_APP_BAR_HEIGHT.plus(MEDIUM_PADDING)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
            ) {
                CommonTextField(
                    modifier = Modifier.fillMaxWidth(SETTINGS_MAX_WIDTH_FRACTION),
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
                    modifier = Modifier.fillMaxWidth(SETTINGS_MAX_WIDTH_FRACTION),
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
                    }),
                    enabled = false
                )
                CommonTextField(
                    modifier = Modifier.fillMaxWidth(SETTINGS_MAX_WIDTH_FRACTION),
                    value = phoneValue,
                    onValueChange = { phoneValue = it },
                    strResId = R.string.phone_number,
                    icon = Icons.Filled.Phone,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                )
                Spacer(modifier = Modifier.padding(SMALL_PADDING))
                /*Row(
                    modifier = Modifier.fillMaxWidth(SETTINGS_MAX_WIDTH_FRACTION),
                    horizontalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Filled.DarkMode, contentDescription = "Dark Mode")
                    Text(text = "Dark Mode")
                    Switch(
                        checked = switchT,
                        onCheckedChange = {
                            switchT = it
                            sharedViewModel.persistDarkThemeState(it)
//                            sharedViewModel.readDarkThemeState()
                        },
                    )
                }*/

                // -------------------------- Transfer to Composable fun later -----------------------------
                val cornerRadius = SMALL_PADDING
                Row(
                    modifier = Modifier
                        .fillMaxWidth(SETTINGS_MAX_WIDTH_FRACTION),
//                        .padding(8.dp)
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    val items = UiMode.values()
                    var selectedIndex by remember { mutableStateOf(0) }
                    selectedIndex = if (switchT) {
                        1
                    } else {
                        0
                    }
                    items.forEachIndexed { index, item ->
                        OutlinedButton(
                            modifier = when (index) {
                                0 -> {
                                    if (selectedIndex == index) {
                                        Modifier
                                            .offset(0.dp, 0.dp)
                                            .zIndex(1f)
                                    } else {
                                        Modifier
                                            .offset(0.dp, 0.dp)
                                            .zIndex(0f)
                                    }
                                }
                                else -> {
                                    val offset = -1 * index
                                    if (selectedIndex == index) {
                                        Modifier
                                            .offset(offset.dp, 0.dp)
                                            .zIndex(1f)
                                    } else {
                                        Modifier
                                            .offset(offset.dp, 0.dp)
                                            .zIndex(0f)
                                    }
                                }
                            },
                            onClick = {
                                selectedIndex = index
                                val darkThemeState = when (selectedIndex) {
                                    0 -> false
                                    else -> true
                                }
                                switchT = darkThemeState
                                sharedViewModel.persistDarkThemeState(darkThemeState)
                            },
                            shape = when (index) {
                                // left outer button
                                0 -> RoundedCornerShape(
                                    topStart = cornerRadius,
                                    topEnd = 0.dp,
                                    bottomStart = cornerRadius,
                                    bottomEnd = 0.dp
                                )
                                // right outer button
                                items.size - 1 -> RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = cornerRadius,
                                    bottomStart = 0.dp,
                                    bottomEnd = cornerRadius
                                )
                                // middle button
                                else -> RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 0.dp
                                )
                            },
                            border = BorderStroke(
                                1.dp, if (selectedIndex == index) {
                                    MaterialTheme.colors.signUpColor
                                } else {
                                    Color.DarkGray.copy(alpha = 0.75f)
                                }
                            ),
                            colors = if (selectedIndex == index) {
                                // selected colors
                                ButtonDefaults.outlinedButtonColors(
                                    backgroundColor = MaterialTheme.colors.signUpColor.copy(
                                        alpha = 0.1f
                                    ), contentColor = MaterialTheme.colors.primary
                                )
                            } else {
                                // not selected colors
                                ButtonDefaults.outlinedButtonColors(
                                    backgroundColor = MaterialTheme.colors.surface,
                                    contentColor = MaterialTheme.colors.primary
                                )
                            },
                        ) {
                            Row(
                                modifier = Modifier.height(30.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Icon(
                                    modifier = Modifier.padding(horizontal = 2.dp),
                                    imageVector = item.imageVector,
                                    contentDescription = "${item.name} Icon",
                                    tint = if (selectedIndex == index) {
                                        MaterialTheme.colors.signUpColor
                                    } else {
                                        Color.DarkGray.copy(alpha = 0.9f)
                                    }
                                )
                            }

                            /*Text(
                                text = item.name,
                                color = if (selectedIndex == index) {
                                    MaterialTheme.colors.signUpColor
                                } else {
                                    Color.DarkGray.copy(alpha = 0.9f)
                                },
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )*/
                        }
                    }
                    Spacer(modifier = Modifier.weight(5f))

                    Spacer(modifier = Modifier.weight(5f))
                    val langList = Languages.values()
                    var selectedLangIndex by remember { mutableStateOf(0) }
                    langList.forEachIndexed { index, item ->
                        OutlinedButton(
                            modifier = when (index) {
                                0 -> {
                                    if (selectedLangIndex == index) {
                                        Modifier
                                            .offset(0.dp, 0.dp)
                                            .zIndex(1f)
                                    } else {
                                        Modifier
                                            .offset(0.dp, 0.dp)
                                            .zIndex(0f)
                                    }
                                }
                                else -> {
                                    val offset = -1 * index
                                    if (selectedLangIndex == index) {
                                        Modifier
                                            .offset(offset.dp, 0.dp)
                                            .zIndex(1f)
                                    } else {
                                        Modifier
                                            .offset(offset.dp, 0.dp)
                                            .zIndex(0f)
                                    }
                                }
                            },
                            onClick = { selectedLangIndex = index },
                            shape = when (index) {
                                // left outer button
                                0 -> RoundedCornerShape(
                                    topStart = cornerRadius,
                                    topEnd = 0.dp,
                                    bottomStart = cornerRadius,
                                    bottomEnd = 0.dp
                                )
                                // right outer button
                                langList.size - 1 -> RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = cornerRadius,
                                    bottomStart = 0.dp,
                                    bottomEnd = cornerRadius
                                )
                                // middle button
                                else -> RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 0.dp
                                )
                            },
                            border = BorderStroke(
                                1.dp, if (selectedLangIndex == index) {
                                    MaterialTheme.colors.signUpColor
                                } else {
                                    Color.DarkGray.copy(alpha = 0.75f)
                                }
                            ),
                            colors = if (selectedLangIndex == index) {
                                // selected colors
                                ButtonDefaults.outlinedButtonColors(
                                    backgroundColor = MaterialTheme.colors.signUpColor.copy(
                                        alpha = 0.1f
                                    ), contentColor = MaterialTheme.colors.primary
                                )
                            } else {
                                // not selected colors
                                ButtonDefaults.outlinedButtonColors(
                                    backgroundColor = MaterialTheme.colors.surface,
                                    contentColor = MaterialTheme.colors.primary
                                )
                            },
                        ) {
                            Row(
                                modifier = Modifier.height(30.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = item.name,
                                    color = if (selectedLangIndex == index) {
                                        MaterialTheme.colors.signUpColor
                                    } else {
                                        Color.DarkGray.copy(alpha = 0.9f)
                                    },
                                    modifier = Modifier.padding(horizontal = 2.dp, vertical = 1.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
                // -------------------------- Transfer to Composable fun later -----------------------------

                // -------------------------- Transfer to Composable fun later -----------------------------
                /* Row(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(8.dp)
                 ) {

                 }*/
                // -------------------------- Transfer to Composable fun later -----------------------------
                /*Spacer(modifier = Modifier.padding(SMALL_PADDING))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(SETTINGS_MAX_WIDTH_FRACTION)
                ) {
                    LanguageDropDown(
                        language = langaguge,
                        onLanguageSelected = { languages ->
                            langaguge = languages
                        }
                    )
                }*/
                Spacer(modifier = Modifier.padding(MEDIUM_PADDING))
                Row(
                    modifier = Modifier.fillMaxWidth(SETTINGS_MAX_WIDTH_FRACTION),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    /*GradientButton(
                        text = "Sign Out",
                        textColor = Color.White,
                        gradient = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colors.primary,
                                MaterialTheme.colors.primaryVariant
                            )
                        ),

                    )*/
                    GradientButton(text = "Update",
                        textColor = Color.White,
                        gradient = MaterialTheme.colors.gradientButtonColors,
                        onClick = {
                            updateInFirestore(nameValue, phoneValue, it)
                            sharedViewModel.loadUserInformation()
                        }
                    )
                }
                Spacer(modifier = Modifier.padding(MEDIUM_PADDING))
                OutlinedButton(
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                        sharedViewModel.persistRememberState(false)
                        navController.navigate(LOGIN_SCREEN) {
                            /*popUpTo(SETTINGS_SCREEN){
                            inclusive = true
                        } */
                            popUpTo(LIST_SCREEN) {
                                inclusive = true
                            }
                        }
                    }) {
                    Text(text = "Sign Out", color = MaterialTheme.colors.signUpColor)
                }

//                Spacer(modifier = Modifier.padding(20.dp))

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
                Spacer(modifier = Modifier.padding(20.dp))*//*
            }


        }
    }*/
            }
        }
    }
}

/*

@Composable
fun ScreenTestingCards(
    name: String,
    email: String,
    phone: String,
    switchT: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    language: Languages,
    onLanguageSelected: (Languages) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
//                top = MEDIUM_PADDING,
                bottom = TOP_APP_BAR_HEIGHT,
//                start = MEDIUM_PADDING,
//                end = MEDIUM_PADDING
            )
            .background(MaterialTheme.colors.background.copy(0.5f)),
        verticalArrangement = Arrangement.SpaceBetween,

        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .background(MaterialTheme.colors.primary)
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_logo_dark),
                contentDescription = stringResource(id = R.string.application_logo)
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LARGE_PADDING)
                .clip(RoundedCornerShape(MEDIUM_PADDING)),
            elevation = 5.dp,
//                backgroundColor = MaterialTheme.colors.cardSecondColor
//                backgroundColor = MaterialTheme.colors.primary
        ) {
            Column(modifier = Modifier.padding(LARGE_PADDING)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MEDIUM_PADDING),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = stringResource(id = R.string.name),
                    )
                    Text(
                        text = name,
                    )
                }
                Spacer(modifier = Modifier.padding(vertical = MEDIUM_PADDING))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MEDIUM_PADDING),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = stringResource(id = R.string.email_address),
                    )
                    Text(
                        text = email,
                    )
                }
                Spacer(modifier = Modifier.padding(vertical = MEDIUM_PADDING))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MEDIUM_PADDING),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = stringResource(id = R.string.name),
                    )
                    Text(
                        text = phone,
                    )
                }
            }
        }


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LARGE_PADDING)
                .clip(RoundedCornerShape(LARGE_PADDING)),
            elevation = 5.dp,
            backgroundColor = MaterialTheme.colors.cardSecondColor
        ) {
            Column(
                Modifier.padding(LARGE_PADDING),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.DarkMode,
                        contentDescription = "",
                        tint = MaterialTheme.colors.taskItemTextColor
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                    Text(
                        text = "Dark Mode",
                        color = MaterialTheme.colors.taskItemTextColor
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                    Switch(
                        checked = switchT,
                        onCheckedChange = onCheckedChange,
                        colors = SwitchDefaults.colors()
                    )
                }
                Spacer(
                    modifier = Modifier.height(MEDIUM_PADDING),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(
                        horizontal = LARGEST_PADDING,
                        vertical = MEDIUM_PADDING
                    )
                ) {
                    LanguageDropDown(
                        language = language,
                        onLanguageSelected = onLanguageSelected
                    )
                }

            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LARGE_PADDING)
                .clip(RoundedCornerShape(LARGE_PADDING)),
            elevation = 5.dp,
            backgroundColor = MaterialTheme.colors.cardSecondColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LARGE_PADDING),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Email")
                Text(text = "User Name")
                Text(text = "Phone")
            }
        }
    }
}

*/

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

@Composable
fun LanguageDropDown(
    language: Languages,
    onLanguageSelected: (Languages) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )
    var parentSize by remember { mutableStateOf(IntSize.Zero) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                parentSize = it.size
            }
            .height(TOP_APP_BAR_HEIGHT)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.background)
            .clickable { expanded = true }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.padding(horizontal = MEDIUM_PADDING))
        Text(
            modifier = Modifier.weight(8f),
            text = language.name,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.taskItemTextColor
        )
        IconButton(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .rotate(degrees = angle)
                .weight(1.5f),
            onClick = { expanded = true }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(R.string.drop_down_arrow_icon),
                tint = MaterialTheme.colors.taskItemTextColor
            )
        }
        DropdownMenu(
            modifier = Modifier.width(with(LocalDensity.current) {
                parentSize.width.toDp()
            }),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Languages.values().forEach { language ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onLanguageSelected(language)
                }) {
                    LanguageItem(language = language)
                }
            }
        }
    }
}


@Composable
fun LanguageItem(language: Languages) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = LARGE_PADDING),
            text = language.name,
            style = Typography.subtitle2,
            color = MaterialTheme.colors.onSurface
        )
    }
}

/*@Composable
@Preview
private fun PriorityDropDownPreview() {
    LanguageDropDown(
        language = Languages.English,
        onLanguageSelected = {}
    )
}*/

/*@Composable
@Preview
private fun LanguageItemPreview() {
    LanguageItem(language = Languages.English)
}*/
/*
        @Preview(showBackground = true)
        @Composable
        fun ScreenTestingCardsPreview() {
            ScreenTestingCards(
                "Husam Metro",
                "husam5009@gmail.com",
                "0598554858",
                true,
                onLanguageSelected = {},
                onCheckedChange = {},
                language = Languages.English
            )
        }
        */
