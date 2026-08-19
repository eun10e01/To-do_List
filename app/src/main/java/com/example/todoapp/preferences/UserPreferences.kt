package com.example.todoapp.preferences

import android.content.Context

class UserPreferences(context: Context) {

    private val preferences = context.getSharedPreferences(
        "user_preferences",
        Context.MODE_PRIVATE
    )

    //로그인한 사용자 고유ID 저장
    fun saveUserId(userId: Long) {
        preferences.edit()
            .putLong("user_id", userId)
            .apply()
    }

    //저장된 사용자 고유ID 가져오기
    fun getUserId(): Long {
        return preferences.getLong("user_id", -1L)
    }

    //로그인 여부 확인
    fun isLoggedIn(): Boolean {
        return getUserId() != -1L
    }

    //로그아웃 시 사용자 정보 삭제
    fun logout() {
        preferences.edit()
            .remove("user_id")
            .apply()
    }

    //회원 탈퇴 시 사용자 정보 삭제
    fun deleteUser() {
        preferences.edit()
            .remove("user_id")
            .apply()
    }
}