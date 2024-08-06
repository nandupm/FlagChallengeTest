package com.test.flagschallenge.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.test.flagschallenge.R
import com.test.flagschallenge.databinding.FragmentTimerBinding
import com.test.flagschallenge.exts.toTwoDigitString
import com.test.flagschallenge.ui.base.BaseFragment
import java.util.Calendar
import java.util.Date

class TimerFragment : BaseFragment(){

    companion object{
        private const val MAX_TIMER_VALUE = 20000L
    }

    private lateinit var binding:FragmentTimerBinding
    private var handler: Handler? = null
    private var handlerCallback = Runnable {
        binding.tv2.text = requireContext().resources.getString(R.string.will_start_in)
        startTimer(MAX_TIMER_VALUE)
    }


    override fun onResume() {
        super.onResume()
        checkAndStartTimer()
    }

    @SuppressLint("SetTextI18n")
    private fun checkAndStartTimer(){
        val startTime = getViewModel().getStartedTime(requireContext())
        val cal = Calendar.getInstance()
        cal.timeInMillis = startTime
        val timeDiff = (startTime - Date().time)
        if (timeDiff in 1..MAX_TIMER_VALUE){
            binding.tv2.text = requireContext().resources.getString(R.string.will_start_in)
            startTimer(timeDiff)
        }else if (timeDiff <= 0){
            getViewModel().currentPage.value = QuestionFragment()
        }else{
            binding.tv2.text = requireContext().resources.getString(R.string.will_start_at)
            binding.tvTimer.text = "${cal.get(Calendar.HOUR_OF_DAY)}:${cal.get(Calendar.MINUTE)}:${cal.get(Calendar.SECOND)}"
            handler = Looper.myLooper()?.let { Handler(it) }
            handler?.postDelayed(handlerCallback,timeDiff - MAX_TIMER_VALUE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTimerBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onDestroy() {
        handler?.removeCallbacks(handlerCallback)
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    private fun startTimer(time:Long){
        getDefaultTimer(time){
            val balanceSec = (it) / 1000
            binding.tvTimer.text = "00:${balanceSec.toTwoDigitString()}"
            if (balanceSec <= 0){
                closeTimer()
                changeFragment(QuestionFragment())
            }
        }.start()
    }

}