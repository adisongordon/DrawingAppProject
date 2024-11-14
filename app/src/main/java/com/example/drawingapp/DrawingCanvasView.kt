package com.example.drawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class DrawingCanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private lateinit var viewModel: DrawingViewModel
    //private val paint = Paint()

    fun setViewModel(viewModel: DrawingViewModel) {
        this.viewModel = viewModel

        viewModel.bitmap.observe(context as LifecycleOwner, Observer<Bitmap> { bitmap ->
            invalidate()
        })
        viewModel.selectedColor.observe(context as LifecycleOwner, Observer<Int> { color ->
            // Update paint object with the new color
            paint.color = color
        })
    }

    private var paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 10f
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewModel.bitmap.value?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> viewModel.start(x, y)
            MotionEvent.ACTION_MOVE -> viewModel.move(x, y)
            MotionEvent.ACTION_UP -> viewModel.up()
        }
        invalidate()
        return true
    }

    fun setPenColor(color: Int) {
        paint.color = color
    }

    fun setPenSize(size: Float) {
        paint.strokeWidth = size
    }
}
