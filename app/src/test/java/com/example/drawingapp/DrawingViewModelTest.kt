package com.example.drawingapp

import org.junit.Assert.assertEquals
import org.junit.Test

class DrawingViewModelTest {

    @Test
    fun testSelectColor() {
        val viewModel = DrawingViewModel()
        val newColor = 0xFFFF0000.toInt()  // Red color
        viewModel.selectColor(newColor)
        assertEquals(newColor, viewModel.selectedColor.value)
    }
}
