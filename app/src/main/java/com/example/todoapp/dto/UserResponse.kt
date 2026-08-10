package com.example.todoapp.dto

data class UserResponse (
    val id: Long,
    val loginId: String,
    val name: String,
    val nickname: String,
    val email: String,
    val phone: String,
    val birth: String
)