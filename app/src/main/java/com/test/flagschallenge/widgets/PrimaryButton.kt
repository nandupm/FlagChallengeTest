package com.test.flagschallenge.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.test.flagschallenge.R

class PrimaryButton : androidx.appcompat.widget.AppCompatButton{

    constructor(context:Context):super(context)

    constructor(context: Context,attr:AttributeSet):super(context,attr, R.style.Theme_FlagsChallenge_PrimaryButtonTheme)

    init {
        background = ResourcesCompat.getDrawable(context.resources,R.drawable.bg_btn_primary,null)
        gravity = Gravity.CENTER
        setTextColor(ContextCompat.getColor(context,R.color.white))
        isAllCaps = true
    }
}