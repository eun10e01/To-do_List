package com.example.todoapp.dto

data class TodoUpdateRequest(
    val title: String,
    val time: String? = null,
    val startDate: String,
    val endDate: String,
    val type: String,
    val isRepeat: Boolean = false,
    val recurrenceType: String? = null,
    val recurrenceEndDate: String? = null
)