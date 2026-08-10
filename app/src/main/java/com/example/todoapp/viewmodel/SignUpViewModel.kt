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

    var signupSuccess by mutableStateOf(false)
        private set

    var loginIdCheckMessage by mutableStateOf("")
        private set

    var nicknameCheckMessage by mutableStateOf("")
        private set

    var passwordCheckMessage by mutableStateOf("")
        private set

    var passwordMatched by mutableStateOf(false)
        private set

    var loginIdAvailable by mutableStateOf(false)
        private set

    var nicknameAvailable by mutableStateOf(false)
        private set

    // 값 변경 함수
    fun onLoginIdChanged(value: String) {
        loginId = value

        loginIdAvailable = false
        loginIdCheckMessage = ""
    }
    fun onPasswordChanged(value: String) {
        password = value

        if (passwordCheck.isNotEmpty()) {
            passwordMatched = password == passwordCheck

            passwordCheckMessage =
                if (passwordMatched)
                    "비밀번호가 일치합니다"
                else
                    "비밀번호가 일치하지 않습니다"
        }
    }

    fun onPasswordCheckChanged(value: String) {
        passwordCheck = value

        if (passwordCheck.isEmpty()) {
            passwordCheckMessage = ""
            passwordMatched = false
        } else {
            passwordMatched = password == passwordCheck

            passwordCheckMessage =
                if (passwordMatched)
                    "비밀번호가 일치합니다"
                else
                    "비밀번호가 일치하지 않습니다"
        }
    }

    fun onNameChanged(value: String) {
        name = value
    }

    fun onNicknameChanged(value: String) {
        nickname = value

        nicknameAvailable = false
        nicknameCheckMessage = ""
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

        if (!loginIdAvailable) {
            errorMessage = "아이디 중복확인을 해주세요"
            return
        }

        if (!nicknameAvailable) {
            errorMessage = "닉네임 중복확인을 해주세요"
            return
        }

        if (password.isEmpty() || passwordCheck.isEmpty()) {
            passwordCheckMessage = "비밀번호를 입력해주세요"
            return
        }

        if (!passwordMatched) {
            passwordCheckMessage = "비밀번호가 일치하지 않습니다"
            return
        }

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

//            println("회원가입 응답 코드: ${response.code()}")
//            println("회원가입 응답 메시지: ${response.message()}")

            if(response.isSuccessful) {
                val body = response.body()

//                println("회원가입 응답 body: $body")

                if(body?.success == true) {
                    // 회원가입 성공
                    signupSuccess = true
                }
                else {
                    errorMessage = body?.message ?: "회원가입에 실패했습니다"
                }
            }
            else {
//                println("회원가입 실패 body: ${response.errorBody()?.string()}")
                errorMessage = "서버와의 통신에 실패했습니다.="
            }
        }
    }

    fun checkLoginId() {

        if(loginId.isBlank()) {
            loginIdCheckMessage = "아이디를 입력해주세요"
            loginIdAvailable = false
            return
        }

        viewModelScope.launch {
            val response = repository.checkLoginId(loginId)

            if(response.isSuccessful) {
                response.body()?.let {
                    loginIdAvailable = it.available
                    loginIdCheckMessage = it.message

                }
            }
            else {
                loginIdAvailable = false
                loginIdCheckMessage = "중복확인에 실패했습니다"
            }
        }
    }

    fun checkNickname() {

        if(nickname.isBlank()) {
            nicknameCheckMessage = "닉네임을 입력해주세요."
            nicknameAvailable = false
            return
        }

        viewModelScope.launch {
            val response = repository.checkNickname(nickname)

            if(response.isSuccessful) {
                response.body()?.let {
                    nicknameAvailable = it.available
                    nicknameCheckMessage = it.message
                }
            }
            else {
                nicknameAvailable = false
                nicknameCheckMessage = "중복확인에 실패했습니다"
            }
        }
    }
}