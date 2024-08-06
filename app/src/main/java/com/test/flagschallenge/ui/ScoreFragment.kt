package com.test.flagschallenge.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.flagschallenge.databinding.FragmentScoreBinding
import com.test.flagschallenge.provider.DataProvider
import com.test.flagschallenge.ui.base.BaseFragment

class ScoreFragment : BaseFragment() {

    private lateinit var binding: FragmentScoreBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        getViewModel().timerVisible.value = false
        getViewModel().updateStartTime(requireContext(),0)
        getViewModel().clearValues()
        binding = FragmentScoreBinding.inflate(layoutInflater,container,false)
        val totalQuestions = DataProvider.getQuestionData(requireContext()).size
        if (totalQuestions > 0){
            binding.tvScore.text = "${((getViewModel().rightAnswers.toFloat() / totalQuestions.toFloat()) * 100).toInt()}/100"
        }else{
            binding.tvScore.text = "0/100"
        }
        return binding.root
    }

}