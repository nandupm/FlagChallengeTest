package com.test.flagschallenge.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.test.flagschallenge.ui.viewmodel.FlagsChallengeVModel

open class BaseFragment : Fragment() {

    private lateinit var viewModel: FlagsChallengeVModel
    private var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(
            requireActivity()
        )[FlagsChallengeVModel::class.java]
        return null
    }

    protected fun getViewModel():FlagsChallengeVModel{
        return viewModel
    }


    protected fun changeFragment(fragment: BaseFragment){
        viewModel.currentPage.value = fragment
    }

    protected fun getDefaultTimer(timeInMillis:Long,onTick:(p0:Long)->Unit):CountDownTimer{
        timer?.cancel()
        timer = object : CountDownTimer(timeInMillis,1000){
            @SuppressLint("SetTextI18n")
            override fun onTick(p0: Long) {
                onTick.invoke(p0)
            }

            override fun onFinish() {

            }
        }
        return timer!!
    }

    protected fun closeTimer(){
        getViewModel().questionTimerValue.value = 0
        timer?.cancel()
    }

    override fun onPause() {
        closeTimer()
        super.onPause()
    }

}