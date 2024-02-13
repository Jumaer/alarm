package com.example.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.constant.Constants.CHANEL_ID
import com.example.myapplication.constant.Constants.CHANEL_NAME
import com.example.myapplication.constant.Constants.openComplete
import com.example.myapplication.databinding.ActivityMainBinding

class AlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isNavSet()
        whenCompleteATask()
    }

    private fun whenCompleteATask(){
        if(intent.getStringExtra(openComplete) != null){
            navGraph.apply {
                setStartDestination(R.id.implementAlarmFragment)
            }
        }
    }

    fun createNotificationChanel() {
        val channel = NotificationChannel(CHANEL_ID,CHANEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        channel.description = getString(R.string.chanel_alarm_manager_hint)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        channel.apply {
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            enableLights(true)
            lightColor = Color.GRAY
            enableVibration(true)
        }

        // Register the channel with the system
        val notificationManager = getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(channel)

    }



    private lateinit var navGraph: NavGraph
    private fun isNavSet(): Boolean {

        val navController: NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        navGraph = navController.navInflater.inflate(R.navigation.base_nav)
        navGraph.apply {
            setStartDestination(R.id.setAlarmFragment)
        }
        navController.setGraph(navGraph,intent.extras)

        return true
    }


}