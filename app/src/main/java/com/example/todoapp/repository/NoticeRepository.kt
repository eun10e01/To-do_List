package com.example.todoapp.repository

import com.example.todoapp.api.NoticeApi
import com.example.todoapp.dto.NoticeResponse
import com.example.todoapp.network.RetrofitClient
import kotlin.jvm.java

class NoticeRepository
{
    private val noticeApi =
        RetrofitClient.retrofit.create(NoticeApi::class.java)

    suspend fun getNotices(
    ) = noticeApi.getNotices()
}