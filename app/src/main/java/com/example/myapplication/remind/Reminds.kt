package com.example.myapplication.remind

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.myapplication.broadcast.AlarmBroadCast
import com.example.myapplication.constant.Constants.NOTIFICATION_ID

object Reminds {




    fun startReminder(
        context: Context,
        reminderId: Int = NOTIFICATION_ID
    ) {
        val alarmTimeMillis: Long = SharedPrefUtil(context).getAlarmTime()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager



        val intent =
            Intent(context.applicationContext, AlarmBroadCast::class.java).let { intent ->
                PendingIntent.getBroadcast(
                    context.applicationContext,
                    reminderId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                )
            }


        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(alarmTimeMillis, intent),
            intent
        )
    }

    fun stopReminder(
        context: Context,
        reminderId: Int = NOTIFICATION_ID
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadCast::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                reminderId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        }
        alarmManager.cancel(intent)
    }
}