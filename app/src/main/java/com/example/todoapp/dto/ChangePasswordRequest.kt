package com.example.todoapp.dto

data class ChangePasswordRequest (
    val currentPassword: String,
    val newPassword: String
)