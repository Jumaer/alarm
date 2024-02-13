package com.example.myapplication.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.myapplication.broadcast.AlarmBroadCast
import java.util.Calendar

class ControlAlarm {

    private var alarmMgr: AlarmManager? = null
    private var alarmIntent: PendingIntent


    constructor(context: Context) {
        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmBroadCast::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                (0..19992).random(),
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }

    fun setAlarmTime(hour: Int, min: Int, sec: Int = 0) {

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, min)
            set(Calendar.SECOND, sec)
        }

        alarmMgr?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )

        Log.d("sjk", "set Alarm")
    }

    fun cancelAlarm() {
        alarmMgr?.cancel(alarmIntent)
    }


}