package com.example.todoapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null){
    //하단 네비게이션 탭 요소
//    object Other : Screen("other", "더보기", Icons.Default.MoreHoriz)
    object Home : Screen("home", "홈", Icons.Default.Home)
    object Calendar : Screen("calendar", "달력", Icons.Default.CalendarMonth)
    object MyPage : Screen("my_page", "마이페이지", Icons.Default.Person)

    //하위 화면
    object Login : Screen("login", "로그인")
    object SignUp : Screen("signup", "회원가입")
    object Withdrawal : Screen("withdrawal", "회원탈퇴")
    object EditSchedule : Screen("edit_schedule/{selectedDate}", "일정 수정"){
        fun createRoute(selectedDate: String) = "edit_schedule/$selectedDate"
    }
    object ChangeNickname : Screen("change_nickname", "닉네임 변경")
    object ChangePassword : Screen("change_password","비밀번호 변경")
    object ChangeEmail : Screen("change_email", "이메일 변경")
    object ChangePhoneNumber : Screen("change_phone_number", "휴대폰번호 변경")
    object ChangeDateOfBirth : Screen("change_date_of_birth", "생년월일 변경")
    object ChangeUserInfo : Screen("change_user_info", "회원정보 변경")
    object Notice : Screen("notice", "공지사항")
}

val bottomNavItems = listOf(Screen.Calendar, Screen.Home, Screen.MyPage)