package com.example.myapplication.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.AlarmActivity
import com.example.myapplication.alarm.ControlAlarm
import com.example.myapplication.constant.Constants.PERMISSIONS
import com.example.myapplication.databinding.FragmentSetAlarmBinding
import com.example.myapplication.remind.Reminds
import com.example.myapplication.remind.SharedPrefUtil


class SetAlarmFragment : Fragment() {
    private lateinit var binding: FragmentSetAlarmBinding

    private val pnPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { grantedMap ->
        grantedMap.values.forEach {
            binding.setAlarm.isEnabled = it
            binding.cancelAlarm.isEnabled = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetAlarmBinding.inflate(inflater, container, false)
        setActions()
        setNotificationPermission()
        return binding.root
    }

    private fun setNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pnPermissionLauncher.launch(PERMISSIONS.toTypedArray())
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
                selectAlarmFromClock(hour, minute)
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
        Log.d("sjk", "$hour, $min")
    }

    private fun setAlarmOnClock() {
        (activity as AlarmActivity).createNotificationChanel()
        context?.let { ControlAlarm(it).setAlarmTime(hour, min).apply {
            SharedPrefUtil(it).setAlarmTime(this.timeInMillis)

            Reminds.startReminder(it)
        } }
    }
}