package com.test.flagschallenge.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseActivity : AppCompatActivity() {

    fun setToolbar(toolbar: Toolbar,title:String? = null){
        setSupportActionBar(toolbar)
        title?.let { setTitle(title) }
    }

}