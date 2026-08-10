package com.example.todoapp.repository

import com.example.todoapp.api.UserApi
import com.example.todoapp.dto.LoginRequest
import com.example.todoapp.dto.SignupRequest
import com.example.todoapp.network.RetrofitClient

class UserRepository {

    private val userApi = RetrofitClient.retrofit.create(UserApi::class.java)

    suspend fun signup(
        request: SignupRequest
    ) = userApi.signup(request)

    suspend fun login(
        request: LoginRequest
    ) = userApi.login(request)

    suspend fun checkLoginId(
        loginId: String
    ) = userApi.checkLoginId(loginId)

    suspend fun checkNickname(
        nickname: String
    ) = userApi.checkNickname(nickname)

    suspend fun getUser(
        id: Long
    ) = userApi.getUser(id)
}