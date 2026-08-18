package com.example.todoapp.pages.main.mypage

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todoapp.preferences.UserPreferences
import com.example.todoapp.viewmodel.ChangeNicknameViewModel

@Composable
//@Preview
fun ChangeNicknameScreen(
    navController: NavController,
    viewModel: ChangeNicknameViewModel = viewModel()
) {
    val context = LocalContext.current

    val userPreferences = remember {
        UserPreferences(context)
    }

    // 로그인한 사용자의 현재 닉네임 조회
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
        Text(
            text = "현재 닉네임",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.currentNickname,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color(0xFFA0A0A0),
                disabledTextColor = Color.Black,
                disabledContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "변경할 닉네임",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = viewModel.nickname,
                onValueChange = {
                    viewModel.onNicknameChanged(it)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                placeholder = {
                    Text(
                        text = "변경할 닉네임을 입력하세요",
                    )
                },
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFA0A0A0),
                    focusedBorderColor = Color(0xFFA0A0A0)
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedButton(
                onClick = {
                    viewModel.checkNickname()
                },
                modifier = Modifier
                    .height(48.dp)
                    .width(80.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(
                    horizontal = 5.dp,
                    vertical = 5.dp
                ),
                border = null,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFFCFD2B2)
                )
            ) {
                Text(
                    text = "중복확인"
                )
            }
        }

        // 중복확인 메시지
        Text(
            text = viewModel.nicknameCheckMessage,
            color = if (viewModel.nicknameAvailable) {
                Color(0xFF2E7D32)
            } else {
                Color.Red
            },
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            minLines = 1
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val userId = userPreferences.getUserId()

                if (userId != -1L) {
                    viewModel.changeNickname(
                        userId = userId,
                        onSuccess = {
                            Toast
                                .makeText(
                                    context,
                                    "닉네임이 변경되었습니다",
                                    Toast.LENGTH_SHORT
                                )
                                .show()

                            navController.popBackStack()
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF858677)
            )
        ) {
            Text(
                text = "닉네임 변경하기",
                color = Color.White,
                fontSize = 15.sp
            )
        }
    }
}