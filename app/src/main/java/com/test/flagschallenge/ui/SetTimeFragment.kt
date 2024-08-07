package com.test.flagschallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.test.flagschallenge.databinding.FragmentSetTimeBinding
import com.test.flagschallenge.ui.base.BaseFragment

class SetTimeFragment : BaseFragment() {

    private lateinit var binding:FragmentSetTimeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSetTimeBinding.inflate(
            inflater,container,false
        )

        binding.btnSave.setOnClickListener {
            val timePicked = binding.timePickerView.getTime()
            if (timePicked != null){
                val time = getViewModel().getTimeInMillis(timePicked.hour,timePicked.minute,timePicked.second)
                if (time > 0 && getViewModel().isAfterNow(time)){
                    getViewModel().updateStartTime(requireContext(),time)
                    changeFragment(TimerFragment())
                    return@setOnClickListener
                }else{
                    Toast.makeText(requireContext(), "Past time entered..", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            Toast.makeText(requireContext(), "Wrong time entered..", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}