package com.tuwaiq.husam.taskstodoapp.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

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


val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = MaterialTheme.colors.primary
//    get() = if (isLight) Purple500 else Color.Black

val Colors.cardColor: Brush
    @Composable
    get() = if (isLight)
        Brush.horizontalGradient(listOf(MediumGreen,LightGreen))
    else Brush.horizontalGradient(listOf(DarkerBlue, DarkBlue))

val Colors.cardBorderColor: Color
    @Composable
    get() = if (isLight) Color.Transparent else Color.Transparent

val Colors.foregroundIndicatorColor: Color
    @Composable
    get() = if (isLight) Color.DarkGray else LightBlue2

val Colors.backgroundIndicatorColor: Color
    @Composable
    get() = if (isLight) Color.White else MediumGray

val Colors.bottomBarSelectedContentColor: Color
    @Composable
    get() = topAppBarContentColor


val Colors.bottomBarUnselectedContentColor: Color
    @Composable
    get() = if (isLight) Color.White else MediumGray


