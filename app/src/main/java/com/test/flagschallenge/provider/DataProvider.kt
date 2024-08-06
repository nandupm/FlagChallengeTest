package com.test.flagschallenge.provider

import android.content.Context
import com.google.gson.Gson
import com.test.flagschallenge.model.Question
import com.test.flagschallenge.model.QuestionDataModel

object DataProvider {

    private const val JSON_ASSET_NAME = "questions.json"

    private var questions:List<Question>? = null
    fun getQuestionData(context: Context):List<Question>{
        if (questions == null){
            val qData = Gson().fromJson(jsonStringFromAsset(context),QuestionDataModel::class.java)
            questions = qData.questions
            questions!!.onEachIndexed { index, question -> question.questionNumber = index + 1 }
        }
        return questions!!
    }

    private fun jsonStringFromAsset(context:Context):String{
        val file = context.assets.open(JSON_ASSET_NAME)
        val buffer = ByteArray(file.available())
        file.read(buffer)
        file.close()
        return String(buffer,Charsets.UTF_8)
    }

}