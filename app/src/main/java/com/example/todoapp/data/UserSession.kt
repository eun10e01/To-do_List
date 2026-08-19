package com.example.todoapp.data

object UserSession{
    var currentUserId: Long? = null

    fun setSession(userId: Long){
        this.currentUserId = userId
    }

    fun clearSession(){
        this.currentUserId = null
    }
}