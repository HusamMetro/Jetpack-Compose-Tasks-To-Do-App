package com.tuwaiq.husam.taskstodoapp.ui.theme

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val Teal200 = Color(0xFF03DAC5)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

val LowPriorityColor = Color(0xFF00C980)
val MediumPriorityColor = Color(0xFFFFC114)
val HighPriorityColor = Color(0xFFFF4646)
val NonePriorityColor = MediumGray

val LightBlue1 = Color(0xFF78D5F5)
val LightBlue2 = Color(0xFF4ADEDE)
val MediumBlue = Color(0xFF1CA7EC)
val DarkBlue = Color(0xFF1F2F98)
val DarkerBlue = Color(0xFF0F1649)

val PeachBeige2 = Color(0xFFF6E3BA)
val MediumBeige = Color(0xFFDFCAA0)

val GreenPrimary = Color(0xFF1BA57B)
val GreenPrimaryVariant = Color(0xFF4d8d6e)
val GreenLight1 = Color(0xFFCDE3D8)

val GoldM = Color(0xFFD6AD60)
val GoldM2 = Color(0xA6D6AD60)
val GoldDark = Color(0xFF5F4B0C)


val Colors.gradientButtonColors: Brush
    @Composable
    get() = Brush.horizontalGradient(
        colors = listOf(primary, primaryVariant, primary),
    )

val Colors.signUpColor: Color
    get() = if (isLight) primary else primaryVariant

val Colors.cardFirstColor: Color
    get() = if (isLight) GreenLight1 else DarkerBlue

val Colors.cardSecondColor: Color
    get() = if (isLight) GreenLight1 else DarkBlue

val Colors.cardFirstColorGold: Color
    get() = if (isLight) MediumBeige else GoldM

val Colors.cardSecondColorGold: Color
    get() = if (isLight) PeachBeige2 else GoldM2

val Colors.cardColor: Brush
    @Composable
    get() = Brush.horizontalGradient(listOf(cardFirstColor, cardSecondColor))

val Colors.cardColorReversed: Brush
    @Composable
    get() = Brush.horizontalGradient(
        listOf(MaterialTheme.colors.cardFirstColor, MaterialTheme.colors.cardSecondColor).reversed()
    )

val Colors.cardColorGold: Brush
    @Composable
    get() = Brush.horizontalGradient(listOf(cardFirstColorGold, cardSecondColorGold))

val Colors.cardColorGoldReversed: Brush
    @Composable
    get() = Brush.horizontalGradient(listOf(cardFirstColorGold, cardSecondColorGold).reversed())

val Colors.splashScreenBackground: Color
    @Composable
    get() = MaterialTheme.colors.primary
//    get() = if (isLight) Purple700 else Color.Black

val Colors.taskItemTextColor: Color
    @Composable
    get() = if (isLight) DarkGray else Color.White

val Colors.taskItemBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.White else DarkGray

val Colors.fabBackgroundColor: Color
    @Composable
    get() = if (isLight) primary else primary


val Colors.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) Color.Black else LightGray

val Colors.textButtonBorderColor: Color
    @Composable
    get() = if (isLight) Color.DarkGray else GoldDark

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = MaterialTheme.colors.primary

val Colors.cardBorderColor: Color
    @Composable
    get() = if (isLight) Color.Transparent else Color.Transparent

val Colors.foregroundIndicatorColor: Color
    @Composable
    get() = if (isLight) Color.DarkGray else LightBlue2

val Colors.backgroundIndicatorColor: Color
    @Composable
    get() = if (isLight) Color.White else MediumGray

val Colors.backgroundIndicatorColor2: Color
    @Composable
    get() = if (isLight) GreenLight1 else MediumGray

val Colors.bottomBarSelectedContentColor: Color
    @Composable
    get() = topAppBarContentColor


val Colors.bottomBarUnselectedContentColor: Color
    @Composable
    get() = if (isLight) Color.White else MediumGray


val Colors.customOutlinedTextFieldColor: TextFieldColors
    @Composable
    get() = TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = if (isLight) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
        focusedBorderColor = if (isLight)
            MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high)
        else MaterialTheme.colors.primaryVariant.copy(alpha = ContentAlpha.high),
        focusedLabelColor =
        if (isLight)
            MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high)
        else MaterialTheme.colors.primaryVariant.copy(alpha = ContentAlpha.high),
    )

val Colors.checkBoxColor: CheckboxColors
    @Composable
    get() = if (isLight) CheckboxDefaults.colors(MaterialTheme.colors.primary) else CheckboxDefaults.colors(
        MaterialTheme.colors.primaryVariant
    )


val Colors.alertButtonColor: ButtonColors
    @Composable
    get() = ButtonDefaults.buttonColors(
        backgroundColor = if (isLight) MaterialTheme.colors.primary else MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.taskItemTextColor,
        disabledBackgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
            .compositeOver(MaterialTheme.colors.surface),
        disabledContentColor = MaterialTheme.colors.onSurface
            .copy(alpha = ContentAlpha.disabled),

        )

val Colors.alertOutlinedButtonColor: ButtonColors
    @Composable
    get() = ButtonDefaults.outlinedButtonColors(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = if (isLight) MaterialTheme.colors.primary else MaterialTheme.colors.taskItemTextColor,
        disabledContentColor = MaterialTheme.colors.onSurface
            .copy(alpha = ContentAlpha.disabled)
    )
