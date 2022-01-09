package com.tuwaiq.husam.taskstodoapp.ui.theme

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

val LowPriorityColor = Color(0xFF00C980)
val MediumPriorityColor = Color(0xFFFFC114)
val HighPriorityColor = Color(0xFFFF4646)
val NonePriorityColor = MediumGray
//val NonePriorityColor = Color(0xFFFFFFFF)

val LightPurple = Color(0xFF787FF6)
val LightBlue1 = Color(0xFF78D5F5)
val LightBlue2 = Color(0xFF4ADEDE)
val MediumBlue = Color(0xFF1CA7EC)
val DarkBlue = Color(0xFF1F2F98)
val DarkerBlue = Color(0xFF0F1649)

val PastelStrawberry = Color(0xFFE44F5C)

val LightBeige = Color(0xFFF5F5DC)
val PeachBeige = Color(0xFFFFE5B4)
val PeachBeige2 = Color(0xFFF6E3BA)
val PeachBeige3 = Color(0xFFF1E9D9)
val MediumBeige = Color(0xFFDFCAA0)
val DarkBeige = Color(0xFFDECFAC)

val LightGreen = Color(0xFF92FE9D)
val LightBlue = Color(0xFF00C9FF)

val MediumGreen = Color(0xFF02C39A)
val MediumYellow = Color(0xFFF0F3BD)

val Green1 = Color(0xFF9be2d4)
val Green2 = Color(0xFFb8f0e3)
val Green3 = Color(0xFFcbf1e4)
val Gray1 = Color(0xFFc2c2c2)
val Gray2 = Color(0xFF797687)

val colorPrimary = Color(0xFF1BA57B)
val gray = Color(0xFF4B4F5A)
val dark_gray = Color(0xFF54555A)
val light_gray = Color(0xFF696969)
val ghost_white = Color(0xFFF8F8F8)
val text_hint_color = Color(0xFFA7A7A7)

// color from First Link
val greenPrimary1 = Color(0xFF4d8d6e)
val greenMid1 = Color(0xFF94BAA8)
val greenMid2 = Color(0xFF8BC0A7)
val greenLight1 = Color(0xFFCDE3D8)
val secondaryGreenMid3 = Color(0xFFA1C254)
val secondaryGreenLight2 = Color(0xFFD9E6BA)

val secondaryBlue = Color(0xFF83ABD6)

val greenPrimary3 = Color(0xFF244234)
val greenPrimary5 = Color(0xFFc4cbbf)
val greenPrimary6 = Color(0xFF3c4236)

val marahGold = Color(0xFFD6AD60)
val marahGold2 = Color(0xA6D6AD60)
val gold = Color(0xFFEEBC1D)
val gold2 = Color(0xFFBE9617)
val gold3 = Color(0xFFb08f26)
val goldDark = Color(0x90EEBC1D)
val goldDark2 = Color(0xFF5F4B0C)


val Colors.gradientButtonColors: Brush
    @Composable
    get() = Brush.horizontalGradient(
        colors = listOf(primary, primaryVariant, primary),
    )

val Colors.signUpColor: Color
    get() = if (isLight) primary else primaryVariant

val Colors.cardFirstColor: Color
    get() = if (isLight) greenLight1 else DarkerBlue

val Colors.cardSecondColor: Color
    get() = if (isLight) greenLight1 else DarkBlue

val Colors.cardFirstColorGold: Color
    get() = if (isLight) MediumBeige else marahGold

val Colors.cardSecondColorGold: Color
    get() = if (isLight) PeachBeige2 else marahGold2

val Colors.cardColor: Brush
    @Composable
    get() = Brush.horizontalGradient(listOf(cardFirstColor, cardSecondColor))

val Colors.cardColorReversed: Brush
    @Composable
    get() = Brush.horizontalGradient(
        listOf(MaterialTheme.colors.cardFirstColor, MaterialTheme.colors.cardSecondColor).reversed())

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
    get() = if (isLight) Color.DarkGray else goldDark2

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = MaterialTheme.colors.primary
//    get() = if (isLight) Purple500 else Color.Black

/*val Colors.cardColor: Brush
    @Composable
    get() = if (isLight)
        Brush.horizontalGradient(listOf(MediumGreen,LightGreen))
    else Brush.horizontalGradient(listOf(DarkerBlue, DarkBlue))*/

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
    get() = if (isLight) greenLight1 else MediumGray

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
