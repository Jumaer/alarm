package com.example.myapplication.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.myapplication.remind.Reminds

class BootBroadcast : BroadcastReceiver() {
    override fun onReceive(context : Context?, intent : Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            // Set the alarm here.
            context?.let { Reminds.startReminder(it) }
        }
    }
}