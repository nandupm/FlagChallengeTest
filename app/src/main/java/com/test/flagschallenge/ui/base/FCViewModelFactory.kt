package com.test.flagschallenge.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FCViewModelFactory <T>(val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return creator() as T
    }
}