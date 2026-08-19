package com.example.todoapp.pages.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController
import com.example.todoapp.navigation.Screen
import com.example.todoapp.preferences.UserPreferences
import com.example.todoapp.viewmodel.DeleteAccountViewModel

@Composable
fun DeleteAccountScreen(
    navController: NavController,
    viewModel: DeleteAccountViewModel = viewModel()
) {
    val context = LocalContext.current

    val userPreferences = remember {
        UserPreferences(context)
    }

    LaunchedEffect(Unit) {
        val userId = userPreferences.getUserId()

        if (userId != -1L) {
            viewModel.loadUser(userId)
        }
    }

    //첫 번째 확인 팝업
    var showDeleteConfirmDialog by remember {
        mutableStateOf(false)
    }

    //두 번째 탈퇴 완료 팝업
    var showDeleteCompleteDialog by remember {
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
            text = "${viewModel.nickname}님 탈퇴하시겠어요?",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "탈퇴 안내",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFFA0A0A0),
                    shape = RoundedCornerShape(10.dp)
                )
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = "탈퇴를 진행하면 계정 및 프로필 정보, 현재까지 작성한 모든 투두리스트가 삭제됩니다.",
                lineHeight = 20.sp,
                color = Color.Black
            )
        }

        //errorMessage
        Text(
            text = viewModel.deleteCheckMessage,
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
                showDeleteConfirmDialog = true
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
                text = "탈퇴하기",
                color = Color.White,
                fontSize = 15.sp
            )
        }
    }

    //1차 탈퇴 확인 AlertDialog
    if (showDeleteConfirmDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteConfirmDialog = false
            },
            title = {
                Text(
                    text = "정말로 탈퇴하시겠습니까?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "탈퇴 버튼 선택 시 계정은 삭제되며 복구되지 않습니다.",
                    lineHeight = 20.sp
                )
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //취소 버튼
                    OutlinedButton(
                        onClick = {
                            showDeleteConfirmDialog = false
                        },
                        modifier = Modifier
                            .width(100.dp)
                            .height(45.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFA0A0A0)
                        ),
                        border = BorderStroke(
                            1.dp,
                            Color(0xFFA0A0A0)
                        ),
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        Text(
                            text = "취소"
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    //탈퇴하기 버튼
                    Button(
                        onClick = {
                            showDeleteConfirmDialog = false

                            val userId =
                                userPreferences.getUserId()

                            if (userId != -1L) {

                                viewModel.deleteAccount(
                                    userId = userId,
                                    onSuccess = {
                                        showDeleteCompleteDialog = true
                                    }
                                )
                            }
                        },
                        modifier = Modifier
                            .width(100.dp)
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF858677)
                        ),
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        Text(
                            text = "탈퇴하기",
                            color = Color.White
                        )
                    }
                }
            }
        )
    }

    //2차 탈퇴 완료 AlertDialog
    if (showDeleteCompleteDialog) {
        AlertDialog(
            onDismissRequest = { },
            //확인 버튼으로만 닫히도록
            properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = false
            ),
            title = {
                Text(
                    text = "탈퇴완료",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "정상적으로 회원 탈퇴 처리가 완료되었습니다.\n그동안 저희 서비스를 이용해 주셔서 진심으로 감사합니다.",
                    lineHeight = 20.sp
                )
            },
            confirmButton = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            userPreferences.deleteUser()

                            showDeleteCompleteDialog = false
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) {
                                    inclusive = true
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF858677)
                        ),
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        Text(
                            text = "확인",
                            color = Color.White
                        )
                    }
                }
            }
        )
    }
}