package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp.pages.main.calendar.CalendarScreen

import com.example.todoapp.pages.main.home.HomeScreen
import com.example.todoapp.pages.main.mypage.MyPageScreen
import com.example.todoapp.pages.main.mypage.ChangeNicknameScreen
import com.example.todoapp.pages.main.mypage.ChangePasswordScreen

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
            MyPageScreen(navController)
        }

        // 닉네임 변경 화면
        composable(Screen.ChangeNickname.route){
            ChangeNicknameScreen()
        }

        // 비밀번호 변경 화면
        composable(Screen.ChangePassword.route) {
            ChangePasswordScreen()
        }
    }
}