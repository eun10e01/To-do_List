package com.example.todoapp.api

import com.example.todoapp.dto.ApiResponse
import com.example.todoapp.dto.ChangeBirthRequest
import com.example.todoapp.dto.ChangeEmailRequest
import com.example.todoapp.dto.ChangeNicknameRequest
import com.example.todoapp.dto.ChangePasswordRequest
import com.example.todoapp.dto.ChangePhoneRequest
import com.example.todoapp.dto.LoginRequest
import com.example.todoapp.dto.SignupRequest
import com.example.todoapp.dto.UserCheckResponse
import com.example.todoapp.dto.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @POST("users/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): Response<ApiResponse<Unit>>

    @POST("users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<ApiResponse<UserResponse>>

    @DELETE("users/{id}")
    suspend fun deleteUser(
        @Path("id") userId: Long
    ): Response<ApiResponse<Void>>

    @GET("users/check-login-id")
    suspend fun checkLoginId(
        @Query("loginId") loginId: String
    ): Response<UserCheckResponse>

    @GET("users/check-nickname")
    suspend fun checkNickname(
        @Query("nickname") nickname: String
    ): Response<UserCheckResponse>

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: Long
    ): Response<ApiResponse<UserResponse>>

    @PATCH("users/{id}/nickname")
    suspend fun changeNickname(
        @Path("id") id: Long,
        @Body request: ChangeNicknameRequest
    ): Response<ApiResponse<Unit>>

    @PATCH("users/{id}/email")
    suspend fun changeEmail(
        @Path("id") id: Long,
        @Body request: ChangeEmailRequest
    ): Response<ApiResponse<Unit>>

    @PATCH("users/{id}/phone")
    suspend fun changePhone(
        @Path("id") id: Long,
        @Body request: ChangePhoneRequest
    ): Response<ApiResponse<Unit>>

    @PATCH("users/{id}/birth")
    suspend fun changeBirth(
        @Path("id") id: Long,
        @Body request: ChangeBirthRequest
    ): Response<ApiResponse<Unit>>

    @PATCH("users/{id}/password")
    suspend fun changePassword(
        @Path("id") id: Long,
        @Body request: ChangePasswordRequest
    ): Response<ApiResponse<Unit>>
}