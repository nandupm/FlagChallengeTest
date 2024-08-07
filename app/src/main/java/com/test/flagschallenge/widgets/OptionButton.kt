package com.test.flagschallenge.widgets

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

class OptionButton : MaterialButton {

    constructor(context: Context):super(context)

    constructor(context: Context,attributeSet: AttributeSet) : super(context,attributeSet)


    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (hasWindowFocus) {
            super.onWindowFocusChanged(true)
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        if (focused) {
            super.onFocusChanged(true, direction, previouslyFocusedRect)
        }
    }

    override fun isFocused(): Boolean {
        return true
    }


}