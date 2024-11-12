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
    private var path = Path()
    private val paths = mutableListOf<Pair<Path, Paint>>()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for ((p, paint) in paths) {
            canvas.drawPath(p, paint)
        }
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                paths.add(Pair(Path(path), Paint(paint)))
                path.reset()
            }
        }
        return true
    }

    fun setPenColor(color: Int) {
        paint.color = color
    }

    fun setPenSize(size: Float) {
        paint.strokeWidth = size
    }
}
