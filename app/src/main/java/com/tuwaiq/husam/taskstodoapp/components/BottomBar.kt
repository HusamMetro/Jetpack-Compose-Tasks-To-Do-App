package com.tuwaiq.husam.taskstodoapp.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tuwaiq.husam.taskstodoapp.R
import com.tuwaiq.husam.taskstodoapp.navigation.BottomBarScreen
import com.tuwaiq.husam.taskstodoapp.ui.theme.bottomBarSelectedContentColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.bottomBarUnselectedContentColor
import com.tuwaiq.husam.taskstodoapp.ui.theme.topAppBarBackgroundColor

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Challenges,
        BottomBarScreen.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val context = LocalContext.current
    BottomNavigationItem(
        label = {
            Text(
                // i used the route because its constant not like the title could change
                text = when (screen.route) {
                    BottomBarScreen.Home.route -> context.getString(R.string.list_bottom_bar)
                    BottomBarScreen.Challenges.route -> context.getString(R.string.challenges_bottom_bar)
                    BottomBarScreen.Settings.route -> context.getString(R.string.settings_bottom_bar)
                    else -> ""
                }
            )
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(BottomBarScreen.Home.route) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        selectedContentColor = MaterialTheme.colors.bottomBarSelectedContentColor,
        unselectedContentColor = MaterialTheme.colors.bottomBarUnselectedContentColor.copy(alpha = ContentAlpha.disabled)
//        unselectedContentColor = Color.White.copy(alpha = ContentAlpha.disabled)
//      unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
    )
}