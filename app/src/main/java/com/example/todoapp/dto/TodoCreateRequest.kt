package com.example.todoapp.dto

data class TodoCreateRequest(
    val userId: Long,
    val title: String,
    val time: String?,
    val startDate: String,
    val endDate: String,
    val type: String,
    val isRepeat: Boolean = false,
    val recurrenceType: String? = null,
    val recurrenceEndDate: String
)