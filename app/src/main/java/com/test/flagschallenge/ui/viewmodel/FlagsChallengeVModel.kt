package com.test.flagschallenge.ui.viewmodel

import android.content.Context
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.flagschallenge.R
import com.test.flagschallenge.exts.getPreference
import com.test.flagschallenge.model.Question
import com.test.flagschallenge.model.QuestionListData
import com.test.flagschallenge.provider.DataProvider
import java.util.Calendar
import java.util.Date

class FlagsChallengeVModel : ViewModel() {


    val currentPage = MutableLiveData<Fragment>()
    val timerVisible = MutableLiveData<Boolean>()
    val questionTimerValue = MutableLiveData<Long>()

    var isOptionClickable = false
    var isOnAnswerUI = false

    companion object{
        const val QUESTION_TIMER = 30000L
        const val INTERVAL_TIMER = 10000L
    }

    var rightAnswers = 0

    var currentTimerValue = 0L

    fun getTimeInMillis(hr:Int,min:Int,sec:Int):Long{
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hr)
        cal.set(Calendar.MINUTE, min)
        cal.set(Calendar.SECOND, sec)
        return if (cal.time.after(Date())){
            cal.timeInMillis
        }else -1
    }

    /*fun validateTime(hr:String,min:String,sec:String):Long{
        return if(
            hr.isNotEmpty() && min.isNotEmpty() && sec.isNotEmpty()
            &&
            hr.isDigitsOnly() && min.isDigitsOnly() && sec.isDigitsOnly()
            &&
            hr.toInt()<=24 && min.toInt()<=60 && sec.toInt()<=60
        ){
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY,hr.toInt())
            cal.set(Calendar.MINUTE,min.toInt())
            cal.set(Calendar.SECOND,sec.toInt())
            if (cal.time.after(Date())){
                cal.timeInMillis
            }else -1
        } else -1
    }*/

    fun isAfterNow(timeInMillis:Long):Boolean{
        return Date(timeInMillis).after(Date())
    }

    fun getQuestionsForCurrentTime(context: Context):QuestionListData?{
        val time = getStartedTime(context)
        val curTime = Date().time
        val timeDif = (curTime) - Date(time).time
        val qNi = (timeDif.toDouble() / (QUESTION_TIMER + INTERVAL_TIMER).toDouble()).toInt()
        var remTime = timeDif - ((qNi)*(QUESTION_TIMER + INTERVAL_TIMER))
        remTime = (QUESTION_TIMER + INTERVAL_TIMER) - remTime
        val totalQns = DataProvider.getQuestionData(context)
        if (qNi+1 > totalQns.size){
            return null
        }
        return QuestionListData(
            totalQns.getOrElse(qNi){totalQns[0]},
            remTime
        )
    }

    fun getStartedTime(context: Context):Long{
        return context.getPreference().getSavedTime()
    }

    fun updateStartTime(context: Context,time:Long){
        context.getPreference().saveTime(time)
    }

    fun clearValues(){
        isOptionClickable = false
        isOnAnswerUI = false
        currentTimerValue = 0L
    }

    fun updateTimeForQuestion(questionNumber:Int,context: Context){
        val startedTime = getStartedTime(context)
        val diff = Date().time - startedTime
        val reqTimeDiff = questionNumber * (QUESTION_TIMER + INTERVAL_TIMER)
        updateStartTime(context,startedTime - (reqTimeDiff - diff))
    }

    fun getImageResForCountry(countryCode:String):Int{
        return when(countryCode){
            "NZ" -> R.drawable.flag_nz
            "AW" -> R.drawable.flag_aw
            "EC" -> R.drawable.flag_ec
            "PY" -> R.drawable.flag_py
            "KG" -> R.drawable.flag_kg
            "PM" -> R.drawable.flag_pm
            "JP" -> R.drawable.flag_jp
            "TM" -> R.drawable.flag_tm
            "GA" -> R.drawable.flag_ga
            "MQ" -> R.drawable.flag_mq
            "BZ" -> R.drawable.flag_bz
            "CZ" -> R.drawable.flag_cz
            "AE" -> R.drawable.flag_ae
            "JE" -> R.drawable.flag_je
            "LS" -> R.drawable.flag_ls
            else -> R.drawable.flag_nz
        }
    }

}