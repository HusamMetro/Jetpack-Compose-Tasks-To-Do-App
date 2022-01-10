package com.tuwaiq.husam.taskstodoapp.ui.screens.settings

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.CommonTextField
import com.tuwaiq.husam.taskstodoapp.components.GradientButton
import com.tuwaiq.husam.taskstodoapp.data.models.Languages
import com.tuwaiq.husam.taskstodoapp.data.models.UiMode
import com.tuwaiq.husam.taskstodoapp.ui.theme.*

@Composable
fun SettingsContent(
    onLaunchedEffect: () -> Unit,
    email: String,
    emailOnValueChange: (String) -> Unit,
    phone: String,
    phoneOnValueChange: (String) -> Unit,
    phoneIsError: Boolean,
    phoneIsErrorMsg: String,
    name: String,
    nameOnValueChange: (String) -> Unit,
    nameIsError: Boolean,
    nameIsErrorMsg: String,
    updateOnClicked: (MutableState<Boolean>) -> Unit,
    signOutOnClicked: () -> Unit,
    darkMode: Boolean,
    darkModeOnChange: (Boolean) -> Unit,
    language: String,
    languageOnChange: (String) -> Unit,
) {

    val focusManager: FocusManager = LocalFocusManager.current

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

    LaunchedEffect(key1 = true) {
        onLaunchedEffect()
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
                    value = name,
                    onValueChange = { nameOnValueChange(it) },
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
                    modifier = Modifier.fillMaxWidth(SETTINGS_MAX_WIDTH_FRACTION),
                    value = email,
                    onValueChange = { emailOnValueChange(it) },
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
                    value = phone,
                    onValueChange = { phoneOnValueChange(it) },
                    strResId = R.string.phone_number,
                    icon = Icons.Filled.Phone,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    isError = phoneIsError,
                    errorMsg = phoneIsErrorMsg
                )
                Spacer(modifier = Modifier.padding(SMALL_PADDING))

                // -------------------------- Transfer to Composable fun later -----------------------------
                val cornerRadius = SMALL_PADDING
                Row(
                    modifier = Modifier
                        .fillMaxWidth(SETTINGS_MAX_WIDTH_FRACTION),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    val items = UiMode.values()
                    var selectedIndex by remember { mutableStateOf(0) }
                    selectedIndex = if (darkMode) {
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
                                darkModeOnChange(darkThemeState)
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
                        }
                    }
                    Spacer(modifier = Modifier.weight(5f))

                    Spacer(modifier = Modifier.weight(5f))
                    val langList = Languages.values()
                    var selectedLangIndex by remember { mutableStateOf(0) }
                    selectedLangIndex = if (language == "en") {
                        0
                    } else {
                        1
                    }
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
                            onClick = {
                                selectedLangIndex = index
                                val langState = when (selectedLangIndex) {
                                    0 -> Languages.English.lang
                                    else -> Languages.Arabic.lang
                                }
                                languageOnChange(langState)
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
                                    text = item.displayName,
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

                Spacer(modifier = Modifier.padding(MEDIUM_PADDING))
                Row(
                    modifier = Modifier.fillMaxWidth(SETTINGS_MAX_WIDTH_FRACTION),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    GradientButton(text = stringResource(R.string.update_button),
                        textColor = Color.White,
                        gradient = MaterialTheme.colors.gradientButtonColors,
                        onClick = { updateOnClicked(it) }
                    )
                }
                Spacer(modifier = Modifier.padding(MEDIUM_PADDING))
                OutlinedButton(
                    onClick = signOutOnClicked
                ) {
                    Text(
                        text = stringResource(R.string.sign_out_button),
                        color = MaterialTheme.colors.signUpColor
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenTestingCardsPreview() {
    SettingsContent(
        onLaunchedEffect = {},
        email = "husam@test.com",
        emailOnValueChange = {},
        phone = "0555555555",
        phoneOnValueChange = {},
        phoneIsError = false,
        phoneIsErrorMsg = "",
        name = "Husam",
        nameOnValueChange = {},
        nameIsError = false,
        nameIsErrorMsg = "",
        updateOnClicked = {},
        signOutOnClicked = {},
        darkMode = false,
        darkModeOnChange = {},
        language = "en",
        languageOnChange = {}
    )
}
