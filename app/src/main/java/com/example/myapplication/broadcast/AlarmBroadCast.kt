package com.example.myapplication.broadcast

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.AlarmActivity
import com.example.myapplication.R
import com.example.myapplication.constant.Constants.CHANEL_ID
import com.example.myapplication.constant.Constants.NOTIFICATION_ID


class AlarmBroadCast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("sjk","wake up")
        val alarmIntent = Intent(context,AlarmActivity::class.java)
        intent?.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK )
        val pendingIntent = PendingIntent.getActivity(context,0,alarmIntent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)


        val nManager =  context?.let { NotificationManagerCompat.from(it) }
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }


        getNotificationBuilder(context,pendingIntent)?.let {
            nManager?.notify(NOTIFICATION_ID,
                it.build())
            notifyWithSound(context)
        }

    }

    private fun notifyWithSound(context: Context) {
        var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        val ringtone = RingtoneManager.getRingtone(context, alarmUri)
        ringtone.play()
    }

    private fun getNotificationBuilder(context: Context?, pendingIntent: PendingIntent?): NotificationCompat.Builder? {
         context?.let {
          return  NotificationCompat.Builder(it,CHANEL_ID).apply {
                setSmallIcon(R.drawable.baseline_access_alarms_24)
                setContentTitle(it.getText(R.string.chanel_alarm_manager_title))
                setContentText(it.getText(R.string.your_alarm_rang_successfully))
                setAutoCancel(true)
                setDefaults(NotificationCompat.DEFAULT_ALL)
                priority = NotificationCompat.PRIORITY_HIGH
                setContentIntent(pendingIntent)
            }
        }
        return null
    }
}