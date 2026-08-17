package com.example.todoapp.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.todoapp.network.RetrofitClient
import com.example.todoapp.preferences.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
class TodoAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {

            try {
                // 현재 로그인한 사용자 ID 가져오기
                val userPreferences = UserPreferences(context)
                val userId = userPreferences.getUserId()

                if (userId == -1L) {
                    return@launch
                }

                // 오늘 날짜
                val today =
                    SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.KOREAN
                    ).format(Date())

                // 오늘의 Todo 가져오기
                val todos =
                    RetrofitClient.todoApiService
                        .getTodosByDate(
                            userId = userId,
                            date = today
                        )

                println("===== 오늘의 Todo =====")

                todos.forEach {
                    println(
                        "Todo: ${it.title}, isChecked: ${it.isChecked}"
                    )
                }

                println("======================")

                // 체크하지 않은 Todo만 남김
                val incompleteTodos =
                    todos.filter { !it.isChecked }

                println("===== 미완료 Todo =====")

                incompleteTodos.forEach {
                    println(
                        "미완료: ${it.title}, isChecked: ${it.isChecked}"
                    )
                }

                println("======================")

                // 미완료 Todo를 알림으로 표시
                NotificationHelper.showTodoNotification(
                    context = context,
                    todos = incompleteTodos
                )

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                pendingResult.finish()
            }
        }
    }
}