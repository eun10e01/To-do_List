package com.example.todoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.dto.ChangeBirthRequest
import com.example.todoapp.repository.UserRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class ChangeDateOfBirthViewModel : ViewModel() {
    private val repository = UserRepository()

    var currentBirth by mutableStateOf("")
        private set

    var birth by mutableStateOf("")
        private set

    var birthCheckMessage by mutableStateOf("")
        private set

    fun onBirthChanged(value: String) {
        birth = value
        birthCheckMessage = ""
    }

    fun changeBirth(
        userId: Long,
        onSuccess: () -> Unit
    ) {
        if (birth.isBlank()) {
            birthCheckMessage = "생년월일을 입력해주세요"
            return
        }

        val formattedBirth = try {
            LocalDate.parse(
                birth,
                DateTimeFormatter.ofPattern("yyyyMMdd")
            ).toString()
        } catch (e: DateTimeParseException) {
            birthCheckMessage = "올바른 생년월일을 입력해주세요"
            return
        }

        if (formattedBirth == currentBirth) {
            birthCheckMessage = "현재 생년월일과 동일합니다"
            return
        }

        viewModelScope.launch {
            println("변경할 생년월일 String: $birth")
            println("변경할 생년월일 LocalDate: $formattedBirth")

            val response = repository.changeBirth(
                userId,
                ChangeBirthRequest(formattedBirth)
            )
            println("ChangeBirthRequest: ${ChangeBirthRequest(formattedBirth)}")
            println("생년월일 변경 응답 코드: ${response.code()}")
            println("생년월일 변경 응답 메시지: ${response.message()}")

            if (response.isSuccessful) {
                val body = response.body()

                println("생년월일 변경 response body: $body")

                if (body?.success == true) {
                    onSuccess()
                } else {
                    birthCheckMessage =
                        body?.message ?: "생년월일 변경에 실패했습니다"
                }
            } else {
                println("생년월일 변경 실패 errorBody: ${response.errorBody()?.string()}")

                birthCheckMessage = "생년월일 변경에 실패했습니다"
            }
        }
    }

    fun loadUser(userId: Long) {
        viewModelScope.launch {

            val response = repository.getUser(userId)

            if (response.isSuccessful) {
                val body = response.body()

                if (body?.success == true) {
                    currentBirth = body.data?.birth?.toString() ?: ""
                } else {
                    birthCheckMessage =
                        body?.message ?: "회원정보를 불러오지 못했습니다"
                }
            } else {
                birthCheckMessage = "회원정보를 불러오지 못했습니다"
            }
        }
    }
}