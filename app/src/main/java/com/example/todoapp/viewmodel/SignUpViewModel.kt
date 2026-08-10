package com.example.todoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.todoapp.dto.SignupRequest
import com.example.todoapp.repository.UserRepository

class SignUpViewModel : ViewModel() {

    private val repository = UserRepository()

    var loginId by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var passwordCheck by mutableStateOf("")
        private set

    var name by mutableStateOf("")
        private set

    var nickname by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var phone by mutableStateOf("")
        private set

    var birth by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf("")
        private set

    // 값 변경 함수
    fun onLoginIdChanged(value: String) {
        loginId = value
    }
    fun onPasswordChanged(value: String) {
        password = value
    }

    fun onPasswordCheckChanged(value: String) {
        passwordCheck = value
    }

    fun onNameChanged(value: String) {
        name = value
    }

    fun onNicknameChanged(value: String) {
        nickname = value
    }

    fun onEmailChanged(value: String) {
        email = value
    }

    fun onPhoneChanged(value: String) {
        phone = value
    }

    fun onBirthChanged(value: String) {
        birth = value
    }

    fun clearErrorMessage() {
        errorMessage = ""
    }

    fun signup() {
        val request = SignupRequest(
            loginId = loginId,
            password = password,
            name = name,
            nickname = nickname,
            email = email,
            phone = phone,
            birth = birth
        )

        // ViewModel 안에서 비동기 작업을 실행하기 위한 Coroutine 공간
        viewModelScope.launch {
            val response = repository.signup(request)

            println("회원가입 응답 코드: ${response.code()}")
            println("회원가입 응답 메시지: ${response.message()}")

            if(response.isSuccessful) {
                val body = response.body()

                println("회원가입 응답 body: $body")

                if(body?.success == true) {
                    // 회원가입 성공
                }
                else {
                    errorMessage = body?.message ?: "회원가입에 실패했습니다"
                }
            }
            else {
                println("회원가입 실패 body: ${response.errorBody()?.string()}")
                errorMessage = "서버와의 통신에 실패했습니다.="
            }
        }
    }
}