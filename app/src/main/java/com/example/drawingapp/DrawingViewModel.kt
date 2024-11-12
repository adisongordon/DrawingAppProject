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

class DrawingViewModel : ViewModel() {

    private val _selectedColor = MutableLiveData(0xFF000000.toInt())  // Default black
    val selectedColor: LiveData<Int> get() = _selectedColor

    private val _bitmaps = MutableLiveData<List<Bitmap>>()
    val bitmaps: LiveData<List<Bitmap>> = _bitmaps
    var fileNameList: MutableList<String> = mutableListOf()

    private val _bitmap = MutableLiveData(
        Bitmap.createBitmap(1000, 800, Bitmap.Config.ARGB_8888)
    )
    val bitmap: LiveData<Bitmap> = _bitmap

    fun selectColor(color: Int) {
        _selectedColor.value = color
    }

    // Save image and return filename(String) for storing to RoomDB
    fun saveImage(context: Context, bitmap: Bitmap): String {
        val filename = "drawing_${System.currentTimeMillis()}.png"
        context.openFileOutput(filename, Context.MODE_PRIVATE).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        }
        return filename
    }

    fun insertIntoDB(context: Context, name: String, filename: String){
        viewModelScope.launch {
            val drawing = DrawingEntity(name = name, filePath = filename)
            val db = DrawingDatabase.getDatabase(context)
            db.drawingDao().insert(drawing)
        }
    }

    fun loadAllDrawings(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = DrawingDatabase.getDatabase(context)
            val drawings = db.drawingDao().getAllDrawings() // Assuming this method exists

            val bitmaps = drawings.mapNotNull { drawing ->
                BitmapFactory.decodeFile(drawing.filePath)
            }

            withContext(Dispatchers.Main) {
                _bitmaps.value = bitmaps
            }
        }
    }
}
