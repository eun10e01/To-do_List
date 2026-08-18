package com.example.todoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.dto.LoginRequest
import com.example.todoapp.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = UserRepository()

    var loginId by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf("")
        private set

    var loginSuccess by mutableStateOf(false)
        private set

    var loggedInUserId by mutableStateOf<Long?>(null)
        private set

    // 아이디 변경
    fun onLoginIdChanged(value: String) {
        loginId = value
    }

    // 비밀번호 변경
    fun onPasswordChanged(value: String) {
        password = value
    }

    // 로그인
    fun login() {
        if (loginId.isBlank()) {
            errorMessage = "아이디를 입력해주세요"
            return
        }

        if (password.isBlank()) {
            errorMessage = "비밀번호를 입력해주세요"
            return
        }

        val request = LoginRequest(
            loginId = loginId,
            password = password
        )

        viewModelScope.launch {
            try {
                val response = repository.login(request)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.success == true) {
                        val user = body.data

                        if (user != null) {
                            loggedInUserId = user.id
                            loginSuccess = true
                        } else {
                            errorMessage = "로그인 정보가 없습니다"
                        }
                    } else {
                        errorMessage = body?.message ?: "로그인에 실패했습니다"
                    }
                } else {
                    errorMessage = "아이디 또는 비밀번호가 올바르지 않습니다"
                }
            } catch (e: Exception) {
                errorMessage = "서버와의 통신에 실패했습니다"
            }
        }
    }
}