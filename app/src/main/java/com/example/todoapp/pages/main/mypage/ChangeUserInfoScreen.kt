package com.example.todoapp.pages.main.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
//@Preview
fun ChangeUserInfoScreen(
    navController: NavController
) {


    // TODO : 로그인한 사용자 정보 가져오기

    val name = "홍길동"
    val id = "hong123"
    val nickname = "길동이"
    val password = "********"
    val email = "honggildong@gmail.com"
    val phone = "010-1234-5678"
    val birth = "2000-01-01"

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
            value = name
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        MemberInfoItem(
            title = "아이디",
            value = id
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        MemberInfoItem(
            title = "닉네임",
            value = nickname,
            showChange = true,
            onClickChange = {
                navController.navigate("change_nickname")

            }
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        MemberInfoItem(
            title = "비밀번호",
            value = password,
            showChange = true,
            onClickChange = {

                navController.navigate("change_password")

            }
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        MemberInfoItem(
            title = "이메일",
            value = email,
            showChange = true,
            onClickChange = {

                navController.navigate("change_email")

            }
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        MemberInfoItem(
            title = "휴대폰번호",
            value = phone,
            showChange = true,
            onClickChange = {

                navController.navigate("change_phone_number")

            }
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        MemberInfoItem(
            title = "생년월일",
            value = birth,
            showChange = true,
            onClickChange = {

                navController.navigate("change_date_of_birth")

            }
        )

    }

}





@Composable
fun MemberInfoItem(

    title: String,

    value: String,

    showChange: Boolean = false,

    onClickChange: () -> Unit = {}

) {


    Column {


        Text(

            text = title,

            fontWeight = FontWeight.Bold,

            fontSize = 15.sp

        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


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



            if (showChange) {


                Spacer(
                    modifier = Modifier.width(10.dp)
                )


                TextButton(

                    onClick = onClickChange,

                    modifier = Modifier.height(48.dp),

                    contentPadding = PaddingValues(0.dp)

                ) {


                    Text(

                        text = "변경하기",

                        color = Color(0xFF444F34),

                        fontSize = 13.sp

                    )

                }

            }

        }

    }

}