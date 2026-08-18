package com.example.todoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.dto.ChangePhoneRequest
import com.example.todoapp.repository.UserRepository
import kotlinx.coroutines.launch

class ChangePhoneNumberViewModel : ViewModel() {
    private val repository = UserRepository()

    var currentPhone by mutableStateOf("")
        private set

    var phone by mutableStateOf("")
        private set

    var phoneCheckMessage by mutableStateOf("")
        private set

    fun onPhoneChanged(value: String) {
        phone = value
        phoneCheckMessage = ""
    }

    fun changePhone(
        userId: Long,
        onSuccess: () -> Unit
    ) {
        if (phone.isBlank()) {
            phoneCheckMessage = "휴대폰번호를 입력해주세요"
            return
        }
        if (phone == currentPhone) {
            phoneCheckMessage = "현재 휴대폰번호와 동일합니다"
            return
        }

        viewModelScope.launch {
            val response = repository.changePhone(
                userId,
                ChangePhoneRequest(phone)
            )

            println("휴대폰번호 변경 응답 코드: ${response.code()}")
            println("휴대폰번호 변경 응답 메시지: ${response.message()}")

            if (response.isSuccessful) {
                val body = response.body()

                println("휴대폰번호 변경 response body: $body")

                if (body?.success == true) {
                    onSuccess()
                } else {
                    phoneCheckMessage =
                        body?.message ?: "휴대폰번호 변경에 실패했습니다"
                }
            } else {
                println("휴대폰번호 변경 실패 errorBody: ${response.errorBody()?.string()}")

                phoneCheckMessage = "휴대폰번호 변경에 실패했습니다"
            }
        }
    }

    fun loadUser(userId: Long) {
        viewModelScope.launch {
            val response = repository.getUser(userId)

            if (response.isSuccessful) {
                val body = response.body()

                if (body?.success == true) {
                    currentPhone = body.data?.phone ?: ""
                } else {
                    phoneCheckMessage =
                        body?.message ?: "회원정보를 불러오지 못했습니다"
                }
            } else {
                phoneCheckMessage = "회원정보를 불러오지 못했습니다"
            }
        }
    }
}