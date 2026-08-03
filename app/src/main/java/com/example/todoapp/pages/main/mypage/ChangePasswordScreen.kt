package com.example.todoapp.pages.main.mypage

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
//@Preview
fun ChangePasswordScreen(
    navController: NavController
) {

    val context = LocalContext.current

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


    // 현재 비밀번호 에러
    var currentPasswordError by remember {
        mutableStateOf("")
    }

    // 변경 비밀번호 관련 에러
    var passwordError by remember {
        mutableStateOf("")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {


        // 현재 비밀번호
        Text(
            text = "현재 비밀번호",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = currentPassword,
            onValueChange = {
                currentPassword = it
                currentPasswordError = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            placeholder = {
                Text(
                    text = "기존 비밀번호 입력",
                    fontSize = 13.sp,
                    lineHeight = 13.sp
                )
            },
            textStyle = TextStyle(
                fontSize = 13.sp,
                lineHeight = 13.sp
            ),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            shape = RoundedCornerShape(7.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFA0A0A0),
                focusedBorderColor = Color(0xFFA0A0A0)
            )
        )


        // 현재 비밀번호 에러
        Text(
            text = currentPasswordError,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp)
                .padding(top = 8.dp),
            minLines = 1
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 변경할 비밀번호
        Text(
            text = "변경할 비밀번호",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = newPassword,
            onValueChange = {
                newPassword = it
                passwordError = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            placeholder = {
                Text(
                    text = "8~16자 대소문자, 숫자, 특수문자",
                    fontSize = 13.sp,
                    lineHeight = 13.sp
                )
            },
            textStyle = TextStyle(
                fontSize = 13.sp,
                lineHeight = 13.sp
            ),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            shape = RoundedCornerShape(7.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFA0A0A0),
                focusedBorderColor = Color(0xFFA0A0A0)
            )
        )


        Spacer(modifier = Modifier.height(12.dp))


        // 새 비밀번호 확인
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                passwordError = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            placeholder = {
                Text(
                    text = "새 비밀번호 확인",
                    fontSize = 13.sp,
                    lineHeight = 13.sp
                )
            },
            textStyle = TextStyle(
                fontSize = 13.sp,
                lineHeight = 13.sp
            ),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            shape = RoundedCornerShape(7.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFA0A0A0),
                focusedBorderColor = Color(0xFFA0A0A0)
            )
        )


        // 변경 비밀번호 관련 에러
        Text(
            text = passwordError,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp)
                .padding(top = 8.dp),
            minLines = 1
        )

        Spacer(modifier = Modifier.weight(1f))

        // 비밀번호 변경 버튼
        Button(
            onClick = {

                // 기존 에러 초기화
                currentPasswordError = ""
                passwordError = ""


                when {


                    // 현재 비밀번호 입력 확인
                    currentPassword.isBlank() -> {

                        currentPasswordError =
                            "비밀번호를 입력해주세요."

                    }


                    // 새 비밀번호 입력 확인
                    newPassword.isBlank()
                            || confirmPassword.isBlank() -> {

                        passwordError =
                            "비밀번호를 입력해주세요."

                    }


                    // 새 비밀번호 확인
                    newPassword != confirmPassword -> {

                        passwordError =
                            "비밀번호가 서로 다릅니다."

                    }


                    // 기존 비밀번호와 동일
                    newPassword == currentPassword -> {

                        passwordError =
                            "현재 비밀번호와 일치합니다."

                    }


                    else -> {

                        // TODO : 서버에 비밀번호 변경 요청


                        Toast
                            .makeText(
                                context,
                                "비밀번호가 변경되었습니다",
                                Toast.LENGTH_SHORT
                            )
                            .show()


                        // 성공 후 이동 예정
                         navController.popBackStack()
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
                text = "비밀번호 변경하기",
                color = Color.White,
                fontSize = 15.sp
            )
        }
    }
}