package com.example.todoapp.pages.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todoapp.navigation.Screen
import com.example.todoapp.preferences.UserPreferences
import com.example.todoapp.viewmodel.LoginViewModel

@Composable
fun LoginTextField(
    icon: ImageVector,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = hint,
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            // 입력창
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                modifier = Modifier.weight(1f),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp
                ),
                visualTransformation = if (isPassword) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                decorationBox = { innerTextField ->

                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
            )
        }

        // 밑줄
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
    }
}

@Composable
//@Preview
fun LoginScreen(
//    onSignUpClick: () -> Unit,
//    onFindAccountClick: () -> Unit
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel.loginSuccess) {
        if (viewModel.loginSuccess) {
            viewModel.loggedInUserId?.let { userId ->
                val userPreferences = UserPreferences(context)
                userPreferences.saveUserId(userId)
            }
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDF5E2))
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center
    ) {

        // 아이디 입력
        LoginTextField(
            icon = Icons.Default.Person,
            hint = "아이디를 입력하세요",
            value = viewModel.loginId,
            onValueChange = viewModel::onLoginIdChanged
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // 비밀번호 입력
        LoginTextField(
            icon = Icons.Default.Lock,
            hint = "비밀번호를 입력하세요",
            value = viewModel.password,
            onValueChange = viewModel::onPasswordChanged,
            isPassword = true
        )

        // 오류 메시지(현재는 값이 없을 경우만 완료, 이후에 아이디 또는 비밀번호가 틀렸을 경우도 추가해야함)
        Text(
            text = viewModel.errorMessage.ifEmpty { " " },
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            minLines = 1
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        // 로그인 버튼
        Button(
            onClick = {
                viewModel.login()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF858677)
            )
        ) {
            Text(
                text = "로그인",
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = "회원가입",
                color = Color.Gray,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.SignUp.route)
                }
            )

            Text(
                text = "  또는  ",
                color = Color.Gray
            )

            Text(
                text = "아이디 / 비밀번호 찾기",
                color = Color.Gray,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    // 아이디/비밀번호 찾기 화면 이동
                }
            )
        }
    }
}