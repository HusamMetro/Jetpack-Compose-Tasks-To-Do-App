package com.tuwaiq.husam.taskstodoapp.ui.screens.login

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.components.CommonPasswordTextField
import com.tuwaiq.husam.taskstodoapp.components.CommonTextField
import com.tuwaiq.husam.taskstodoapp.components.GradientButton
import com.tuwaiq.husam.taskstodoapp.ui.theme.checkBoxColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.gradientButtonColors
import com.tuwaiq.husam.taskstodoapp.ui.theme.signUpColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.taskItemTextColor


@Composable
fun LoginContent(
    registerOnClicked: () -> Unit,
    onForgotPassword: () -> Unit,
    email: String,
    emailOnValueChange: (String) -> Unit,
    emailIsError: Boolean,
    emailIsErrorMsg: String,
    password: String,
    passwordOnValueChange: (String) -> Unit,
    passwordIsError: Boolean,
    passwordIsErrorMsg: String,
    signInOnClicked: (MutableState<Boolean>) -> Unit,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit

) {
    val focusManager: FocusManager = LocalFocusManager.current

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
                modifier = Modifier.offset(y = translateAnimation.value.dp),
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
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = stringResource(R.string.sign_in),
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
                        value = email,
                        onValueChange = { emailOnValueChange(it) },
                        strResId = R.string.email_address,
                        singleLine = true,
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

                    CommonPasswordTextField(
                        text = password,
                        onValueChange = { passwordOnValueChange(it) },
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
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { onCheckedChange(it) },
                            colors = MaterialTheme.colors.checkBoxColor
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            text = stringResource(
                                R.string.remember_me
                            )
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    GradientButton(
                        text = stringResource(R.string.log_in_button),
                        textColor = Color.White,
                        gradient = MaterialTheme.colors.gradientButtonColors,
                        onClick = { signInOnClicked(it) }
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = registerOnClicked) {
                            Text(
                                text = stringResource(R.string.sign_up),
                                color = MaterialTheme.colors.signUpColor
                            )
                        }
                        TextButton(onClick = onForgotPassword) {
                            Text(
                                text = stringResource(R.string.forgot_password),
                                color = Color.Gray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

        }
    }
}

@Composable
@Preview
private fun LoginContentPreview() {
    LoginContent(
        {},
        {},
        "Husam@email.com",
        {},
        false,
        "No Error",
        "Confi",
        {},
        false,
        "No Error",
        {},
        false,
        {}
    )
}

