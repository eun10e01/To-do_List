package com.example.todoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.repository.UserRepository
import kotlinx.coroutines.launch

class DeleteAccountViewModel : ViewModel() {
    private val repository = UserRepository()

    var nickname by mutableStateOf("")
        private set

    var deleteCheckMessage by mutableStateOf("")
        private set

    fun deleteAccount(
        userId: Long,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.deleteUser(userId)

                println("회원 탈퇴 응답 코드: ${response.code()}")
                println("회원 탈퇴 응답 메시지: ${response.message()}")

                if (response.isSuccessful) {
                    val body = response.body()

                    println("회원 탈퇴 response body: $body")

                    if (body?.success == true) {
                        onSuccess()
                    } else {
                        deleteCheckMessage = body?.message ?: "회원 탈퇴에 실패했습니다"
                    }
                } else {
                    println("회원 탈퇴 실패 errorBody: ${response.errorBody()?.string()}")
                    deleteCheckMessage = "회원 탈퇴에 실패했습니다"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                deleteCheckMessage = "회원 탈퇴에 실패했습니다"
            }
        }
    }

    // 현재 사용자 정보 불러오기
    fun loadUser(userId: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getUser(userId)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.success == true) {
                        nickname = body.data?.nickname ?: ""
                    } else {
                        deleteCheckMessage = body?.message ?: "회원정보를 불러오지 못했습니다"
                    }
                } else {
                    deleteCheckMessage = "회원정보를 불러오지 못했습니다"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                deleteCheckMessage = "회원정보를 불러오지 못했습니다"
            }
        }
    }
}
