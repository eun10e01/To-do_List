package com.example.todoapp.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

object TodoAlarmScheduler {
    //알람을 구분하기 위한 고유 ID
    private const val REQUEST_CODE_10 = 100
    private const val REQUEST_CODE_13 = 101
    private const val REQUEST_CODE_17 = 102

    //3개의 알람을 한 번에 예약하는 함수
    fun scheduleAll(context: Context) {

        scheduleAlarm(
            context = context,
            hour = 10,
            minute = 0,
            requestCode = REQUEST_CODE_10
        )

        scheduleAlarm(
            context = context,
            hour = 13,
            minute = 0,
            requestCode = REQUEST_CODE_13
        )

        scheduleAlarm(
            context = context,
            hour = 17,
            minute = 0,
            requestCode = REQUEST_CODE_17
        )
    }

    private fun scheduleAlarm(
        context: Context,
        hour: Int,
        minute: Int,
        requestCode: Int
    ) {

        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        val intent = Intent(
            context,
            TodoAlarmReceiver::class.java
        )

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            //이미 시간이 지났다면 다음 날로 예약
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun scheduleTest(
        context: Context
    ) {
        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        val intent = Intent(
            context,
            TodoAlarmReceiver::class.java
        )

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                999,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        val triggerTime =
            System.currentTimeMillis() + 60_000

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }

    fun cancelAll(context: Context) {

        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        val requestCodes = listOf(
            REQUEST_CODE_10,
            REQUEST_CODE_13,
            REQUEST_CODE_17
        )

        requestCodes.forEach { requestCode ->

            val intent = Intent(
                context,
                TodoAlarmReceiver::class.java
            )

            val pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or
                            PendingIntent.FLAG_IMMUTABLE
                )

            alarmManager.cancel(pendingIntent)

            pendingIntent.cancel()
        }
    }
}