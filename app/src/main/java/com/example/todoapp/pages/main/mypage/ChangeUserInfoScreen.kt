package com.example.todoapp.pages.main.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todoapp.navigation.Screen
import com.example.todoapp.preferences.UserPreferences
import com.example.todoapp.viewmodel.ChangeUserInfoViewModel

@Composable
fun ChangeUserInfoScreen(
    navController: NavController,
    viewModel: ChangeUserInfoViewModel = viewModel()
) {
    val context = LocalContext.current

    val userPreferences = remember {
        UserPreferences(context)
    }

    LaunchedEffect(Unit) {
        val userId = userPreferences.getUserId()

        if (userId != -1L) {
            viewModel.loadUser(userId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 24.dp,
                vertical = 20.dp
            )
    ) {
        MemberInfoItem(
            title = "이름",
            value = viewModel.user?.name ?: ""
        )

        Spacer(modifier = Modifier.height(20.dp))

        MemberInfoItem(
            title = "아이디",
            value = viewModel.user?.loginId ?: ""
        )

        Spacer(modifier = Modifier.height(20.dp))

        MemberInfoItem(
            title = "닉네임",
            value = viewModel.user?.nickname ?: "",
            showChange = true,
            onClickChange = {
                navController.navigate(Screen.ChangeNickname.route)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        MemberInfoItem(
            title = "비밀번호",
            value = "*******",
            showChange = true,
            onClickChange = {
                navController.navigate(Screen.ChangePassword.route)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        MemberInfoItem(
            title = "이메일",
            value = viewModel.user?.email ?: "",
            showChange = true,
            onClickChange = {
                navController.navigate(Screen.ChangeEmail.route)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        MemberInfoItem(
            title = "휴대폰번호",
            value = viewModel.user?.phone ?: "",
            showChange = true,
            onClickChange = {
                navController.navigate(Screen.ChangePhoneNumber.route)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        MemberInfoItem(
            title = "생년월일",
            value = viewModel.user?.birth ?: "",
            showChange = true,
            onClickChange = {
                navController.navigate(Screen.ChangeDateOfBirth.route)
            }
        )
    }
}

@Composable
fun MemberInfoItem(
    title: String,
    value: String,
    showChange: Boolean = false,    //변경하기 버튼을 화면에 보여줄지 말지 결정
    onClickChange: () -> Unit = {}
) {
    Column {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                enabled = false,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color(0xFFA0A0A0),
                    disabledTextColor = Color.Black,
                    disabledContainerColor = Color.Transparent
                )
            )

            if (showChange) {
                Spacer(modifier = Modifier.width(10.dp))

                TextButton(
                    onClick = onClickChange,
                    modifier = Modifier.height(48.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "변경하기",
                        color = Color(0xFF444F34),
                    )
                }
            }
        }
    }
}