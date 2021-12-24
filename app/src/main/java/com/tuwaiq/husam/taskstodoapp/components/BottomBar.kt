package com.tuwaiq.husam.taskstodoapp.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
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
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
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
                popUpTo(BottomBarScreen.Home.route){
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