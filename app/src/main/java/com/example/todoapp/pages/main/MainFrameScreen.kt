package com.example.todoapp.pages.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.todoapp.navigation.Screen
import com.example.todoapp.navigation.bottomNavItems
import com.example.todoapp.navigation.AppNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainFrameScreen(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarRoutes = listOf(Screen.Calendar.route, Screen.Home.route, Screen.MyPage.route)

    val topBarTitles = mapOf(
        Screen.ChangeUserInfo.route to "회원정보 변경",
        Screen.ChangeNickname.route to "닉네임 변경",
        Screen.ChangeEmail.route to "이메일 변경",
        Screen.ChangePhoneNumber.route to "휴대폰번호 변경",
        Screen.ChangeDateOfBirth.route to "생년월일 변경",
        Screen.ChangePassword.route to "비밀번호 변경",
        Screen.Withdrawal.route to "회원 탈퇴"
    )

    Scaffold(
        topBar = {
            topBarTitles[currentRoute]?.let { title ->
                TopAppBar(
                    title = {
                        Text(
                            text = title,
                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector =
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "뒤로가기"
                            )
                        }
                    }
                )
            }
        },

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