package com.example.todoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.dto.UserResponse
import com.example.todoapp.repository.UserRepository
import kotlinx.coroutines.launch

class ChangeUserInfoViewModel : ViewModel() {
    private val repository = UserRepository()

    // 현재 로그인한 사용자의 회원정보
    var user by mutableStateOf<UserResponse?>(null)
        private set

    // 회원정보 조회
    fun loadUser(userId: Long) {
        viewModelScope.launch {
            val response = repository.getUser(userId)

            if (response.isSuccessful) {
                val body = response.body()
                if (body?.success == true) {
                    user = body.data
                }
            }
        }
    }
}