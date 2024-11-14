package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.graphics.Path
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color

class DrawingViewModel : ViewModel() {

    private val _selectedColor = MutableLiveData(0xFF000000.toInt())  // Default black
    val selectedColor: LiveData<Int> get() = _selectedColor

    private val _bitmaps = MutableLiveData<List<Pair<Bitmap, String>>>() // Pair of Bitmap and title
    val bitmaps: LiveData<List<Pair<Bitmap, String>>> = _bitmaps
    var fileNameList: MutableList<String> = mutableListOf()
    var drawingDao: DrawingDAO? = null

    private val _bitmap = MutableLiveData(
        Bitmap.createBitmap(1000, 800, Bitmap.Config.ARGB_8888)
    )
    val bitmap: LiveData<Bitmap> = _bitmap
    private val path = Path()
    private val canvas = Canvas(_bitmap.value!!)

    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 10f
        style = Paint.Style.STROKE
        isAntiAlias = true
        isDither = true
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    fun selectColor(color: Int) {
        _selectedColor.value = color
    }

    // Start Drawing
    fun start(x: Float, y: Float) {
        path.moveTo(x, y)
    }

    // Move Drawing
    fun move(x: Float, y: Float) {
        path.lineTo(x, y)
        canvas.drawPath(path, paint)
        _bitmap.postValue(_bitmap.value)
    }

    // End Drawing
    fun up() {
        path.reset()
    }

    // Save image and return filename(String) for storing to RoomDB
    fun saveImage(context: Context, bitmap: Bitmap, drawingTitle: String): String {
        val filename = "${drawingTitle}.png" // Include title
        context.openFileOutput(filename, Context.MODE_PRIVATE).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        }
        return filename
    }

    // Insert into RoomDB
    fun insertIntoDB(context: Context, name: String, filename: String){
        viewModelScope.launch {
            val drawing = DrawingEntity(name = name, filePath = filename)
            val db = DrawingDatabase.getDatabase(context)
            db.drawingDAO().insert(drawing)
        }
    }

    // Loads all drawings from RoomDB
    fun loadDrawings(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = DrawingDatabase.getDatabase(context)
            val drawings = db.drawingDAO().getAllDrawings()

            val bitmapAndTitles = drawings.mapNotNull { drawing: DrawingEntity ->
                val bitmap = BitmapFactory.decodeFile(drawing.filePath)
                if (bitmap != null) {
                    Pair(bitmap, drawing.name) // Create Pair of Bitmap and title
                } else {
                    null
                }
            }

            withContext(Dispatchers.Main) {
                _bitmaps.value = bitmapAndTitles
            }
        }
    }
}
