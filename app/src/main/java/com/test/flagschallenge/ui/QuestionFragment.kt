package com.test.flagschallenge.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.test.flagschallenge.R
import com.test.flagschallenge.databinding.FragmentQuestionBinding
import com.test.flagschallenge.model.Question
import com.test.flagschallenge.ui.base.BaseFragment

class QuestionFragment : BaseFragment() {

    private lateinit var binding: FragmentQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentQuestionBinding.inflate(layoutInflater,container,false)
        getViewModel().timerVisible.value = true
        setQuestionToUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestFocusButton()
    }


    private fun requestFocusButton(){
        binding.button.requestFocus()
        binding.button2.requestFocus()
        binding.button3.requestFocus()
        binding.button3.requestFocus()
    }

    private fun setQuestionToUI(){
        val qstn = getViewModel().getQuestionsForCurrentTime(requireContext())
        if (qstn != null) {
            setUI(qstn.question)
            startTimer(qstn.questionRemainingTime,qstn.question)
        }else{
            getViewModel().currentPage.value = GameOverFragment()
        }
    }


    private fun setUI(question: Question){

        clearAllStrokeColor()

        binding.tvQNumber.text = "${question.questionNumber}"
        binding.imgFlag.setImageResource(getViewModel().getImageResForCountry(question.countryCode))
        binding.button.text = question.countries[0].countryName
        binding.button2.text = question.countries[1].countryName
        binding.button3.text = question.countries[2].countryName
        binding.button4.text = question.countries[3].countryName

        binding.button.setOnClickListener { clickedOption(1,question) }
        binding.button2.setOnClickListener { clickedOption(2,question) }
        binding.button3.setOnClickListener { clickedOption(3,question) }
        binding.button4.setOnClickListener { clickedOption(4,question) }

        setButtonsClickable(true)
        getViewModel().isOnAnswerUI = false
    }

    private fun clearAllStrokeColor(){
        val color = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.btn_text_clr_holo))
        val colorBg = ContextCompat.getColor(requireContext(),android.R.color.transparent)
        binding.button.strokeColor = color
        binding.button2.strokeColor = color
        binding.button3.strokeColor = color
        binding.button4.strokeColor = color
        binding.button.setBackgroundColor(colorBg)
        binding.button2.setBackgroundColor(colorBg)
        binding.button3.setBackgroundColor(colorBg)
        binding.button4.setBackgroundColor(colorBg)
        binding.button.setTextColor(color)
        binding.button2.setTextColor(color)
        binding.button3.setTextColor(color)
        binding.button4.setTextColor(color)
        binding.tvResult1.visibility = View.INVISIBLE
        binding.tvResult2.visibility = View.INVISIBLE
        binding.tvResult3.visibility = View.INVISIBLE
        binding.tvResult4.visibility = View.INVISIBLE
    }

    private fun setButtonSuccess(button:MaterialButton,textView: TextView){
        button.strokeColor = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.btn_stroke_green_success))
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.btn_text_clr_holo))
        textView.visibility = View.VISIBLE
        textView.text = requireContext().resources.getText(R.string.correct)
        textView.setTextColor(ContextCompat.getColor(requireContext(),R.color.btn_stroke_green_success))
    }

    private fun setButtonError(button:MaterialButton,textView: TextView){
        button.strokeColor = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
        button.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.colorAccent))
        button.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        textView.visibility = View.VISIBLE
        textView.text = requireContext().resources.getText(R.string.wrong)
        textView.setTextColor(ContextCompat.getColor(requireContext(),R.color.colorAccent))
    }

    private fun clickedOption(option: Int,question: Question){
        closeTimer()
        clearAllStrokeColor()
        setButtonsClickable(false)
        val btnClicked = getButtonForOption(option)
        if(question.countries[option - 1].id == question.answerId){
            setButtonSuccess(btnClicked.first,btnClicked.second)
            answeredCorrectly(question.questionNumber)
        }else{
            setButtonError(btnClicked.first,btnClicked.second)
            val btnSuccess = getButtonForOption(getCorrectOption(question))
            setButtonSuccess(btnSuccess.first,btnSuccess.second)
        }
        answered(question.questionNumber)
    }

    private fun setAnswerUI(question: Question){
        if (!getViewModel().isOnAnswerUI){
            getViewModel().isOnAnswerUI = true
            clearAllStrokeColor()
            setButtonsClickable(false)
            val correctOption = question.countries.indexOfFirst { it.id == question.answerId } + 1
            val btn = getButtonForOption(correctOption)
            setButtonSuccess(btn.first,btn.second)
        }
    }

    private fun getCorrectOption(question: Question):Int{
        return question.countries.indexOfFirst { it.id == question.answerId } + 1
    }

    private fun getButtonForOption(option: Int):Pair<MaterialButton,TextView>{
        return when(option){
            1 -> {
                Pair(binding.button,binding.tvResult1)
            }
            2 -> {
                Pair(binding.button2,binding.tvResult2)
            }
            3 -> {
                Pair(binding.button3,binding.tvResult3)
            }
            else -> {
                Pair(binding.button4,binding.tvResult4)
            }
        }
    }

    private fun startTimer(time:Long,question: Question){
        getViewModel().currentTimerValue = 0
        getDefaultTimer(time){
            val remainingTime = it / 1000
            getViewModel().currentTimerValue = remainingTime
            if (remainingTime >= 10){
                getViewModel().questionTimerValue.value = remainingTime - 10
            }else if(remainingTime in  1..9){
                setAnswerUI(question)
                getViewModel().questionTimerValue.value = remainingTime
            }else {
                closeTimer()
                goToNextQuestion(question.questionNumber)
            }
        }.start()
    }

    private fun answeredCorrectly(questionNumber: Int){
        getViewModel().rightAnswers += 1
    }

    private fun setButtonsClickable(isClickable:Boolean){
        if (getViewModel().isOptionClickable != isClickable){
            getViewModel().isOptionClickable = isClickable
            binding.button.isClickable = isClickable
            binding.button2.isClickable = isClickable
            binding.button3.isClickable = isClickable
            binding.button4.isClickable = isClickable
        }
    }

    private fun goToNextQuestion(questionNumber:Int){
        closeTimer()
        getViewModel().updateTimeForQuestion(questionNumber,requireContext())
        setQuestionToUI()
    }

    private fun answered(questionNumber: Int){
        getViewModel().currentTimerValue = 0
        getDefaultTimer(10000){
            Log.e("TAG", "answered_timer: $it" )
            if (it < 1000L){
                closeTimer()
                goToNextQuestion(questionNumber)
            }else{
                getViewModel().currentTimerValue = it
                getViewModel().questionTimerValue.value = it / 1000
            }
        }.start()
    }

    override fun onResume() {
        setQuestionToUI()
        super.onResume()
    }

}