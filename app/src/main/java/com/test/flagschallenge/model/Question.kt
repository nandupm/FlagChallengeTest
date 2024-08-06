package com.test.flagschallenge.model

import com.google.gson.annotations.SerializedName

data class Question(

    var questionNumber:Int = 0,

    @SerializedName("answer_id")
    val answerId:Int,

    val countries: List<Country>,

    @SerializedName("country_code")
    val countryCode: String

)