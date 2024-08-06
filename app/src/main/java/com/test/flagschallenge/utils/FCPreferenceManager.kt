package com.test.flagschallenge.utils

import android.content.Context
import android.content.SharedPreferences

class FCPreferenceManager(context: Context) {

    companion object{
        private const val PREF_NAME = "fc_pref"

        const val PREF_SET_TIME = "time"
    }
    private val sharedPref:SharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)

    fun getLong(name:String):Long{
        return sharedPref.getLong(name,0)
    }

    fun getSavedTime():Long{
        return getLong(PREF_SET_TIME)
    }

    fun saveTime(time:Long){
        setLong(PREF_SET_TIME,time)
    }

    fun setLong(name: String, value:Long){
        with(sharedPref.edit()) {
            this.putLong(name,value)
            this.commit()
        }
    }

}