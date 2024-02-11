package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentImplementAlarmBinding
import com.example.myapplication.databinding.FragmentSetAlarmBinding


class ImplementAlarmFragment : Fragment() {
    private lateinit var binding : FragmentImplementAlarmBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImplementAlarmBinding.inflate(inflater, container, false)
        setViews()
        return binding.root
    }

    private fun setViews() {
        binding.fabSwipe.setOnClickListener {
            findNavController().navigate(R.id.action_implementAlarmFragment_to_setAlarmFragment)
        }
    }
}