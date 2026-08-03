package com.example.todoapp.pages.main.mypage

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
//@Preview
fun ChangeEmailScreen(
    navController: NavController
) {

    val context = LocalContext.current


    // TODO : 로그인한 사용자의 이메일 가져오기
    val currentEmail = "honggildong@gmail.com"


    var newEmail by remember {
        mutableStateOf("")
    }


    // 에러 메시지
    var errorMessage by remember {
        mutableStateOf("")
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {


        // 현재 이메일
        Text(
            text = "현재 이메일",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        OutlinedTextField(
            value = currentEmail,
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

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // 변경 이메일
        Text(
            text = "변경 이메일",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        OutlinedTextField(
            value = newEmail,
            onValueChange = {
                newEmail = it
                errorMessage = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            placeholder = {
                Text(
                    text = "변경할 이메일 주소를 입력하세요",
                    fontSize = 13.sp,
                    lineHeight = 13.sp
                )
            },
            textStyle = TextStyle(
                fontSize = 13.sp,
                lineHeight = 13.sp
            ),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFA0A0A0),
                focusedBorderColor = Color(0xFFA0A0A0)
            )
        )


        // errorMessage
        Text(
            text = errorMessage,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(25.dp),
            minLines = 1
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        // 이메일 변경 버튼
        Button(
            onClick = {

                when {

                    newEmail.isBlank() -> {

                        errorMessage = "이메일을 입력해주세요."

                    }

                    // 현재 이메일과 동일
                    newEmail == currentEmail -> {

                        errorMessage = "현재 이메일과 동일합니다."

                    }

                    else -> {

                        // TODO : 서버에 이메일 변경 요청


                        Toast
                            .makeText(
                                context,
                                "이메일이 변경되었습니다",
                                Toast.LENGTH_SHORT
                            )
                            .show()


                        // 이전 화면으로 이동
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
                text = "이메일 변경하기",
                color = Color.White,
                fontSize = 15.sp
            )
        }
    }
}