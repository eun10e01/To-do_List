package com.example.todoapp.repository

import com.example.todoapp.api.UserApi
import com.example.todoapp.dto.SignupRequest
import com.example.todoapp.network.RetrofitClient

class UserRepository {

    private val userApi = RetrofitClient.retrofit.create(UserApi::class.java)

    suspend fun signup(
        request: SignupRequest
    ) = userApi.signup(request)
}