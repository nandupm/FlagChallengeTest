package com.test.flagschallenge.exts

import android.content.Context
import android.content.SharedPreferences
import com.test.flagschallenge.ui.base.BaseFragment
import com.test.flagschallenge.utils.FCPreferenceManager

fun Long.toTwoDigitString():String{
    return String.format("%02d",this)
}

fun Int.toTwoDigitString():String{
    return String.format("%02d",this)
}

fun Context.getPreference():FCPreferenceManager{
    return FCPreferenceManager(this)
}