package com.example.todoapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.UserSession
import com.example.todoapp.dto.UserResponse
import com.example.todoapp.repository.UserRepository
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {
    private val repository = UserRepository()

    private val currentUserId: Long get() = UserSession.currentUserId ?: 1L

    var user by mutableStateOf<UserResponse?>(null)
        private set
    var errorMessage by mutableStateOf("")
        private set

    fun loadUser(userId: Long) {
        viewModelScope.launch {
            val response = repository.getUser(userId)

            Log.d("USER_TEST", "code = ${response.code()}")

            if (response.isSuccessful) {
                val body = response.body()

                if (body?.success == true) {
                    user = body.data
                } else {
                    errorMessage =
                        body?.message ?: "회원정보를 불러오지 못했습니다"
                }
            } else {
                errorMessage = "회원정보를 불러오지 못했습니다"
            }
        }
    }
}