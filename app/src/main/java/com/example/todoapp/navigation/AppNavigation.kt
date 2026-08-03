package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp.pages.main.calendar.CalendarScreen

import com.example.todoapp.pages.main.home.HomeScreen
import com.example.todoapp.pages.main.mypage.ChangeDateOfBirthScreen
import com.example.todoapp.pages.main.mypage.ChangeEmailScreen
import com.example.todoapp.pages.main.mypage.MyPageScreen
import com.example.todoapp.pages.main.mypage.ChangeNicknameScreen
import com.example.todoapp.pages.main.mypage.ChangePasswordScreen
import com.example.todoapp.pages.main.mypage.ChangePhoneNumberScreen
import com.example.todoapp.pages.main.mypage.ChangeUserInfoScreen
import com.example.todoapp.pages.signup.SignUpScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier){
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier){
        //홈 화면
        composable(Screen.Home.route){
            HomeScreen()
        }

        // 회원가입 화면
        composable(Screen.SignUp.route) {
            SignUpScreen()
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
            ChangeNicknameScreen(navController)
        }

        // 비밀번호 변경 화면
        composable(Screen.ChangePassword.route) {
            ChangePasswordScreen(navController)
        }

        // 이메일 변경 화면
        composable(Screen.ChangeEmail.route) {
            ChangeEmailScreen(navController)
        }

        // 휴대폰번호 변경 화면
        composable (Screen.ChangePhoneNumber.route){
            ChangePhoneNumberScreen(navController)
        }

        // 생년월일 변경 화면
        composable (Screen.ChangeDateOfBirth.route) {
            ChangeDateOfBirthScreen(navController)
        }

        // 회원정보 변경 화면
        composable (Screen.ChangeUserInfo.route) {
            ChangeUserInfoScreen(navController)
        }
    }
}