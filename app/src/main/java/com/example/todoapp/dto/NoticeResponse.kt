package com.example.todoapp.dto

data class NoticeResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: String
)