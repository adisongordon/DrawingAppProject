package com.example.drawingapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DrawingViewModel : ViewModel() {

    private val _selectedColor = MutableLiveData(0xFF000000.toInt())  // Default black
    val selectedColor: LiveData<Int> get() = _selectedColor

    fun selectColor(color: Int) {
        _selectedColor.value = color
    }
}
