package com.example.todoapp.api

import com.example.todoapp.dto.NoticeResponse
import retrofit2.Response
import retrofit2.http.GET

interface NoticeApi {
    @GET("api/notices")
    suspend fun getNotices(
    ): Response<List<NoticeResponse>>
}