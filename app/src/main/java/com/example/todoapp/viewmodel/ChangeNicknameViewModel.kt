package com.example.todoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.dto.ChangeNicknameRequest
import com.example.todoapp.repository.UserRepository
import kotlinx.coroutines.launch

class ChangeNicknameViewModel : ViewModel() {
    private val repository = UserRepository()

    var currentNickname by mutableStateOf("")
        private set

    var nickname by mutableStateOf("")
        private set

    var nicknameAvailable by mutableStateOf(false)
        private set

    var nicknameCheckMessage by mutableStateOf("")
        private set

    fun onNicknameChanged(value: String) {
        nickname = value
        nicknameAvailable = false
        nicknameCheckMessage = ""
    }

    // 중복확인 버튼
    fun checkNickname() {
        if (nickname.isBlank()) {
            nicknameAvailable = false
            nicknameCheckMessage = "닉네임을 입력해주세요"
            return
        }

        viewModelScope.launch {
            val response = repository.checkNickname(nickname)

            if (response.isSuccessful) {
                val body = response.body()

                if (body?.available == true) {
                    nicknameAvailable = true
                    nicknameCheckMessage =
                        body.message ?: "사용 가능한 닉네임입니다"
                } else {
                    nicknameAvailable = false
                    nicknameCheckMessage =
                        body?.message ?: "이미 존재하는 닉네임입니다"
                }
            } else {
                nicknameAvailable = false
                nicknameCheckMessage = "닉네임 중복확인에 실패했습니다"
            }
        }
    }

    fun loadUser(userId: Long) {
        viewModelScope.launch {
            val response = repository.getUser(userId)

            if (response.isSuccessful) {
                val body = response.body()

                if (body?.success == true) {
                    currentNickname = body.data?.nickname ?: ""
                } else {
                    nicknameCheckMessage =
                        body?.message ?: "회원정보를 불러오지 못했습니다"
                }
            } else {
                nicknameCheckMessage = "회원정보를 불러오지 못했습니다"
            }
        }
    }

    // 닉네임 변경하기 버튼
    fun changeNickname(
        userId: Long,
        onSuccess: () -> Unit
    ) {
        if (nickname.isBlank()) {
            nicknameCheckMessage = "닉네임을 입력해주세요"
            nicknameAvailable = false
            return
        }

        if (nickname == currentNickname) {
            nicknameCheckMessage = "현재 닉네임과 동일합니다"
            nicknameAvailable = false
            return
        }

        if (!nicknameAvailable) {
            nicknameCheckMessage = "중복확인을 해주세요"
            return
        }

        viewModelScope.launch {
            val response = repository.changeNickname(
                userId,
                ChangeNicknameRequest(nickname)
            )

            println("닉네임 변경 응답 코드: ${response.code()}")
            println("닉네임 변경 응답 메시지: ${response.message()}")

            if (response.isSuccessful) {
                val body = response.body()
                println("닉네임 변경 response body: $body")

                if (body?.success == true) {
                    onSuccess()
                } else {
                    nicknameCheckMessage =
                        body?.message ?: "닉네임 변경에 실패했습니다"
                }
            } else {
                println("닉네임 변경 실패 errorBody: ${response.errorBody()?.string()}")
                nicknameCheckMessage = "닉네임 변경에 실패했습니다"
            }
        }
    }
}