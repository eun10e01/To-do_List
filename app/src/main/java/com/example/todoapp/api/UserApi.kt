package com.example.todoapp.api

import com.example.todoapp.dto.ApiResponse
import com.example.todoapp.dto.LoginRequest
import com.example.todoapp.dto.SignupRequest
import com.example.todoapp.dto.UserCheckResponse
import com.example.todoapp.dto.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
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
}