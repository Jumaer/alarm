package com.example.myapplication.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.AlarmActivity
import com.example.myapplication.alarm.ControlAlarm
import com.example.myapplication.databinding.FragmentSetAlarmBinding


class SetAlarmFragment : Fragment() {
    private lateinit var binding : FragmentSetAlarmBinding

    private val pnPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        binding.setAlarm.isEnabled = granted
        binding.cancelAlarm.isEnabled = granted
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetAlarmBinding.inflate(inflater, container, false)
        setActions()
        setPermission()
        return binding.root
    }

    private fun setPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pnPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            binding.setAlarm.isEnabled = true
            binding.cancelAlarm.isEnabled = true
        }
    }

    private fun setActions() {
        binding.apply {
            setAlarm.setOnClickListener {
                setAlarmOnClock()
            }

            alarmClock.setOnTimeChangedListener { _, hour, minute ->
                selectAlarmFromClock(hour,minute)
            }



            cancelAlarm.setOnClickListener {
                context?.let { ControlAlarm(it).cancelAlarm() }
            }
        }
    }

    private var hour = 0
    private var min = 0

    private fun selectAlarmFromClock(hourTime: Int, minute: Int) {
        hour = hourTime
        min = minute
        Log.d("sjk","$hour, $min")
    }

    private fun setAlarmOnClock() {
        (activity as AlarmActivity).createNotificationChanel()
        context?.let { ControlAlarm(it).setAlarmTime(hour, min) }
    }
}