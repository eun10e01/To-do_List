package com.example.todoapp.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todoapp.R
import com.example.todoapp.pages.main.home.ToDoItemData
import kotlin.collections.joinToString

object NotificationHelper {
    const val CHANNEL_ID = "todo_notification_channel"

    fun createNotificationChannel(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                "Todo 알림",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "오늘의 Todo 목록을 알려주는 알림입니다."
            }

            val notificationManager =
                context.getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(
        context: Context
    ) {

        //Android 13 이상에서 알림 권한 확인
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            context.checkSelfPermission(
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("오늘의 할 일")
            .setContentText("오늘의 Todo 목록을 확인해보세요!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat
            .from(context)
            .notify(1001, notification)
    }

    fun showTodoNotification(
        context: Context,
        todos: List<ToDoItemData>
    ) {

        //Android 13 이상에서 알림 권한 확인
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            context.checkSelfPermission(
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val todoText =
            if (todos.isEmpty()) {
                "남은 할 일이 없습니다."
            } else {
                todos.joinToString("\n") { todo ->
                    "• ${todo.title}"
                }
            }

        val notification =
            NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )
                .setSmallIcon(
                    R.drawable.ic_launcher_foreground
                )
                .setContentTitle("오늘의 할 일")
                .setContentText(
                    if (todos.isEmpty()) {
                        "남은 할 일이 없습니다."
                    } else {
                        "${todos.size}개의 할 일이 남아있습니다."
                    }
                )
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(todoText)
                )
                .setPriority(
                    NotificationCompat.PRIORITY_DEFAULT
                )
                .setAutoCancel(true)
                .build()

        NotificationManagerCompat
            .from(context)
            .notify(
                System.currentTimeMillis().toInt(),
                notification
            )
    }
}