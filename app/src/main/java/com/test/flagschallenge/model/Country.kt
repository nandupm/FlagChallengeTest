package com.test.flagschallenge.model

import com.google.gson.annotations.SerializedName

data class Country(

    val id:Int,

    @SerializedName("country_name")
    val countryName:String

)