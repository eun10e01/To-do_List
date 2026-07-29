package com.example.todoapp.pages.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.*
import androidx.compose.material3.NavigationBarDefaults.containerColor
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.example.todoapp.navigation.Screen
import com.example.todoapp.navigation.bottomNavItems
import com.example.todoapp.navigation.AppNavigation

@Composable
fun MainFrameScreen(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarRoutes = listOf(Screen.Other.route, Screen.Home.route, Screen.Calendar.route, Screen.MyPage.route)

    Scaffold(
        bottomBar = {
            if(currentRoute in bottomBarRoutes){
                NavigationBar(modifier = Modifier
                    .border(width = 1.dp, Color(0xFFA0A0A0)),
                    containerColor = Color.White,
                ){
                    bottomNavItems.forEach{screen -> NavigationBarItem(
                        icon = {screen.icon?.let{Icon(imageVector = it, contentDescription = screen.title)}},
                        label = {Text(screen.title)},
                        selected = currentRoute == screen.route,
                        onClick = {
                            if(currentRoute != screen.route){
                                navController.navigate(screen.route){
                                    popUpTo(navController.graph.findStartDestination().id){
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color(0xFFEDF5E2)
                        )
                    )}
                }
            }
        }
    ){innerPadding -> AppNavigation(navController = navController,
        modifier = Modifier.padding(innerPadding)
    )}
}