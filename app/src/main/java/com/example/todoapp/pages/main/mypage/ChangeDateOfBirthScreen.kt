package com.example.todoapp.pages.main.mypage


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoapp.ui.transformation.DateOfBirthVisualTransformation


@Composable
@Preview
fun ChangeDateOfBirthScreen(
    navController: NavController
) {
    val context = LocalContext.current

    // TODO : 로그인한 사용자 생년월일 가져오기
    // DB 저장 형태 : YYYYMMDD
    val currentBirth = "20000101"

    // 실제 저장되는 값
    var newBirth by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 24.dp,
                vertical = 20.dp
            )
    ) {

        // 현재 생년월일
        Text(
            text = "현재 생년월일",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = currentBirth,
            onValueChange = {},
            enabled = false,
            visualTransformation = DateOfBirthVisualTransformation(),
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

        // 변경 생년월일
        Text(
            text = "변경 생년월일",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = newBirth,
            onValueChange = { input ->
                // 숫자만 허용
                val numbers = input.filter {
                    it.isDigit()
                }
                // 최대 8자리
                newBirth = numbers.take(8)
                // 입력 변경 시 에러 제거
                errorMessage = ""
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            placeholder = {
                Text(
                    text = "변경할 생년월일을 입력하세요",
                    fontSize = 13.sp,
                    lineHeight = 13.sp
                )
            },

            textStyle = TextStyle(
                fontSize = 13.sp,
                lineHeight = 13.sp
            ),

            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),

            visualTransformation = DateOfBirthVisualTransformation(),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFA0A0A0),
                focusedBorderColor = Color(0xFFA0A0A0)
            )
        )

        // Error Message
        Text(
            text = errorMessage,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(25.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // 변경 버튼
        Button(
            onClick = {
                when {
                    newBirth.isBlank() -> {
                        errorMessage = "생년월일을 입력해주세요"
                    }

                    newBirth.length != 8 -> {
                        errorMessage =
                            "생년월일 8자리를 입력해주세요"
                    }

                    newBirth == currentBirth -> {
                        errorMessage = "현재 생년월일과 동일합니다"
                    }

                    else -> {
                        // TODO : 서버에 생년월일 변경 요청

                        Toast.makeText(
                            context,
                            "생년월일이 변경되었습니다",
                            Toast.LENGTH_SHORT
                        ).show()

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
                text = "생년월일 변경하기",
                color = Color.White,
                fontSize = 15.sp
            )
        }
    }
}