package com.test.flagschallenge.widgets

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.text.isDigitsOnly
import com.test.flagschallenge.R
import com.test.flagschallenge.databinding.WidgetTimePickerBinding

class TimePickerView : FrameLayout {

    private lateinit var binding:WidgetTimePickerBinding
    private var currentEt:EditText? = null

    constructor(context: Context):super(context){
        initView()
    }

    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){
        initView()
    }

    private fun initView(){
        binding = WidgetTimePickerBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
        setViews()
    }

    private fun setViews(){

        binding.et1Hr.setText("0")
        binding.et2Hr.setText("0")
        binding.et1Min.setText("0")
        binding.et2Min.setText("0")
        binding.et1Sec.setText("0")
        binding.et2Sec.setText("0")

        binding.et1Hr.onFocusChangeListener = focusChangedListener
        binding.et2Hr.onFocusChangeListener = focusChangedListener
        binding.et1Min.onFocusChangeListener = focusChangedListener
        binding.et2Min.onFocusChangeListener = focusChangedListener
        binding.et1Sec.onFocusChangeListener = focusChangedListener
        binding.et2Sec.onFocusChangeListener = focusChangedListener

        binding.et1Hr.addTextChangedListener(listener)
        binding.et2Hr.addTextChangedListener(listener)
        binding.et1Min.addTextChangedListener(listener)
        binding.et2Min.addTextChangedListener(listener)
        binding.et1Sec.addTextChangedListener(listener)
        binding.et2Sec.addTextChangedListener(listener)


    }

    private var listener: TextWatcher = object : TextWatcher {
        private var oldText = ""
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            oldText = p0?.toString() ?: ""
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (!p0.isNullOrEmpty()){
                if (p0.length ==1){
                    if (!p0.isDigitsOnly()){
                        currentEt?.setText("0")
                    }
                    getNextFocusEditText()?.requestFocus()
                }else if (p0.length > 1){
                    val c = p0.toList()[p1]
                    currentEt?.setText(c.toString())
                }else{
                    currentEt?.setText("0")
                }
            }else{
                currentEt?.setText("0")
            }
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    }

    private val focusChangedListener: OnFocusChangeListener =
        OnFocusChangeListener { p0, p1 ->
            if (p1 && p0 is EditText){
                currentEt = p0
            }
        }

    private fun getNextFocusEditText():EditText?{
        val vw = currentEt ?: return null
        return when(vw.id){
            R.id.et1_hr -> {
                binding.et2Hr
            }
            R.id.et2_hr -> {
                binding.et1Min
            }
            R.id.et1_min -> {
                binding.et2Min
            }
            R.id.et2_min -> {
                binding.et1Sec
            }
            R.id.et1_sec -> {
                binding.et2Sec
            }
            else -> {
                null
            }
        }
    }


    private fun validate(hour: Int,minute: Int,second: Int):Boolean{
        return hour <= 24 && minute <= 60 && second <= 60
    }


    fun getTime():Time?{
        val hr = "${binding.et1Hr.text}${binding.et2Hr.text}".toIntOrNull() ?: 0
        val min = "${binding.et1Min.text}${binding.et2Min.text}".toIntOrNull() ?: 0
        val sec = "${binding.et1Sec.text}${binding.et2Sec.text}".toIntOrNull() ?: 0
        return if (validate(hr,min,sec)){
            Time(hr,min,sec)
        }else null
    }

    data class Time(
        val hour:Int,
        val minute:Int,
        val second:Int
    )

}