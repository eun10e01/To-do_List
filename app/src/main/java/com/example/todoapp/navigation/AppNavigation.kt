package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp.pages.auth.LoginScreen
import com.example.todoapp.pages.main.calendar.CalendarScreen

import com.example.todoapp.pages.main.home.HomeScreen
import com.example.todoapp.pages.main.home.ScheduleEditScreen
import com.example.todoapp.pages.main.home.TodoViewModel
import com.example.todoapp.pages.main.mypage.ChangeDateOfBirthScreen
import com.example.todoapp.pages.main.mypage.ChangeEmailScreen
import com.example.todoapp.pages.main.mypage.MyPageScreen
import com.example.todoapp.pages.main.mypage.ChangeNicknameScreen
import com.example.todoapp.pages.main.mypage.ChangePasswordScreen
import com.example.todoapp.pages.main.mypage.ChangePhoneNumberScreen
import com.example.todoapp.pages.main.mypage.ChangeUserInfoScreen
import com.example.todoapp.pages.signup.SignUpScreen
import com.example.todoapp.preferences.UserPreferences

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier){
    val todoViewModel: TodoViewModel = viewModel()
    val context = LocalContext.current
    val userPreferences = remember {
        UserPreferences(context)
    }

    val startDestination =
        if (userPreferences.isLoggedIn()) {
            Screen.Home.route
        } else {
            Screen.Login.route
        }

    NavHost(navController = navController, startDestination = startDestination, modifier = modifier){
        //홈 화면
        composable(Screen.Home.route){
            HomeScreen(
                viewModel = todoViewModel,
                onIconClick = {navController.navigate(Screen.EditSchedule.route)}
            )
        }

        composable(Screen.EditSchedule.route) {
            ScheduleEditScreen(
                navController = navController,
                viewModel = todoViewModel
            )
        }

        // 로그인 화면
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        // 회원가입 화면
        composable(Screen.SignUp.route) {
            SignUpScreen(navController)
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