package com.example.todoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.dto.ChangeEmailRequest
import com.example.todoapp.repository.UserRepository
import kotlinx.coroutines.launch

class ChangeEmailViewModel : ViewModel() {
    private val repository = UserRepository()

    var currentEmail by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var emailCheckMessage by mutableStateOf("")
        private set

    fun onEmailChanged(value: String) {
        email = value
        emailCheckMessage = ""
    }

    fun changeEmail(
        userId: Long,
        onSuccess: () -> Unit
    ) {
        if (email.isBlank()) {
            emailCheckMessage = "이메일을 입력해주세요"
            return
        }
        if (email == currentEmail) {
            emailCheckMessage = "현재 이메일과 동일합니다"
            return
        }

        viewModelScope.launch {
            val response = repository.changeEmail(
                userId,
                ChangeEmailRequest(email)
            )

            println("이메일 변경 응답 코드: ${response.code()}")
            println("이메일 변경 응답 메시지: ${response.message()}")

            if (response.isSuccessful) {
                val body = response.body()

                println("이메일 변경 response body: $body")

                if (body?.success == true) {
                    onSuccess()
                } else {
                    emailCheckMessage =
                        body?.message ?: "이메일 변경에 실패했습니다"
                }
            } else {
                println("이메일 변경 실패 errorBody: ${response.errorBody()?.string()}")

                emailCheckMessage = "이메일 변경에 실패했습니다"
            }
        }
    }

    fun loadUser(userId: Long) {
        viewModelScope.launch {

            val response = repository.getUser(userId)

            if (response.isSuccessful) {
                val body = response.body()

                if (body?.success == true) {
                    currentEmail = body.data?.email ?: ""
                } else {
                    emailCheckMessage =
                        body?.message ?: "회원정보를 불러오지 못했습니다"
                }
            } else {
                emailCheckMessage = "회원정보를 불러오지 못했습니다"
            }
        }
    }
}