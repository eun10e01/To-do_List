package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp.pages.main.calendar.CalendarScreen

import com.example.todoapp.pages.main.home.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier){
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier){
        //홈 화면
        composable(Screen.Home.route){
            HomeScreen()
        }

        //달력 화면
        composable(Screen.Calendar.route){
            CalendarScreen()
        }

        //마이페이지 화면
        composable(Screen.MyPage.route){

        }
    }
}