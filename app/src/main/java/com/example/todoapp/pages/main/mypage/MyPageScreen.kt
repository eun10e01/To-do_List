package com.example.todoapp.pages.main.mypage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todoapp.navigation.Screen
import com.example.todoapp.notification.NotificationHelper
import com.example.todoapp.notification.TodoAlarmScheduler
import com.example.todoapp.preferences.UserPreferences
import com.example.todoapp.viewmodel.MyPageViewModel

data class MenuItemData(
    val title: String,
    val onClick: () -> Unit
)

@Composable
//@Preview
fun MyPageScreen(
    navController: NavController,
    viewModel: MyPageViewModel = viewModel()
) {
    val context = LocalContext.current
    val userPreferences = remember {
        UserPreferences(context)
    }

    LaunchedEffect(Unit) {
        val userId = userPreferences.getUserId()

        Log.d("LOGIN_TEST", "저장된 userId = $userId")

        if (userId != -1L) {
            viewModel.loadUser(userId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)


    ) {

        ProfileSection(
            nickname = viewModel.user?.nickname ?: "",
            name = viewModel.user?.name ?: ""
        )

        Spacer(modifier = Modifier.height(10.dp))

        MenuSection(
            title = "개인정보 수정",
            menus = listOf(
                MenuItemData(
                    title = "회원정보 변경",
                    onClick = {
                        navController.navigate("change_user_info")
                    }
                ),
                MenuItemData(
                    title = "닉네임 변경",
                    onClick = {
                        navController.navigate("change_nickname")
                    }
                ),
                MenuItemData(
                    title = "비밀번호 변경",
                    onClick = {
                        navController.navigate("change_password")
                    }
                )
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        MenuSection(
            title = "설정",
            menus = listOf(
                MenuItemData(
                    title = "화면 모드 변경",
                    onClick = {
                        navController.navigate("ChangeScreenMode")
                    }
                ),
                MenuItemData(
                    title = "알림 설정",
                    onClick = {
                        navController.navigate("NotificationSettings")
                    }
                ),
                MenuItemData(
                    title = "공지사항",
                    onClick = {
                        navController.navigate("Notice")
                    }
                )
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        MenuSection(
            title = "",
            menus = listOf(
                MenuItemData(
                    title = "로그아웃",
                    onClick = {
                        TodoAlarmScheduler.cancelAll(context)   // 알림창 전체 삭제
                        userPreferences.logout()
                        println("로그아웃 후 userId = ${userPreferences.getUserId()}") // -1 나오면 성공
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { // 백스택 정리
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                ),
                MenuItemData(
                    title = "회원 탈퇴",
                    onClick = {
                        navController.navigate(Screen.Withdrawal.route)
                    }
                )
            )
        )

        // 테스트용 버튼
        Button(
            onClick = {
                TodoAlarmScheduler.scheduleTest(context)
            }
        ) {
            Text("1분 후 알람 테스트")
        }

    }
}

@Composable
fun ProfileSection(
    nickname: String,
    name: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F6E8))
            .padding(24.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            tint = Color.Gray
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {

            Text(
                text = nickname,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = name,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun MenuSection(
    title: String?,
    menus: List<MenuItemData>
) {

    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {

        if (!title.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
        menus.forEach { menu ->
            MenuItem(menu.title) {
                menu.onClick()
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
fun MenuItem(
    title: String,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(vertical = 10.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            fontSize = 15.sp
        )

//        Icon(
//            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
//            contentDescription = null
//        )
    }
}



