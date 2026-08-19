package com.example.todoapp.pages.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todoapp.navigation.Screen
import com.example.todoapp.viewmodel.SignUpViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel.signupSuccess) {
        if (viewModel.signupSuccess) {
            Toast.makeText(
                context,
                "회원가입이 완료되었습니다",
                Toast.LENGTH_SHORT
            ).show()

            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.SignUp.route) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFEDF5E2)
            )
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 24.dp,
                vertical = 20.dp
            )
    ) {
        //회원 정보 입력 영역
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                SignupInputItem(
                    title = "아이디",
                    value = viewModel.loginId,
                    onValueChange = viewModel::onLoginIdChanged,
                    placeholder = "아이디를 입력하세요",
                    hasButton = true,
                    onButtonClick = {
                        viewModel.checkLoginId()
                    },
                    errorMessage = viewModel.loginIdCheckMessage,
                    errorMessageColor =
                        if (viewModel.loginIdAvailable)
                            Color(0xFF2E7D32)
                        else
                            Color.Red
                )

                Spacer(modifier = Modifier.height(10.dp))

                SignupInputItem(
                    title = "비밀번호",
                    value = viewModel.password,
                    onValueChange = viewModel::onPasswordChanged,
                    placeholder = "비밀번호를 입력하세요",
                    isPassword = true
                )

                SignupInputItem(
                    title = "비밀번호 확인",
                    value = viewModel.passwordCheck,
                    onValueChange = viewModel::onPasswordCheckChanged,
                    placeholder = "비밀번호를 다시 입력하세요",
                    isPassword = true,
                    errorMessage = viewModel.passwordCheckMessage,
                    errorMessageColor =
                        if (viewModel.passwordMatched)
                            Color(0xFF2E7D32)
                        else
                            Color.Red
                )

                Spacer(modifier = Modifier.height(10.dp))

                SignupInputItem(
                    title = "이름",
                    value = viewModel.name,
                    onValueChange = viewModel::onNameChanged,
                    placeholder = "이름을 입력하세요",
                    errorMessage = viewModel.nameCheckMessage
                )

                Spacer(modifier = Modifier.height(10.dp))

                SignupInputItem(
                    title = "닉네임",
                    value = viewModel.nickname,
                    onValueChange = viewModel::onNicknameChanged,
                    placeholder = "닉네임을 입력하세요",
                    hasButton = true,
                    onButtonClick = {
                        viewModel.checkNickname()
                    },
                    errorMessage = viewModel.nicknameCheckMessage,
                    errorMessageColor =
                        if (viewModel.nicknameAvailable)
                            Color(0xFF2E7D32)
                        else
                            Color.Red
                )

                Spacer(modifier = Modifier.height(10.dp))

                SignupInputItem(
                    title = "이메일",
                    value = viewModel.email,
                    onValueChange = viewModel::onEmailChanged,
                    placeholder = "이메일 주소를 입력하세요",
                    errorMessage = viewModel.emailCheckMessage
                )

                Spacer(modifier = Modifier.height(10.dp))

                SignupInputItem(
                    title = "휴대폰번호",
                    value = viewModel.phone,
                    onValueChange = viewModel::onPhoneChanged,
                    placeholder = "휴대폰번호를 입력하세요",
                    keyboardType = KeyboardType.Number,
                    errorMessage = viewModel.phoneCheckMessage
                )

                Spacer(modifier = Modifier.height(10.dp))

                SignupInputItem(
                    title = "생년월일",
                    value = viewModel.birth,
                    onValueChange = viewModel::onBirthChanged,
                    placeholder = "생년월일 8자리를 입력하세요",
                    keyboardType = KeyboardType.Number,
                    errorMessage = viewModel.birthCheckMessage
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        //약관 동의 영역
//        Surface(
//            modifier = Modifier
//                .fillMaxWidth(),
//            shape = RoundedCornerShape(5.dp),
//            color = Color.White
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(20.dp)
//            ) {
//                /*
//                    여기에 추가 예정
//
//                    전체동의
//
//                    이용약관 (필수) 자세히보기
//
//                    개인정보처리방침 (필수) 자세히보기
//
//                */

//                Spacer(modifier = Modifier.height(120.dp) )
//            }
//        }
//        Spacer(
//            modifier = Modifier.height(30.dp)
//        )

        //회원가입 버튼
        Button(
            onClick = {
                viewModel.signup()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(7.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF858677)
            )
        ) {
            Text(
                text = "회원가입",
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun SignupInputItem(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    hasButton: Boolean = false,
    onButtonClick: (() -> Unit)? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    errorMessage: String = "",
    errorMessageColor: Color = Color.Red
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }

    Column {
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                placeholder = {
                    Text(
                        text = placeholder
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType
                ),
                visualTransformation = if (isPassword && !passwordVisible) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                trailingIcon = {
                    if (isPassword) {
                        IconButton(
                            onClick = {
                                passwordVisible = !passwordVisible
                            }
                        ) {
                            Icon(
                                imageVector = if (passwordVisible) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },
                                contentDescription = if (passwordVisible) {
                                    "비밀번호 숨기기"
                                } else {
                                    "비밀번호 보기"
                                },
                                modifier = Modifier.size(18.dp),
                                tint = Color.Gray
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFA0A0A0),
                    focusedBorderColor = Color(0xFFA0A0A0)
                )
            )
            if(hasButton) {
                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    onClick = {
                        onButtonClick?.invoke()
                    },
                    modifier = Modifier
                        .height(48.dp)
                        .width(80.dp),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(
                        horizontal = 5.dp,
                        vertical = 5.dp
                    )
                ) {
                    Text(
                        text = "중복확인",
                    )
                }
            }
        }

        //errorMessage
        Text(
            text = errorMessage.ifEmpty { " " },
            color = errorMessageColor,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            minLines = 1
        )
    }
}

//To-do :약관 동의 부분, phone&birth 입력 숫자 갯수 제한
