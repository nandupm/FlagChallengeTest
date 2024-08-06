package com.test.flagschallenge.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.flagschallenge.databinding.FragmentGameOverBinding
import com.test.flagschallenge.ui.base.BaseFragment

class GameOverFragment : BaseFragment() {

    private var handler:Handler? = null
    private val handlerCallBack = {getViewModel().currentPage.value = ScoreFragment()}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding =FragmentGameOverBinding.inflate(layoutInflater,container,false)
        getViewModel().timerVisible.value = false
        Looper.myLooper()?.let {
            Handler(it).postDelayed(
                handlerCallBack,
                2000)
        }
        return binding.root
    }

    override fun onDestroy() {
        handler?.removeCallbacks(handlerCallBack)
        super.onDestroy()
    }

}