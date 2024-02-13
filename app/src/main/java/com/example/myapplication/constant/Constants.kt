package com.example.myapplication.constant

import android.os.Build
import androidx.annotation.RequiresApi

object Constants {

    const val CHANEL_ID = "alarmChanelId"
    const val CHANEL_NAME = "alarmChanelName"
    const val NOTIFICATION_ID = 123
    const val openComplete = "Open Complete"

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val PERMISSIONS = listOf<String>(
        android.Manifest.permission.POST_NOTIFICATIONS,
        android.Manifest.permission.SCHEDULE_EXACT_ALARM
    )
}