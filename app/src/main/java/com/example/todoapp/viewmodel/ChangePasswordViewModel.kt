package com.example.todoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.dto.ApiResponse
import com.example.todoapp.dto.ChangePasswordRequest
import com.example.todoapp.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class ChangePasswordViewModel : ViewModel() {

    private val repository = UserRepository()

    var currentPassword by mutableStateOf("")
        private set

    var newPassword by mutableStateOf("")
        private set

    var newPasswordConfirm by mutableStateOf("")
        private set

    var passwordCheckMessage by mutableStateOf("")
        private set

    var currentPasswordError by mutableStateOf("")
        private set

    fun onCurrentPasswordChanged(value: String) {
        currentPassword = value
        currentPasswordError = ""
        passwordCheckMessage = ""
    }

    fun onNewPasswordChanged(value: String) {
        newPassword = value
        passwordCheckMessage = ""
    }

    fun onNewPasswordConfirmChanged(value: String) {
        newPasswordConfirm = value
        passwordCheckMessage = ""
    }

    fun changePassword(
        userId: Long,
        onSuccess: () -> Unit
    ) {

        if (currentPassword.isBlank()) {
            passwordCheckMessage = "현재 비밀번호를 입력해주세요"
            return
        }

        if (newPassword.isBlank()) {
            passwordCheckMessage = "새 비밀번호를 입력해주세요"
            return
        }

        if (newPasswordConfirm.isBlank()) {
            passwordCheckMessage = "새 비밀번호 확인을 입력해주세요"
            return
        }

        if (newPassword != newPasswordConfirm) {
            passwordCheckMessage = "새 비밀번호가 일치하지 않습니다"
            return
        }

        if (currentPassword == newPassword) {
            passwordCheckMessage = "현재 비밀번호와 동일합니다"
            return
        }

        viewModelScope.launch {
            val response = repository.changePassword(
                userId,
                ChangePasswordRequest(
                    currentPassword = currentPassword,
                    newPassword = newPassword
                )
            )
            println("비밀번호 변경 응답 코드: ${response.code()}")
            println("비밀번호 변경 응답 메시지: ${response.message()}")

            if (response.isSuccessful) {
                val body = response.body()

                println("비밀번호 변경 response body: $body")

                if (body?.success == true) {
                    onSuccess()
                } else {
                    passwordCheckMessage = body?.message ?: "비밀번호 변경에 실패했습니다"
                }
            } else {
                // 서버에서 전달한 에러 응답
                val errorBody = response.errorBody()?.string()

                println("비밀번호 변경 실패 errorBody: $errorBody")

                try {
                    val errorResponse = Gson().fromJson(
                        errorBody,
                        ApiResponse::class.java
                    )
                    val message = errorResponse?.message

                    if (!message.isNullOrBlank()) {
                        currentPasswordError = message
                    } else {
                        passwordCheckMessage = "비밀번호 변경에 실패했습니다"
                    }
                } catch (e: Exception) {
                    println("에러 응답 파싱 실패: ${e.message}")
                    passwordCheckMessage = "비밀번호 변경에 실패했습니다"
                }
            }
        }
    }
}