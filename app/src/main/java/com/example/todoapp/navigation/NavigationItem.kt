package com.example.todoapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null){
    //하단 네비게이션 탭 요소
    object Other : Screen("other", "더보기", Icons.Default.MoreHoriz)
    object Home : Screen("home", "홈", Icons.Default.Home)
    object Calendar : Screen("calendar", "달력", Icons.Default.CalendarMonth)
    object MyPage : Screen("my_page", "마이페이지", Icons.Default.Person)

    //하위 화면
    object EditSchedule : Screen("edit_schedule", "일정 수정")
}

val bottomNavItems = listOf(Screen.Other, Screen.Home, Screen.Calendar, Screen.MyPage)