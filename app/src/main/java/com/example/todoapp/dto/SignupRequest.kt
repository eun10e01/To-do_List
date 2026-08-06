package com.example.todoapp.dto

data class SignupRequest(
    val loginId: String,
    val password: String,
    val name: String,
    val nickname: String,
    val email: String,
    val phone: String,
    val birth: String
)
