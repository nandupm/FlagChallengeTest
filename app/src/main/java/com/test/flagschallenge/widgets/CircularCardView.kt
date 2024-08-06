package com.test.flagschallenge.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.cardview.widget.CardView

class CircularCardView : CardView {

    private val drawPath = Path()

    constructor(context: Context) : super(context)

    constructor(context: Context,attr:AttributeSet) : super(context,attr)

    constructor(context: Context,attr: AttributeSet,defStyle:Int) : super(context,attr,defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wh = widthMeasureSpec.coerceAtMost(heightMeasureSpec)
        val radius = wh.toFloat() / 2f
        setRadius(radius)
        super.onMeasure(wh,wh)
    }

    override fun dispatchDraw(canvas: Canvas) {
        drawPath.reset()

        drawPath.addCircle(
            measuredWidth.toFloat() / 2f,
            measuredHeight.toFloat() / 2f,
            radius,
            Path.Direction.CCW
        )
        canvas.clipPath(drawPath)
        super.dispatchDraw(canvas)
    }

}