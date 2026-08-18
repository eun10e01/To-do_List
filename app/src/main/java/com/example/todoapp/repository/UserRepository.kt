package com.example.todoapp.repository

import com.example.todoapp.api.UserApi
import com.example.todoapp.dto.ChangeBirthRequest
import com.example.todoapp.dto.ChangeEmailRequest
import com.example.todoapp.dto.ChangeNicknameRequest
import com.example.todoapp.dto.ChangePasswordRequest
import com.example.todoapp.dto.LoginRequest
import com.example.todoapp.dto.SignupRequest
import com.example.todoapp.dto.ChangePhoneRequest
import com.example.todoapp.network.RetrofitClient

class UserRepository {

    private val userApi = RetrofitClient.retrofit.create(UserApi::class.java)

    suspend fun signup(
        request: SignupRequest
    ) = userApi.signup(request)

    suspend fun login(
        request: LoginRequest
    ) = userApi.login(request)

    suspend fun deleteUser(
        userId: Long
    ) = userApi.deleteUser(userId)

    suspend fun checkLoginId(
        loginId: String
    ) = userApi.checkLoginId(loginId)

    suspend fun checkNickname(
        nickname: String
    ) = userApi.checkNickname(nickname)

    suspend fun getUser(
        id: Long
    ) = userApi.getUser(id)

    suspend fun changeNickname(
        id: Long,
        request: ChangeNicknameRequest
    ) = userApi.changeNickname(id, request)

    suspend fun changeEmail(
        id: Long,
        request: ChangeEmailRequest
    ) = userApi.changeEmail(id, request)

    suspend fun changePhone(
        id: Long,
        request: ChangePhoneRequest
    ) = userApi.changePhone(id, request)

    suspend fun changeBirth(
        id: Long,
        request: ChangeBirthRequest
    ) = userApi.changeBirth(id, request)

    suspend fun changePassword(
        id: Long,
        request: ChangePasswordRequest
    ) = userApi.changePassword(id, request)
}