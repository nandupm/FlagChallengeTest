package com.test.flagschallenge.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.test.flagschallenge.R
import com.test.flagschallenge.databinding.ActivityFlagsChallengeBinding
import com.test.flagschallenge.exts.toTwoDigitString
import com.test.flagschallenge.ui.base.BaseActivity
import com.test.flagschallenge.ui.base.FCViewModelFactory
import com.test.flagschallenge.ui.viewmodel.FlagsChallengeVModel
import java.util.Date

class FlagsChallengeActivity : BaseActivity(){

    private lateinit var binding:ActivityFlagsChallengeBinding
    private lateinit var viewModel:FlagsChallengeVModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlagsChallengeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar.root, "")

        viewModel = ViewModelProvider(
            this,
            FCViewModelFactory{
                FlagsChallengeVModel()
            }
        )[FlagsChallengeVModel::class.java]

        viewModel.currentPage.observe(this){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, it)
                .commit()
        }

        viewModel.timerVisible.observe(this){
            binding.vwTimer.visibility = if (it){
                View.VISIBLE
            }else{
                View.GONE
            }
        }

        viewModel.questionTimerValue.observe(this){
            binding.tvTimerQuestion.text = "00:${it.toTwoDigitString()}"
        }

        viewModel.timerVisible.value = false
        viewModel.questionTimerValue.value = 0
        val startedTime = viewModel.getStartedTime(this)
        viewModel.currentPage.value = if(startedTime == 0L){
            SetTimeFragment()
        }else if (Date().before(Date(startedTime))){
            TimerFragment()
        }else{
            QuestionFragment()
        }

    }

}