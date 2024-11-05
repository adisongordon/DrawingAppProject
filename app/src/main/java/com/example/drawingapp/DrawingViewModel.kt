package com.example.drawingapp

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DrawingViewModel : ViewModel() {

    private val _selectedColor = MutableLiveData(0xFF000000.toInt())  // Default black
    val selectedColor: LiveData<Int> get() = _selectedColor

    private val _bitmaps = MutableLiveData<List<Bitmap>>()
    val bitmaps: LiveData<List<Bitmap>> = _bitmaps

    fun selectColor(color: Int) {
        _selectedColor.value = color
    }
}
