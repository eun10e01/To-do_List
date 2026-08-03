package com.example.todoapp.pages.main.mypage

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
@Preview
fun ChangeNicknameScreen(
//    navController: NavController
) {
    // TODO : 로그인한 사용자의 닉네임으로 변경
    val currentNickname = "홍길동"

    var newNickname by remember { mutableStateOf("") }

    // 에러/성공 메시지
    var errorMessage by remember { mutableStateOf("") }
    var errorMessageColor by remember { mutableStateOf(Color.Red) }

    // 중복확인 성공 여부
    var isNicknameChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {

        // 현재 닉네임
        Text(
            text = "현재 닉네임",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = currentNickname,
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            textStyle = TextStyle(
                fontSize = 13.sp,
                lineHeight = 13.sp
            ),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color(0xFFA0A0A0),
                disabledTextColor = Color.Black,
                disabledContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 변경할 닉네임
        Text(
            text = "변경할 닉네임",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = newNickname,
                onValueChange = {
                    newNickname = it

                    // 입력이 바뀌면 다시 중복확인 필요
                    isNicknameChecked = false
                    errorMessage = ""
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                textStyle = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 13.sp
                ),
                placeholder = {
                    Text(
                        text="변경할 닉네임을 입력하세요",
                        fontSize = 13.sp,
                        lineHeight = 13.sp
                    )
                },
                shape = RoundedCornerShape(7.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFA0A0A0),
                    focusedBorderColor = Color(0xFFA0A0A0)
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedButton(
                onClick = {

                    when {

                        newNickname.isBlank() -> {
                            errorMessage = "닉네임을 입력해주세요."
                            errorMessageColor = Color.Red
                            isNicknameChecked = false
                        }

                        newNickname == currentNickname -> {
                            errorMessage = "현재 닉네임과 동일합니다."
                            errorMessageColor = Color.Red
                            isNicknameChecked = false
                        }

                        else -> {
                            // TODO : 서버에 중복확인 요청

                            errorMessage = "사용 가능한 닉네임입니다."
                            errorMessageColor = Color(0xFF007800)
                            isNicknameChecked = true
                        }
                    }
                },
                modifier = Modifier
                    .height(48.dp)
                    .width(100.dp)
                ,
                shape = RoundedCornerShape(7.dp),
                border = null,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFFCFD2B2)
                )
            ) {
                Text(
                    text="중복확인",
                    fontSize = 13.sp
                    )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = errorMessage.ifEmpty { "" },
            color = errorMessageColor,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            minLines = 1
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {

                when {

                    newNickname.isBlank() -> {
                        errorMessage = "닉네임을 입력해주세요."
                        errorMessageColor = Color.Red
                    }

                    !isNicknameChecked -> {
                        errorMessage = "중복확인을 해주세요."
                        errorMessageColor = Color.Red
                    }

                    else -> {
                        // TODO : 서버에 닉네임 변경 요청

                        errorMessage = "닉네임이 변경되었습니다."
                        errorMessageColor = Color(0xFF2E7D32)

                        // 예시
                        // navController.popBackStack()
                    }
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