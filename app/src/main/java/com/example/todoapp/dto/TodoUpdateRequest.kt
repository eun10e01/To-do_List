package com.example.todoapp.dto

data class TodoUpdateRequest(
    val title: String,
    val time: String? = null,
    val startDate: String, // yyyy-MM-dd
    val endDate: String,   // yyyy-MM-dd
    val type: String,
    val isRepeat: Boolean = false,
    val recurrenceType: String? = null,
    val recurrenceEndDate: String? = null
)