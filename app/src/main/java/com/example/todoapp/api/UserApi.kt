package com.example.todoapp.api

import com.example.todoapp.dto.ApiResponse
import com.example.todoapp.dto.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("users/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): Response<ApiResponse<Unit>>
}