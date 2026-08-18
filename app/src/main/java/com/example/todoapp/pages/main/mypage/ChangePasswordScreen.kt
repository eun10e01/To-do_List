package com.example.todoapp.pages.main.mypage

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todoapp.dto.ChangePasswordRequest
import com.example.todoapp.preferences.UserPreferences
import com.example.todoapp.viewmodel.ChangePasswordViewModel

@Composable
//@Preview
fun ChangePasswordScreen(
    navController: NavController,
    viewModel: ChangePasswordViewModel = viewModel()
) {
    val context = LocalContext.current

    val userPreferences = remember {
        UserPreferences(context)
    }

    var currentPasswordVisible by remember {
        mutableStateOf(false)
    }

    var newPasswordVisible by remember {
        mutableStateOf(false)
    }

    var newPasswordConfirmVisible by remember {
        mutableStateOf(false)
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
            text = "현재 비밀번호",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.currentPassword,
            onValueChange = {
                viewModel.onCurrentPasswordChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            singleLine = true,
            visualTransformation =
                if (currentPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
            trailingIcon = {
                IconButton(
                    onClick = {
                        currentPasswordVisible =
                            !currentPasswordVisible
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = if (currentPasswordVisible) {
                            Icons.Default.VisibilityOff
                        } else {
                            Icons.Default.Visibility
                        },
                        contentDescription = if (currentPasswordVisible) {
                            "비밀번호 숨기기"
                        } else {
                            "비밀번호 보기"
                        },
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFA0A0A0),
                focusedBorderColor = Color(0xFFA0A0A0)
            )
        )

        // currentPasswordErrorMessage
        Text(
            text = viewModel.currentPasswordError,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            minLines = 1
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "새 비밀번호",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.newPassword,
            onValueChange = {
                viewModel.onNewPasswordChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            singleLine = true,
            visualTransformation =
                if (newPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
            trailingIcon = {
                IconButton(
                    onClick = {
                        newPasswordVisible =
                            !newPasswordVisible
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = if (newPasswordVisible) {
                            Icons.Default.VisibilityOff
                        } else {
                            Icons.Default.Visibility
                        },
                        contentDescription = if (newPasswordVisible) {
                            "비밀번호 숨기기"
                        } else {
                            "비밀번호 보기"
                        },
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFA0A0A0),
                focusedBorderColor = Color(0xFFA0A0A0)
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "새 비밀번호 확인",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.newPasswordConfirm,
            onValueChange = {
                viewModel.onNewPasswordConfirmChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            singleLine = true,
            visualTransformation =
                if (newPasswordConfirmVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
            trailingIcon = {
                IconButton(
                    onClick = {
                        newPasswordConfirmVisible =
                            !newPasswordConfirmVisible
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = if (newPasswordConfirmVisible) {
                            Icons.Default.VisibilityOff
                        } else {
                            Icons.Default.Visibility
                        },
                        contentDescription = if (newPasswordConfirmVisible) {
                            "비밀번호 숨기기"
                        } else {
                            "비밀번호 보기"
                        },
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFA0A0A0),
                focusedBorderColor = Color(0xFFA0A0A0)
            )
        )

        // errorMessage
        Text(
            text = viewModel.passwordCheckMessage,
            color = Color.Red,
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
                    viewModel.changePassword(
                        userId = userId,
                        onSuccess = {
                            Toast
                                .makeText(
                                    context,
                                    "비밀번호가 변경되었습니다",
                                    Toast.LENGTH_SHORT
                                )
                                .show()

                            navController.popBackStack()
                        }
                    )
                } else {
                    Toast
                        .makeText(
                            context,
                            "로그인 정보를 찾을 수 없습니다",
                            Toast.LENGTH_SHORT
                        )
                        .show()
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