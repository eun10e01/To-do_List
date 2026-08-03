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

@Composable
//@Preview
fun ChangePhoneNumberScreen(
    navController: NavController
) {

    val context = LocalContext.current

    // TODO : 로그인한 사용자의 휴대폰번호 가져오기
    val currentPhone = "01012345678"

    var newPhone by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    // 숫자 -> 010-1234-5678 형태로 변환
    fun formatPhoneNumber(phone: String): String {
        return when {
            phone.length <= 3 ->
                phone

            phone.length <= 7 ->
                "${phone.substring(0, 3)}-${phone.substring(3)}"

            else ->
                "${phone.substring(0, 3)}-${phone.substring(3, 7)}-${phone.substring(7)}"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {

        // 현재 휴대폰번호
        Text(
            text = "현재 휴대폰번호",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = formatPhoneNumber(currentPhone),
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

        // 변경할 휴대폰번호
        Text(
            text = "변경할 휴대폰번호",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = formatPhoneNumber(newPhone),
            onValueChange = { input ->

                // 숫자만 허용
                val numbers = input.filter { it.isDigit() }

                // 최대 11자리
                newPhone = numbers.take(11)

                errorMessage = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            placeholder = {
                Text(
                    text = "변경할 휴대폰번호를 입력하세요",
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
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFA0A0A0),
                focusedBorderColor = Color(0xFFA0A0A0)
            )
        )

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

        Button(
            onClick = {

                when {

                    newPhone.isBlank() -> {
                        errorMessage = "휴대폰번호를 입력해주세요"
                    }

                    newPhone.length != 11 -> {
                        errorMessage = "휴대폰 번호 11자리를 입력해주세요"
                    }

                    newPhone == currentPhone -> {
                        errorMessage = "현재 휴대폰번호와 동일합니다"
                    }

                    else -> {

                        // TODO : 서버에 휴대폰번호 변경 요청

                        Toast
                            .makeText(
                                context,
                                "휴대폰번호가 변경되었습니다",
                                Toast.LENGTH_SHORT
                            )
                            .show()

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
                text = "휴대폰번호 변경하기",
                color = Color.White,
                fontSize = 15.sp
            )
        }
    }
}